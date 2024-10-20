package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.packet.entity.PacketReceive;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprReceivedData extends SimpleExpression<PacketReceive> {
    @Override
    protected @Nullable PacketReceive[] get(Event event) {
        if(event instanceof DataReceiveEvent e) {
            PacketReceive data = e.getData().clonePacket();
//            data.nextString();
            return new PacketReceive[] { data };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends PacketReceive> getReturnType() {
        return PacketReceive.class;
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
