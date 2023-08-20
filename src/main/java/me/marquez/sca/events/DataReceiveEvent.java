package me.marquez.sca.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@Getter
public class DataReceiveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return DataReceiveEvent.handlers;
    }

    public HandlerList getHandlers() {
        return DataReceiveEvent.handlers;
    }

    @NonNull
    private final UDPEchoServer server;
    @NonNull
    private final InetSocketAddress sender;
    @NonNull
    private final UDPEchoSend data;
    @NonNull
    private final UDPEchoResponse response;

    public DataReceiveEvent(UDPEchoServer server, InetSocketAddress sender, UDPEchoSend data, UDPEchoResponse response) {
        super(true);
        this.server = server;
        this.sender = sender;
        this.data = data;
        this.response = response;
    }
}
