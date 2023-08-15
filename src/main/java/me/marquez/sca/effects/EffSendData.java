package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

//"send data named %string% with %objects% to %strings%"
public class EffSendData extends Effect {

    private Expression<String> name;
    private Expression<Object> data;
    private Expression<String> target;

    @Override
    protected void execute(Event event) {
        String name = this.name.getSingle(event);
        Object[] data = this.data.getArray(event);
        String[] target = this.target.getSingle(event).split(":");
        InetSocketAddress address = new InetSocketAddress(target[0], Integer.parseInt(target[1]));

    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        data = (Expression<Object>) expressions[1];
        target = (Expression<String>) expressions[2];
        return true;
    }
}
