package me.marquez.sca.expr;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprReceivedData extends SimpleExpression<UDPEchoSend> {
    @Override
    protected @Nullable UDPEchoSend[] get(Event event) {
        if(event instanceof DataReceiveEvent e) {
            return new UDPEchoSend[] { e.getData() };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends UDPEchoSend> getReturnType() {
        return UDPEchoSend.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
