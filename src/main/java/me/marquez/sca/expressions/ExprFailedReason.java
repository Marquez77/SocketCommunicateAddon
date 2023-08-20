package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.ServerConnectFailEvent;
import me.marquez.sca.events.ServerPreConnectEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprFailedReason extends SimpleExpression<String> {
    @Override
    protected @Nullable String[] get(Event event) {
        if(event instanceof ServerConnectFailEvent e) {
            return e.getFailReasons().toArray(String[]::new);
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
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
