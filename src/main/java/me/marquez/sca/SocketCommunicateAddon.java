package me.marquez.sca;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import me.marquez.sca.effects.EffCloseSocketServer;
import me.marquez.sca.effects.EffConnectServer;
import me.marquez.sca.expressions.ExprOpenSocketServer;
import me.marquez.sca.effects.EffSendData;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.sca.events.EvtReceiveData;
import me.marquez.sca.expressions.ExprDataSender;
import me.marquez.sca.expressions.ExprResponseData;
import me.marquez.sca.expressions.ExprSendDataAndReceive;
import me.marquez.sca.expressions.ExprReceivedData;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketCommunicateAddon extends JavaPlugin {
    @Override
    public void onEnable() {
        Skript.registerAddon(this);

        Skript.registerEffect(EffSendData.class, "send data named %string% with %objects% to %strings% from %object%");
        Skript.registerEffect(EffCloseSocketServer.class, "close socket server of %object%");
        Skript.registerEffect(EffConnectServer.class, "connect server %players% to %string%");

        Skript.registerEvent("data receive", EvtReceiveData.class, DataReceiveEvent.class,"receive data named %string%");
//        Skript.registerEvent("server pre-connect", SimpleEvent.class, EvtServerPreConnect.class, "server pre[-]connect");
//        Skript.registerEvent("server connecting", SimpleEvent.class, EvtServerConnecting.class, "server connect[ing]");
//        Skript.registerEvent("server post-connect", SimpleEvent.class, EvtServerPostConnect.class, "server post[-]connect");

        Skript.registerExpression(ExprOpenSocketServer.class, UDPEchoServer.class, ExpressionType.SIMPLE, "open socket server with port %number%[ on debug %boolean%]");
        Skript.registerExpression(ExprSendDataAndReceive.class, UDPEchoResponse.class, ExpressionType.SIMPLE, "send data named %string% with %objects% to %string% from %object% and receive data");
        Skript.registerExpression(ExprDataSender.class, String.class, ExpressionType.SIMPLE, "data sender");
        Skript.registerExpression(ExprReceivedData.class, UDPEchoSend.class, ExpressionType.SIMPLE, "received data");
        Skript.registerExpression(ExprResponseData.class, UDPEchoResponse.class, ExpressionType.SIMPLE, "response data");
    }

    @Override
    public void onDisable() {

    }
}
