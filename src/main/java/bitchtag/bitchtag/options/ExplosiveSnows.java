package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveSnows implements Listener, OptionListener {

    private final Bitchtag plugin;

    public ExplosiveSnows(Bitchtag plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballThrown(ProjectileHitEvent event){
        if(event.getEntity() instanceof Snowball snowball && event.getEntity().getShooter() instanceof Player) {
            Location location = snowball.getLocation();

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
