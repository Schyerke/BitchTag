package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveEggs implements Listener, OptionListener {

    private final Bitchtag plugin;
    public ExplosiveEggs(Bitchtag plugin){
        this.plugin = plugin;
    }

    //make an explosive egg
    @EventHandler
    public void onEggThrown(ProjectileHitEvent event){
        if(event.getEntity() instanceof Egg egg && event.getEntity().getShooter() instanceof Player) {
            Location location = egg.getLocation();

            World world = location.getWorld();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            world.createExplosion(
                    x,
                    y,
                    z,
                    4, //size
                    false, // set fire
                    true); //block break
        }
    }

    @Override
    public void init() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void disable(){
        HandlerList.unregisterAll(this);
    }
}
