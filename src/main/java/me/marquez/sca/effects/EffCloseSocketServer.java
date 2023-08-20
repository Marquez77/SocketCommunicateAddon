package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import me.marquez.socket.udp.UDPEchoServer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffCloseSocketServer extends Effect {

    private Expression<UDPEchoServer> server;

    @Override
    protected void execute(Event event) {
        UDPEchoServer server = this.server.getSingle(event);
        server.close();
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.server = (Expression<UDPEchoServer>)expressions[0];
        return true;
    }
}
