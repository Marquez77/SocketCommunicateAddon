package me.marquez.sca;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import me.marquez.sca.effects.EffCloseSocketServer;
import me.marquez.sca.expr.ExprOpenSocketServer;
import me.marquez.sca.effects.EffSendData;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.sca.events.EvtReceiveData;
import me.marquez.sca.expr.ExprDataSender;
import me.marquez.sca.expr.ExprResponseData;
import me.marquez.sca.expr.ExprSendDataAndReceive;
import me.marquez.sca.expr.ExprReceivedData;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketCommunicateAddon extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(this, this);

        Skript.registerAddon(this);

        Skript.registerEffect(EffSendData.class, "send data named %string% with %objects% to %strings% from %object%");
        Skript.registerEffect(EffCloseSocketServer.class, "close socket server of %object%");

        Skript.registerEvent("data receive", EvtReceiveData.class, DataReceiveEvent.class,"receive data named %string%");

        Skript.registerExpression(ExprOpenSocketServer.class, UDPEchoServer.class, ExpressionType.SIMPLE, "open socket server with port %integer% [on debug %boolean%]");
        Skript.registerExpression(ExprSendDataAndReceive.class, UDPEchoResponse.class, ExpressionType.SIMPLE, "send data named %string% with %objects% to %string% from %object% and receive data");
        Skript.registerExpression(ExprDataSender.class, String.class, ExpressionType.SIMPLE, "data sender");
        Skript.registerExpression(ExprReceivedData.class, UDPEchoSend.class, ExpressionType.SIMPLE, "received data");
        Skript.registerExpression(ExprResponseData.class, UDPEchoResponse.class, ExpressionType.SIMPLE, "response data");
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onReceiveData(DataReceiveEvent e) {
        System.out.println("onReceiveData: " + e.getData());
    }


}
