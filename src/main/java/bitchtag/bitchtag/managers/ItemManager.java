package bitchtag.bitchtag.managers;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

import static bitchtag.bitchtag.helpers.ItemHelper.createItem;

public class ItemManager {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final HashMap<String, ItemStack> items;

    public static final String START_GAME = "start_game";
    public static final String CHANGE_GAME_LAST_MINUTES = "change_game_last_minutes";
    public static final String COMPASS_TRACKER = "compass_tracker";
    public static final String OPTION_VIEW = "option_view";

    private final Bitchtag plugin;

    public ItemManager(Bitchtag plugin) {
        this.plugin = plugin;

        logger.info("Initializing items...");
        this.items = new HashMap<>();
        logger.info("Adding start game item...");
        logger.info("Adding start game item to items...");
        items.put(START_GAME, createItem(
                Material.DIAMOND_SWORD,
                "Start a Game!",
                "Click me to start a game!"
        ));
        logger.info("Adding change game last minutes item...");
        items.put(CHANGE_GAME_LAST_MINUTES, createItem(
                        Material.STICK,
                        "Game will end in " + GameManager.DEFAULT_LAST_MINUTES + " minutes",
                "Click me to change the last minutes of the game!"
        ));
        logger.info("Adding compass tracker item...");
        items.put(COMPASS_TRACKER, createItem(
                Material.COMPASS,
                "Click me to track a player!",
                "This compass is used to track players!")
        );

        items.put(OPTION_VIEW, createItem(
                Material.REDSTONE_TORCH,
                "Click me to modify the option games",
                "Options"
        ));
        logger.info("Items initialized!");
    }

    public ItemStack getItem(String key){
        return items.get(key);
    }

    public ItemStack[] getStartItems() {
        logger.info("returning items on player join...");
        return new ItemStack[]{
                items.get(START_GAME),
                items.get(OPTION_VIEW)
        };
    }

    public void updateItem(String key, ItemStack item) {
        logger.info("Updating item " + key + "...");
        items.put(key, item);
    }
}
