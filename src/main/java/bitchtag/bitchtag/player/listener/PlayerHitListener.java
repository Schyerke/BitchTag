package bitchtag.bitchtag.player.listener;

import bitchtag.bitchtag.game.GameHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        e.setDamage(0);
        if(e.getEntity() instanceof Player player && e.getDamager() instanceof Player damager){
            if(GameHelper.INSTANCE.isBitch(player)){
                return;
            }
            if(GameHelper.INSTANCE.isBitch(damager)){
                GameHelper.INSTANCE.addBitch(player);
                GameHelper.INSTANCE.removeBitch(damager);
            }
        }
    }
}
