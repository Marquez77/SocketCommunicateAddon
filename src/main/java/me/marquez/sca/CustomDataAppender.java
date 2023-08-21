package me.marquez.sca;

import ch.njol.skript.registrations.Classes;
import ch.njol.skript.variables.Variables;
import lombok.NonNull;
import me.marquez.socket.udp.entity.UDPEchoData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CustomDataAppender {

    private CustomDataAppender() {}
    public static CustomDataAppender INSTANCE = new CustomDataAppender();

    public UDPEchoData appendItem(UDPEchoData storage, @NonNull ItemStack item) {
        byte[] data = Variables.serialize(item).data;
        String hash = encode(data);
        storage.append(hash);
        return storage;
    }

    @Nullable
    public ItemStack nextItem(UDPEchoData storage) {
        String hash = storage.nextString();
        byte[] data = decode(hash);
        return (ItemStack)Classes.deserialize("itemstack", data);
    }

    static String encode(byte[] data) {
        char[] r = new char[data.length * 2];

        for(int i = 0; i < data.length; ++i) {
            r[2 * i] = Character.toUpperCase(Character.forDigit((data[i] & 240) >>> 4, 16));
            r[2 * i + 1] = Character.toUpperCase(Character.forDigit(data[i] & 15, 16));
        }

        return new String(r);
    }

    static byte[] decode(String hex) {
        byte[] r = new byte[hex.length() / 2];

        for(int i = 0; i < r.length; ++i) {
            r[i] = (byte)((Character.digit(hex.charAt(2 * i), 16) << 4) + Character.digit(hex.charAt(2 * i + 1), 16));
        }

        return r;
    }
}
