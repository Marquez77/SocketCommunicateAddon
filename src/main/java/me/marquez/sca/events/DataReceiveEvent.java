package me.marquez.sca.events;

import lombok.Getter;
import lombok.NonNull;
import me.marquez.socket.data.SocketServer;
import me.marquez.socket.packet.entity.PacketReceive;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.net.SocketAddress;

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
    private final SocketServer server;
    @NonNull
    private final SocketAddress sender;
    @NonNull
    private final PacketReceive data;

    public DataReceiveEvent(SocketServer server, SocketAddress sender, PacketReceive data) {
        super(true);
        this.server = server;
        this.sender = sender;
        this.data = data;
    }
}
