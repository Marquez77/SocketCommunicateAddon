package me.marquez.sca.events;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServerConnectFailEvent extends AbstractServerConnectEvent {
    static {
        handlers = new HandlerList();
    }

    @Getter
    private final ImmutableList<String> failReasons;

    public ServerConnectFailEvent(@NotNull Player who, String originServer, String targetServer, List<String> failReasons) {
        super(who, originServer, targetServer);
        this.failReasons = ImmutableList.copyOf(failReasons);
    }
}
