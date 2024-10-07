package me.marquez.sca.placeholder;

import ch.njol.skript.variables.Variables;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SkriptVariableExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "skriptvariable";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DevMarquez";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(!params.contains("."))
            return null;
        if(params.contains("{") && params.contains("}")) {
            params = params.replaceFirst("\\{", "%");
            params = replaceLast(params, "}", "%");
            params = PlaceholderAPI.setPlaceholders(player, params);
        }
        return Optional.ofNullable(Variables.getVariable(params, null, false)).map(Object::toString).orElse("");
    }

    public static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos) + replacement + string.substring(pos + toReplace.length());
        } else {
            return string;
        }
    }
}
