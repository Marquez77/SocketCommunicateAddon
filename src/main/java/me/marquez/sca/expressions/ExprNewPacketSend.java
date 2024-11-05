package me.marquez.sca.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.socket.SocketAPI;
import me.marquez.socket.packet.entity.PacketSend;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprNewPacketSend extends SimpleExpression<PacketSend> {

    @Override
    protected @Nullable PacketSend[] get(Event e) {
        return new PacketSend[] { SocketAPI.createPacketSend() };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends PacketSend> getReturnType() {
        return PacketSend.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "new send packet";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
