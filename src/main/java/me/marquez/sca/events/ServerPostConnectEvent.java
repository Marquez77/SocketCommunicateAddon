package me.marquez.sca.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class ServerPostConnectEvent extends Event implements ServerConnectEvent{
    private static final HandlerList handlers = new HandlerList();

    private final OfflinePlayer offlinePlayer;
    private final String originServer;
    private final String targetServer;

    public ServerPostConnectEvent(OfflinePlayer player, String originServer, String targetServer) {
        super(true);
        this.offlinePlayer = player;
        this.originServer = originServer;
        this.targetServer = targetServer;
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
