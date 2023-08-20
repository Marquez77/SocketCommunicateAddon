package me.marquez.sca.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AbstractServerConnectEvent extends PlayerEvent implements ServerConnectEvent {

    protected static HandlerList handlers;

    private final String originServer;
    private final String targetServer;
    @Setter
    private boolean cancelled;

    public AbstractServerConnectEvent(@NotNull Player who, String originServer, String targetServer) {
        super(who);
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
