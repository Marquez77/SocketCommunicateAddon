package me.marquez.sca.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AbstractServerConnectEvent extends Event implements ServerConnectEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String originServer;
    private final String targetServer;
    @Setter
    private boolean cancelled;

    public AbstractServerConnectEvent(@NotNull Player player, String originServer, String targetServer) {
        super(true);
        this.player = player;
        this.originServer = originServer;
        this.targetServer =targetServer;
    }


    @Override
    public OfflinePlayer getOfflinePlayer() {
        return getPlayer();
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
