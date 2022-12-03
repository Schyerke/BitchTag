package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener, OptionListener{
    private final Bitchtag plugin;

    public DoubleJump(Bitchtag plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(15).setY(1));
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, -5.0f);
            player.setAllowFlight(true);
        }
    }

    @Override
    public void init() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setAllowFlight(true);
            player.setFlying(false);
        });
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
