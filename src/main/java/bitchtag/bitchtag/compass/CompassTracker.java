package bitchtag.bitchtag.compass;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CompassTracker {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Player runner;
    private final Player hunter;
    private int compassTrackingTaskId;

    public CompassTracker(Player runner, Player hunter){
        this.runner =  runner;
        this.hunter = hunter;
    }

    public Player getHunter() {
        return hunter;
    }

    public Player getRunner() {
        return runner;
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(this.compassTrackingTaskId);
    }

    public void startTracking(){
        try{
            stop();
        }
        catch (Exception e){
            System.out.println("Compass tracker was not running");
        }
        logger.info("Runner: " + runner.getName());
        logger.info("Hunter: " + hunter.getName());
        this.compassTrackingTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bitchtag.getInstance(),
                () -> hunter.setCompassTarget(runner.getLocation()), 0, 20);
    }
}
