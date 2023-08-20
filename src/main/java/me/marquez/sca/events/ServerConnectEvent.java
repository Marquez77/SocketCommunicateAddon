package me.marquez.sca.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;

public interface ServerConnectEvent extends Cancellable {
    String getOriginServer();
    String getTargetServer();
    OfflinePlayer getOfflinePlayer();
}
