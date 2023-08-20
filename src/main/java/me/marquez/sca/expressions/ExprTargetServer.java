package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.ServerConnectEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprTargetServer extends SimpleExpression<String> {
    @Override
    protected @Nullable String[] get(Event event) {
        if(event instanceof ServerConnectEvent e) {
            return new String[] { e.getTargetServer() };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
