package me.marquez.sca.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprResponseData extends SimpleExpression<UDPEchoResponse> {
    @Override
    protected @Nullable UDPEchoResponse[] get(Event event) {
        if(event instanceof DataReceiveEvent e) {
            return new UDPEchoResponse[] { e.getResponse() };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends UDPEchoResponse> getReturnType() {
        return UDPEchoResponse.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        switch(mode) {
            case ADD -> {
                return new Class<?>[] { Object.class } ;
            }
        }
        return null;
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if(event instanceof DataReceiveEvent e) {
            if (mode == Changer.ChangeMode.ADD) {
                e.getResponse().append(delta[0]);
            }
        }
    }
}
