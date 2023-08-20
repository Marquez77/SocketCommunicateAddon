package me.marquez.sca.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtReceiveData extends SimpleEvent {

    private Literal<String> name;

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        this.name = (Literal<String>) literals[0];
        return true;
    }

    @Override
    public boolean check(Event event) {
        String name = this.name.getSingle();
        if(event instanceof DataReceiveEvent e) {
            if(name.equalsIgnoreCase(e.getData().clone().nextString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return getClass().getName();
    }
}
