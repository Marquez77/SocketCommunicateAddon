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
@RequiredArgsConstructor
public class ServerPostConnectEvent extends Event implements ServerConnectEvent{
    private static final HandlerList handlers = new HandlerList();

    private final String originServer;
    private final String targetServer;
    private final OfflinePlayer offlinePlayer;
    @Setter
    private boolean cancelled;

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
