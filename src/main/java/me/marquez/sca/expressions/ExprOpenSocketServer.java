package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.udp.UDPEchoServer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExprOpenSocketServer extends SimpleExpression<UDPEchoServer> {

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
    protected @Nullable UDPEchoServer[] get(Event event) {
        Number port = this.port.getSingle(event);
        boolean debug = this.debug != null ? this.debug.getSingle(event) : false;
        if (port != null) {
            UDPEchoServer server = null;
            try {
                final UDPEchoServer finalServer = server = new UDPEchoServer(port.intValue(), LoggerFactory.getLogger("Skript-UDP-Server"));
                // TODO: 2023-10-10 change outside
                server.registerHandler((inetSocketAddress, udpEchoSend, udpEchoResponse) -> {
                    DataReceiveEvent e = new DataReceiveEvent(finalServer, inetSocketAddress, udpEchoSend, udpEchoResponse);
                    Bukkit.getPluginManager().callEvent(e);
                });
                server.setDebug(debug);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new UDPEchoServer[] { server };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends UDPEchoServer> getReturnType() {
        return UDPEchoServer.class;
    }
}
