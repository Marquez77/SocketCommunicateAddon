package me.marquez.sca.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@Getter
@RequiredArgsConstructor
public class DataReceiveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NonNull
    private final InetSocketAddress sender;
    @NonNull
    private final UDPEchoSend data;
    @NonNull
    private final UDPEchoResponse response;
}
