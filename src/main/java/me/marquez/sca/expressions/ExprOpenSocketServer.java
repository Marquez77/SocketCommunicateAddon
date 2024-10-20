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

    private Expression<Number> port;
    private Expression<Boolean> debug;

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.port = (Expression<Number>) expressions[0];
        if(expressions.length > 1) {
            this.debug = (Expression<Boolean>) expressions[1];
        }
        return true;
    }

    @Override
    protected @Nullable SocketServer[] get(Event event) {
        Number port = this.port.getSingle(event);
        boolean debug = this.debug != null ? this.debug.getSingle(event) : false;
        if (port != null) {
            SocketServer server = null;
            try {
                final SocketServer finalServer = server = SocketAPI.getFactory(ServerProtocol.TCP).createOrGet(port.intValue());
                server.registerListener(new PacketListener() {
                    @PacketHandler
                    public void onPacketReceive(PacketMessage packet) {
                        DataReceiveEvent e = new DataReceiveEvent(finalServer, packet.origin_server_address(), packet.received_packet());
                        Bukkit.getPluginManager().callEvent(e);
                    }
                });
                server.setDebug(debug);
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
