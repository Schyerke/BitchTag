package bitchtag.bitchtag.game_events.hit;

import bitchtag.bitchtag.game.GameHelper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
    
    @EventHandler
    public void onBowHit(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow){
            return;
        }
        Player playerShooter = (Player) e.getEntity().getShooter();
        Player hitPlayer = (Player) e.getHitEntity();


        GameHelper gameHelper = GameHelper.INSTANCE;
        if(gameHelper.isBitch(playerShooter) && gameHelper.isBitch(hitPlayer)){
            gameHelper.swapBitches(playerShooter, hitPlayer);
        }
    }

    @EventHandler
    public void onEggOrSnowballHit(ProjectileHitEvent e){
        if(e.getEntity() instanceof Egg || e.getEntity() instanceof Snowball){
            return;
        }
        GameHelper gameHelper = GameHelper.INSTANCE;
        Player playerShooter = (Player) e.getEntity().getShooter();
        Player hitPlayer = (Player) e.getHitEntity();
        if(gameHelper.isBitch(playerShooter) && gameHelper.isBitch(hitPlayer)){
            gameHelper.swapBitches(playerShooter, hitPlayer);
        }
    }
}
