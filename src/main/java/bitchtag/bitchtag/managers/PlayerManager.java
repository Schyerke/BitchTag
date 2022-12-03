package bitchtag.bitchtag.managers;

import bitchtag.bitchtag.Bitchtag;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class PlayerManager {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Bitchtag plugin;
    private final ItemManager itemManager;

    public PlayerManager(Bitchtag plugin, ItemManager itemManager){
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    public void teleportToSpawn(Player player) {
        player.teleport(Bitchtag.getInstance().getDefaultWorld().getSpawnLocation());
        logger.info("Teleported " + player.getName() + " to spawn!");
        player.getInventory().clear();
        logger.info("Cleared " + player.getName() + "'s inventory!");
        player.setInvulnerable(true);
        logger.info("Set " + player.getName() + " invulnerable!");
        player.setHealth(20);
        logger.info("Set " + player.getName() + "'s health to 20!");
        player.setFoodLevel(20);
        logger.info("Set " + player.getName() + "'s food level to 20!");
        player.setAllowFlight(true);
        logger.info("Set " + player.getName() + " flight to true!");
        player.showTitle(Title.title(text(ChatColor.RED + "Welcome!"), text(ChatColor.YELLOW + "You are now playing BitchTag!")));

        player.getInventory().addItem(itemManager.getStartItems());
        logger.info("Added items to " + player.getName() + "'s inventory!");
        logger.info("Checking if the player is SpringFramework...");
        if(player.displayName().equals(text("SpringFramework"))){
            logger.info("The player is SpringFramework!");
            logger.info("Giving SpringFramework the stick to change game minutes item!");
            player.getInventory().addItem(itemManager.getItem(ItemManager.CHANGE_GAME_LAST_MINUTES));
        }
    }

}
