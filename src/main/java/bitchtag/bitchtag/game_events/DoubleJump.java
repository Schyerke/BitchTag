package bitchtag.bitchtag.game_events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {
    private final List<String> players = new ArrayList<String>();;

    @EventHandler
    public void setFly(PlayerJoinEvent e) {
        e.getPlayer().setAllowFlight(true);
        e.getPlayer().setFlying(false);

    }

    @EventHandler
    public void setVelocity(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();

        if (!(player.getGameMode() == GameMode.CREATIVE
                || player.getGameMode() == GameMode.SPECTATOR
                || player.isFlying()
                || players.contains(player.getName()))
        ) {
            return;
        }
        players.add(player.getName());
        e.setCancelled(true);

        player.setAllowFlight(false);
        player.setFlying(false);

        player.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, -5.0f);
        player.setFallDistance(100);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player && e.getCause() == DamageCause.FALL) {
            if (players.contains(player.getName())) {
                e.setCancelled(true);
                players.remove(player.getName());
                player.setAllowFlight(true);

            }
        }
    }

    @EventHandler
    public void removePlayer(PlayerQuitEvent e) {
        players.remove(e.getPlayer().getName());
    }

}
