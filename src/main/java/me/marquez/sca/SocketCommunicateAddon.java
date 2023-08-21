package me.marquez.sca;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.marquez.sca.effects.EffCloseSocketServer;
import me.marquez.sca.effects.EffConnectServer;
import me.marquez.sca.events.*;
import me.marquez.sca.expressions.*;
import me.marquez.sca.effects.EffSendData;
import me.marquez.socket.udp.UDPEchoServer;
import me.marquez.socket.udp.entity.UDPEchoResponse;
import me.marquez.socket.udp.entity.UDPEchoSend;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;

public class SocketCommunicateAddon extends JavaPlugin {

    @Getter
    private static SocketCommunicateAddon instance;
    private String serverName;

    @Override
    public void onEnable() {
        instance = this;
        try {
            loadServerName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerSkriptAddons();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getLogger().info("Enabled SocketCommunicateAddon v" + getDescription().getVersion());
        getLogger().info("ServerName=" + serverName);
    }

    private void loadServerName() throws IOException {
        File file = new File("socket.txt");
        if(!file.exists()) {
            serverName = file.getParentFile().getName();
        }else {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            serverName = reader.readLine();
            reader.close();
        }
    }

    private void registerSkriptAddons() {
        Skript.registerAddon(this);

        Skript.registerEffect(EffSendData.class, "[(1¦synchronously)] send data named %string% with %objects% to %strings% from %object% [and receive in %-objects% [or timeout %integer%]]");
        Skript.registerEffect(EffCloseSocketServer.class, "close socket server of %object%");
        Skript.registerEffect(EffConnectServer.class, "connect server %players% to %string%");

        Skript.registerEvent("data receive", EvtReceiveData.class, DataReceiveEvent.class,"receive data named %string%");
        Skript.registerEvent("server pre-connect", SimpleEvent.class, ServerPreConnectEvent.class, "server pre[-]connect");
        Skript.registerEvent("server connecting", SimpleEvent.class, ServerConnectingEvent.class, "server connect[ing]");
        Skript.registerEvent("server post-connect", SimpleEvent.class, ServerPostConnectEvent.class, "server post[-]connect");
        Skript.registerEvent("server connect failed", SimpleEvent.class, ServerConnectFailEvent.class, "server connect failed");

        Skript.registerExpression(ExprOpenSocketServer.class, UDPEchoServer.class, ExpressionType.SIMPLE, "open socket server with port %number%[ on debug %boolean%]");
        Skript.registerExpression(ExprDataSender.class, String.class, ExpressionType.SIMPLE, "data sender");
        Skript.registerExpression(ExprReceivedData.class, MinecraftEchoData.class, ExpressionType.SIMPLE, "received data");
        Skript.registerExpression(ExprResponseData.class, UDPEchoResponse.class, ExpressionType.SIMPLE, "response data");
        Skript.registerExpression(ExprOriginServer.class, String.class, ExpressionType.SIMPLE, "origin(-| )server");
        Skript.registerExpression(ExprTargetServer.class, String.class, ExpressionType.SIMPLE, "target(-| )server");
        Skript.registerExpression(ExprCancelReason.class, String.class, ExpressionType.SIMPLE, "cancel(-| )reason");
        Skript.registerExpression(ExprFailedReason.class, String.class, ExpressionType.SIMPLE, "failed(-| )reason");
        Skript.registerExpression(ExprCurrentServer.class, String.class, ExpressionType.SIMPLE, "current(-| )server");
    }

    public String getCurrentServerName() {
        return serverName;
    }

    public void connectPlayer(Player player, String server) {
        if(Bukkit.isPrimaryThread()) {
            Executors.newCachedThreadPool().submit(() -> connectPlayer(player, server));
            return;
        }
        ServerPreConnectEvent preConnectEvent = new ServerPreConnectEvent(player, getCurrentServerName(), server);
        getServer().getPluginManager().callEvent(preConnectEvent);
        if(preConnectEvent.isCancelled() || !preConnectEvent.getCancelReasons().isEmpty()) {
            failConnect(player, server, preConnectEvent.getCancelReasons());
            return;
        }

        ServerConnectingEvent connectingEvent = new ServerConnectingEvent(player, getCurrentServerName(), server);
        getServer().getPluginManager().callEvent(connectingEvent);
        if(connectingEvent.isCancelled()) {
            failConnect(player, server, List.of("connecting event cancelled"));
            return;
        }
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", output.toByteArray());

        ServerPostConnectEvent postConnectEvent = new ServerPostConnectEvent(getServer().getOfflinePlayer(player.getUniqueId()), getCurrentServerName(), server);
        getServer().getPluginManager().callEvent(postConnectEvent);
    }

    public void failConnect(Player player, String server, List<String> failReasons) {
        ServerConnectFailEvent connectFailEvent = new ServerConnectFailEvent(player, getCurrentServerName(), server, failReasons);
        getServer().getPluginManager().callEvent(connectFailEvent);
    }

}
