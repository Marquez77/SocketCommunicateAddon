package me.marquez.sca.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

public class EffSendData extends Effect {

    private Expression<String> name;
    private Expression<Object> data;
    private Expression<String> target;
    private Expression<UDPEchoServer> server;

    @Override
    protected void execute(Event event) {
        String name = this.name.getSingle(event);
        Object[] data = this.data.getArray(event);
        UDPEchoServer server = this.server.getSingle(event);
        UDPEchoSend send = new UDPEchoSend(name);
        for (Object d : data) send.append(d);
        for(String targets : this.target.getArray(event)) {
            String[] target = targets.split(":");
            InetSocketAddress address = new InetSocketAddress(target[0], Integer.parseInt(target[1]));
//            if(server.getServerSocket().getInetAddress().equals(address)) continue;
            server.sendData(address, send);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        data = (Expression<Object>) expressions[1];
        target = (Expression<String>) expressions[2];
        server = (Expression<UDPEchoServer>) expressions[3];
        return true;
    }
}
