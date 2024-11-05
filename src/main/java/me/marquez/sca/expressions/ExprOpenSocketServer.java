package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.SocketAPI;
import me.marquez.socket.data.ServerProtocol;
import me.marquez.socket.data.SocketServer;
import me.marquez.socket.packet.PacketHandler;
import me.marquez.socket.packet.PacketListener;
import me.marquez.socket.packet.PacketMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ExprOpenSocketServer extends SimpleExpression<SocketServer> {

    private Expression<String> host;
    private Expression<Number> port;
    private Expression<Boolean> debug;

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.host = (Expression<String>) expressions[0];
        this.port = (Expression<Number>) expressions[1];
        if(expressions.length > 1) {
            this.debug = (Expression<Boolean>) expressions[2];
        }
        return true;
    }

    @Override
    protected @Nullable SocketServer[] get(Event event) {
        String host = this.host.getSingle(event);
        Number port = this.port.getSingle(event);
        boolean debug = this.debug != null ? this.debug.getSingle(event) : false;
        if (port != null) {
            SocketServer server = null;
            try {
                final SocketServer finalServer = server = SocketAPI.getFactory(ServerProtocol.TCP).createOrGet(host, port.intValue(), debug, 100, 10);
                server.registerListener(new PacketListener() {
                    @PacketHandler(identifiers = {"Skript", "*"})
                    public void onPacketReceive(PacketMessage packet) {
                        DataReceiveEvent e = new DataReceiveEvent(finalServer, packet.origin_server_address(), packet.received_packet());
                        Bukkit.getPluginManager().callEvent(e);
                    }
                });
                server.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new SocketServer[] { server };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends SocketServer> getReturnType() {
        return SocketServer.class;
    }
}
