package bitchtag.bitchtag.player.listener;

import bitchtag.bitchtag.player.PlayerHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        PlayerHelper.INSTANCE.teleportSpawn(e.getPlayer());
    }
}
