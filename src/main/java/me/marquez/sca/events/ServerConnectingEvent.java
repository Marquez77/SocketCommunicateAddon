package me.marquez.sca.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ServerConnectingEvent extends AbstractServerConnectEvent{
    static {
        handlers = new HandlerList();
    }
    public ServerConnectingEvent(@NotNull Player who, String originServer, String targetServer) {
        super(who, originServer, targetServer);
    }
}
