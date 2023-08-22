package me.marquez.sca.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.marquez.sca.CustomDataAppender;
import me.marquez.sca.MinecraftEchoData;
import me.marquez.sca.SocketCommunicateAddon;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
send data named %string% with %objects% to %strings% from %object% [and receive in %-objects% [or timeout %integer%]]
 */
public class EffSendData extends Delay {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Expression<String> name;
    private Expression<Object> data;
    private Expression<String> target;
    private Expression<UDPEchoServer> server;

    private boolean isSync;
    private VariableString var;
    private boolean isLocal;
    private boolean isList;

    private long timeout = 30*1000L;

    @Nullable
    private InetSocketAddress getAddress(String str) {
        String[] target = str.split(":");
        if(target.length < 2) {
            Skript.error("Invalid server address: " + str);
            return null;
        }
        return new InetSocketAddress(target[0], Integer.parseInt(target[1]));
    }

    private void continueScriptExecution(Event e) {
        if(getNext() != null) {
            TriggerItem.walk(getNext(), e);
        }
    }

    @Override
    protected @Nullable TriggerItem walk(Event e) {
        debug(e, true);
        delayed.add(e);
        execute(e);
        return null;
    }

    @Override
    protected void execute(Event event) {
        String name = this.name.getSingle(event);
        Object[] data = this.data.getArray(event);
        UDPEchoServer server = this.server.getSingle(event);
        if(server == null) {
            Skript.error("UDP socket server is null!");
            return;
        }
        UDPEchoSend send = new UDPEchoSend(name);
        for (Object d : data) {
            if(d instanceof ItemStack item) {
                CustomDataAppender.INSTANCE.appendItem(send, item);
            }else {
                send.append(d);
            }
        }
        String[] targetArray = this.target.getArray(event);
        if(targetArray.length == 1 && var != null) {
            boolean isMainThread = Bukkit.isPrimaryThread();
            String address = targetArray[0];
            if(isSync) {
                if (isMainThread) {
                    Skript.warning("§e===============================================================");
                    Skript.warning("§csend data and receive effect was attempted on the main thread!");
                    Skript.warning("§e===============================================================");
                }
                executeSend(server, address, send, event, null);
                continueScriptExecution(event);
            }else {
                final Object localVars = Variables.removeLocals(event);
                CompletableFuture.supplyAsync(() -> {
                            executeSend(server, address, send, event, localVars);
                            return null;
                        }, threadPool)
                .whenComplete((o, throwable) -> {
                    Bukkit.getScheduler().runTask(SocketCommunicateAddon.getInstance(), ()->continueScriptExecution(event));
                });
            }
        }else {
            for (String target : targetArray) {
                var socketAddress = getAddress(target);
                if(socketAddress == null) continue;
                server.sendData(socketAddress, send);
            }
            continueScriptExecution(event);
        }
    }

    private void executeSend(UDPEchoServer server, String address, UDPEchoSend send, Event event, Object localVars) {
        var socketAddress = getAddress(address);
        if(socketAddress == null) return;
        server.sendDataAndReceive(socketAddress, send)
                .whenComplete((udpEchoResponse, throwable) -> {
                    if (throwable == null) {
                        if(localVars != null) Variables.setLocalVariables(event, localVars);
                        Variables.setVariable(var.toString(event).toLowerCase(Locale.ENGLISH), MinecraftEchoData.of(udpEchoResponse), event, isLocal);
                    }
                }).orTimeout(timeout, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {
                    return null;
                }).join();
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        try {
            name = (Expression<String>) expressions[0];
            data = (Expression<Object>) expressions[1];
            target = (Expression<String>) expressions[2];
            server = (Expression<UDPEchoServer>) expressions[3];
            isSync = parseResult.mark == 1;
            if (expressions.length > 4 && expressions[4] instanceof Variable<?> variable) {
                var = variable.getName();
                isLocal = variable.isLocal();
                isList = variable.isList();
                if (expressions.length > 5 && expressions[5] instanceof Literal<?> literal) {
                    if(!literal.isDefault()) timeout = ((Literal<Integer>) literal).getSingle();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
