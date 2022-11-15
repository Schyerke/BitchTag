package bitchtag.bitchtag.compass;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CompassTracker {
    private Player playerTracked;
    private  Player playerTracking;
    private int compassTrackingTaskId;

    private static CompassTracker instance;
    public static CompassTracker getInstance() {
        if(instance == null){
            instance = new CompassTracker();
        }
        return instance;
    }

    private CompassTracker(){
    }

    public Player getPlayerTracking() {
        return playerTracking;
    }

    public Player getPlayerTracked() {
        return playerTracked;
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(this.compassTrackingTaskId);
    }
    

    public void startTracking(Player target, Player player){
        if(player == null || target == null){
            return;
        }
        try{
            stop();
        }
        catch (Exception e){
            System.out.println("Compass tracker was not running");
        }
        this.playerTracking = player;
        this.playerTracked = target;
        this.compassTrackingTaskId = Bukkit.getScheduler()
                .scheduleSyncRepeatingTask(Bitchtag.getInstance(),
                        () -> playerTracking.setCompassTarget(playerTracked.getLocation()),
                        0, 20
                );

    }
}
