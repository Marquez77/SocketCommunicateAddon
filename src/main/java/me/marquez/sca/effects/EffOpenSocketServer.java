package me.marquez.sca.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.udp.UDPEchoServer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EffOpenSocketServer extends AsyncEffect {

    private Expression<Integer> port;

    @Override
    protected void execute(Event event) {
        Integer port = this.port.getSingle(event);
        if (port != null) {
            UDPEchoServer server = null;
            try {
                server = new UDPEchoServer(port, LoggerFactory.getLogger("Skript-UDP-Server"));
                server.registerHandler((inetSocketAddress, udpEchoSend, udpEchoResponse) -> {
                    DataReceiveEvent e = new DataReceiveEvent(inetSocketAddress, udpEchoSend, udpEchoResponse);
                    Bukkit.getPluginManager().callEvent(e);
                });
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.port = (Expression<Integer>) expressions[0];
        return true;
    }
}
