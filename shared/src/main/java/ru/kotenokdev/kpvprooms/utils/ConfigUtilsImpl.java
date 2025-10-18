package ru.kotenokdev.kpvprooms.utils;

import org.bukkit.entity.Player;

public class ConfigUtilsImpl implements ConfigUtils{

    @Override
    public void sendMessage(Player player, String message, String... placeholders) {
        if (placeholders.length > 1){
            String placeholder = "${"+placeholders[0]+"}";
            for (int i = 1; i < placeholders.length; i++){
                String v = placeholders[i];
                if (placeholder != null){
                    message = message.replace(placeholder, v);
                    placeholder = null;
                    continue;
                }
                placeholder = "${"+v+"}";
            }
        }
        player.sendMessage(message);
    }
}
