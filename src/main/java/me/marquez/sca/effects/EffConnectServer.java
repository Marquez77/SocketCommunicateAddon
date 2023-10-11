package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.marquez.sca.SocketCommunicateAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffConnectServer extends Effect {

    private Expression<Player> player;
    private Expression<String> server;

    @Override
    protected void execute(Event event) {
//        Player[] players = this.player.getArray(event);
//        String server = this.server.getSingle(event);
//        for (Player p : players) {
//            SocketCommunicateAddon.getInstance().connectPlayer(p, server);
//        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>)expressions[0];
        this.server = (Expression<String>)expressions[1];
        return true;
    }
}
