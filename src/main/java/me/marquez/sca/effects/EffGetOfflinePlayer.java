package me.marquez.sca.effects;

import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.marquez.sca.SocketCommunicateAddon;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
set %object% to %string% parsing to offline player on async
 */
public class EffGetOfflinePlayer extends Delay {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Expression<String> target;

    private VariableString var;
    private boolean isLocal;

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
        String target = this.target.getSingle(event);
        final Object localVars = Variables.removeLocals(event);
        CompletableFuture.supplyAsync(() -> {
                    executeParsing(target, event, localVars);
                    return null;
                }, threadPool)
                .whenComplete((o, throwable) -> {
                    Bukkit.getScheduler().runTask(SocketCommunicateAddon.getInstance(), ()->continueScriptExecution(event));
                });
    }

    public void executeParsing(String target, Event event, Object localVars) {
        OfflinePlayer player;
        if(target.length() == 36) {
            player = Bukkit.getOfflinePlayer(UUID.fromString(target));
        }else {
            player = Bukkit.getOfflinePlayer(target);
        }
        if(localVars != null) Variables.setLocalVariables(event, localVars);
        Variables.setVariable(var.toString(event).toLowerCase(Locale.ENGLISH), player, event, isLocal);
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expressions[0] instanceof Variable<?> variable) {
            var = variable.getName();
            isLocal = variable.isLocal();
        }
        target = (Expression<String>) expressions[1];
        return true;
    }
}
