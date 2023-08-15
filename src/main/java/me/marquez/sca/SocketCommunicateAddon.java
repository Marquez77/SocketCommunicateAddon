package me.marquez.sca;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;
import me.marquez.sca.effects.EffOpenSocketServer;
import me.marquez.sca.effects.EffSendData;
import me.marquez.sca.events.DataReceiveEvent;
import me.marquez.sca.events.EvtReceiveData;
import me.marquez.sca.expr.ExprDataSender;
import me.marquez.sca.expr.ExprResponseData;
import me.marquez.sca.expr.ExprSendDataAndReceive;
import me.marquez.sca.expr.ExprSentData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketCommunicateAddon extends JavaPlugin {
    @Override
    public void onEnable() {
        Skript.registerAddon(this);

        Skript.registerEffect(EffOpenSocketServer.class, "open socket server with port %integer%");
        Skript.registerEffect(EffSendData.class, "send data named %string% with %objects% to %strings%");

        Skript.registerEvent("data receive", EvtReceiveData.class, DataReceiveEvent.class,"receive data named %string%");

        Skript.registerExpression(ExprSendDataAndReceive.class, Object.class, ExpressionType.SIMPLE, "send data named %string% with %objects% to %strings% and receive data");
        Skript.registerExpression(ExprDataSender.class, String.class, ExpressionType.SIMPLE, "data sender");
        Skript.registerExpression(ExprSentData.class, Object.class, ExpressionType.SIMPLE, "sent data");
        Skript.registerExpression(ExprResponseData.class, Object.class, ExpressionType.SIMPLE, "response data");
    }

    @Override
    public void onDisable() {

    }


}
