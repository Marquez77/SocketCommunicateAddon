package me.marquez.sca.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.ServerConnectEvent;
import me.marquez.sca.events.ServerPreConnectEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCancelReason extends SimpleExpression<String> {
    @Override
    protected @Nullable String[] get(Event event) {
        if(event instanceof ServerPreConnectEvent e) {
            return e.getCancelReasons().toArray(String[]::new);
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

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        switch(mode) {
            case ADD:
                return new Class<?>[] { String.class };
        }
        return null;
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if(mode == Changer.ChangeMode.ADD) {
            if(event instanceof ServerPreConnectEvent e) {
                e.getCancelReasons().add((String)delta[0]);
            }
        }
    }
}
