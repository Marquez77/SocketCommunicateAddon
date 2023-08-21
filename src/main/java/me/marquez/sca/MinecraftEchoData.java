package me.marquez.sca;

import me.marquez.socket.udp.entity.UDPEchoData;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MinecraftEchoData extends UDPEchoData {
    private MinecraftEchoData() {}

    public static MinecraftEchoData of(UDPEchoData data) {
        var result = new MinecraftEchoData();
        result.data.addAll(Arrays.asList(data.getData()));
        return result;
    }

    public MinecraftEchoData clone() {
        return of(this);
    }

    public ItemStack nextItem() {
        return CustomDataAppender.INSTANCE.nextItem(this);
    }
}
