package bitchtag.bitchtag.listeners;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.managers.GameManager;
import bitchtag.bitchtag.managers.GameState;
import bitchtag.bitchtag.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class PlayerEvents implements Listener {

    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Bitchtag plugin;
    private final GameManager gameManager;
    private final PlayerManager playerManager;

    public PlayerEvents(Bitchtag plugin, GameManager gameManager, PlayerManager playerManager){
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        logger.info("Checking if entity is a player...");
        if(e.getEntity() instanceof Player){
            logger.info("Entity is a player!");
            logger.info("Canceling the damage...");
            e.setDamage(0);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if(!gameManager.isGameStarted()){
            logger.info("Game has not started yet!");
            return;
        }
        e.setDamage(0);
        logger.info("Checking if entities are players...");
        if(e.getEntity() instanceof Player victim && e.getDamager() instanceof Player damager){
            logger.info("Entities are players!");
            gameManager.handleHits(victim, damager);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        logger.info("Teleporting " + e.getPlayer().getName() + " to spawn...");
        playerManager.teleportToSpawn(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        logger.info(ChatColor.RED + e.getPlayer().getName() + " has left the game!");
        Bukkit.broadcast(text(ChatColor.RED + e.getPlayer().getName() + " has left the game!"));
        if(gameManager.getGameState() != GameState.IN_GAME){
            return;
        }
        gameManager.setGameState(GameState.ENDING);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(gameManager.getGameState() == GameState.LOBBY){
            event.setCancelled(true);
        }
    }
}
