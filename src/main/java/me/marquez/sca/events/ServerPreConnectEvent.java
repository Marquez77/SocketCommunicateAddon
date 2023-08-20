package me.marquez.sca.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServerPreConnectEvent extends AbstractServerConnectEvent{
    @Getter
    private final List<String> cancelReasons = new ArrayList<>();

    static {
        handlers = new HandlerList();
    }

    public ServerPreConnectEvent(@NotNull Player who, String originServer, String targetServer) {
        super(who, originServer, targetServer);
    }
}
