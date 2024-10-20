package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.marquez.socket.data.SocketServer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class EffCloseSocketServer extends Effect {

    private Expression<SocketServer> server;

    @Override
    protected void execute(Event event) {
        SocketServer server = this.server.getSingle(event);
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.server = (Expression<SocketServer>)expressions[0];
        return true;
    }
}
