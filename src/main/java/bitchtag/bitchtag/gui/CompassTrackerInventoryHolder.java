package bitchtag.bitchtag.gui;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.compass.CompassTracker;
import bitchtag.bitchtag.gui.icon.Icon;
import bitchtag.bitchtag.helpers.ItemHelper;
import bitchtag.bitchtag.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class CompassTrackerInventoryHolder implements InventoryHolder {
    private Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Bitchtag plugin;
    private final GameManager gameManager;

    private final int size;
    private final String title;
    private final Map<Integer, Icon> icons = new HashMap<>();

    public CompassTrackerInventoryHolder(Bitchtag plugin, GameManager gameManager, int size, String title){
        this.plugin = plugin;
        this.gameManager = gameManager;

        this.size = size;
        this.title = title;
    }

    public void setIcon(int position, Icon icon) {
        this.icons.put(position, icon);
    }

    public Icon getIcon(int position) {
        return this.icons.get(position);
    }

    @Override
    public @NotNull Inventory getInventory() {
        logger.info("Getting compass tracker inventory...");
        Inventory inventory = Bukkit.createInventory(this, 9*this.size, text(this.title));

        // get runners' minecraft skin and create a head item with its skin and add it to the inventory
        logger.info("Adding no bitches' heads to compass tracker inventory...");
        int i = 0;
        for(Player playerClicked : Bukkit.getOnlinePlayers()){
            if(gameManager.isBitch(playerClicked)){
                continue;
            }
            logger.info(playerClicked.getName() + " head added");
            Icon icon = new Icon(ItemHelper.getHead(playerClicked)).addClickAction(player -> {
                logger.info("Start tracking: " + ChatColor.RED + playerClicked.getName() + " by " + player.getName());
                CompassTracker compassTracker = new CompassTracker(player, playerClicked);
                compassTracker.startTracking();
            });
            this.icons.put(i, icon);
            inventory.setItem(i, icon.itemStack);
            i++;
        }
        logger.info("Returning the inventory");
        return inventory;
    }
}
