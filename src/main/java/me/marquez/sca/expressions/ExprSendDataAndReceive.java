package me.marquez.sca.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

public class ExprSendDataAndReceive extends SimpleExpression<UDPEchoResponse> {

    private Expression<String> name;
    private Expression<Object> data;
    private Expression<String> target;
    private Expression<UDPEchoServer> server;

    @Override
    protected @Nullable UDPEchoResponse[] get(Event event) {
        String name = this.name.getSingle(event);
        Object[] data = this.data.getArray(event);
        String[] target = this.target.getSingle(event).split(":");
        InetSocketAddress address = new InetSocketAddress(target[0], Integer.parseInt(target[1]));
        UDPEchoServer server = this.server.getSingle(event);
        UDPEchoSend send = new UDPEchoSend(name);
        for (Object d : data) send.append(d);
        UDPEchoResponse response = server.sendDataAndReceive(address, send).join();
        return new UDPEchoResponse[] { response };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends UDPEchoResponse> getReturnType() {
        return UDPEchoResponse.class;
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
