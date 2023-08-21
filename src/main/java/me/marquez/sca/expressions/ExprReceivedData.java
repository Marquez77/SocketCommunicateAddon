package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.sca.MinecraftEchoData;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprReceivedData extends SimpleExpression<MinecraftEchoData> {
    @Override
    protected @Nullable MinecraftEchoData[] get(Event event) {
        if(event instanceof DataReceiveEvent e) {
            MinecraftEchoData data = e.getData().clone();
            data.nextString();
            return new MinecraftEchoData[] { data };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MinecraftEchoData> getReturnType() {
        return MinecraftEchoData.class;
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
