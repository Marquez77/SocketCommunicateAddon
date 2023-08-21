package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.marquez.sca.SocketCommunicateAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffKickPlayerOnProxy extends Effect {

    private Expression<Player> player;
    private Expression<String> reason;

    @Override
    protected void execute(Event event) {
        Player player = this.player.getSingle(event);
        String reason = this.reason.getSingle(event);
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("KickPlayer");
        output.writeUTF(player.getName());
        output.writeUTF(reason);
        player.sendPluginMessage(SocketCommunicateAddon.getInstance(), "BungeeCord", output.toByteArray());
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.reason = (Expression<String>) expressions[1];
        return true;
    }
}
