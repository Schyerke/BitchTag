package bitchtag.bitchtag.helpers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHelper {
    private PlayerHelper() {
    }

    public static Player getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player == null) {
            throw new IllegalArgumentException("Player not found");
        }
        return player;
    }
}
