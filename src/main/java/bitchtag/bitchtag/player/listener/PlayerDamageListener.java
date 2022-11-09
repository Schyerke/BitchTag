package bitchtag.bitchtag.player.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player player){
            e.setDamage(0);
        }
    }
}
