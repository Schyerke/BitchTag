package bitchtag.bitchtag.listeners;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.gui.CompassTrackerInventoryHolder;
import bitchtag.bitchtag.gui.OptionsInventoryHolder;
import bitchtag.bitchtag.managers.GameManager;
import bitchtag.bitchtag.managers.GameState;
import bitchtag.bitchtag.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class ItemsTriggerEvents implements Listener {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final List<String> cooldownPlayers;
    public static final int DEFAULT_COOLDOWN_SECONDS = 3;

    private final Bitchtag plugin;
    private final ItemManager itemManager;
    private final GameManager gameManager;

    public ItemsTriggerEvents(Bitchtag plugin, ItemManager itemManager, GameManager gameManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
        this.gameManager = gameManager;

        this.cooldownPlayers = new ArrayList<>();
    }

    @EventHandler
    public void startGame(PlayerInteractEvent e) {
        if (gameManager.getGameState() != GameState.LOBBY) {
            logger.info("Not in lobby. State: " + gameManager.getGameState().name());
            return;
        }
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (!item.equals(itemManager.getItem(ItemManager.START_GAME))) {
            logger.warning("sword is not start game item");
            return;
        }
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
            logger.warning("action is not right click");
            return;
        }
        Player player = e.getPlayer();

        if (checkPlayerOnCooldown(player)) return;

        gameManager.addOnQueue(player);
        gameManager.setGameState(GameState.STARTING);
    }

    private boolean checkPlayerOnCooldown(Player player) {
        if (isPlayerOnCooldown(player)) {
            logger.info(player.getName() + " is in cooldown...");
            player.sendMessage(ChatColor.RED + "You have to wait " + DEFAULT_COOLDOWN_SECONDS + " seconds to perform this command!");
            return true;
        } else {
            this.cooldownPlayers.add(player.getName());
            logger.info("Player: " + player.getName() + " is not on cooldown!");
            Bukkit.getScheduler().runTaskLater(
                    Bitchtag.getInstance(),
                    () -> {
                        this.cooldownPlayers.remove(player.getName());
                        logger.info(player.getName() + " is not in cooldown anymore!");
                    }
                    , DEFAULT_COOLDOWN_SECONDS * 20L);
        }
        return false;
    }

    @EventHandler
    public void changeGameLastMinutes(PlayerInteractEvent e) {
        if (gameManager.getGameState() != GameState.LOBBY) {
            logger.info("Not in lobby. State: " + gameManager.getGameState().name());
            return;
        }
        Player player = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (!item.equals(itemManager.getItem(ItemManager.CHANGE_GAME_LAST_MINUTES))) {
            logger.warning("stick is not change game last minutes item");
            return;
        }
        if (checkPlayerOnCooldown(player)) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            logger.info("added 5 minutes to game last minutes");
            gameManager.add5MinuteLast();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(text("Game will end in " + gameManager.getMinuteLast() + " minutes"));
            item.setItemMeta(itemMeta);
            player.updateInventory();
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
            logger.info("removed 5 minutes to game last minutes");
            gameManager.remove5MinuteLast();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(text("Game will end in " + gameManager.getMinuteLast() + " minutes"));
            item.setItemMeta(itemMeta);
            player.updateInventory();
        } else {
            logger.warning("action is not right or left click");
        }
        itemManager.updateItem(ItemManager.CHANGE_GAME_LAST_MINUTES, item);
    }

    // on hunter compass inventory open
    @EventHandler
    public void hunterCompass(PlayerInteractEvent e) {
        if (gameManager.getGameState() != GameState.IN_GAME) {
            logger.info("Not in game. State: " + gameManager.getGameState().name());
            return;
        }
        Player player = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

        logger.info("Checking if the item is the compass...");
        if (item.getType() == Material.COMPASS) {
            logger.info("Item is the compass!");
            logger.info("Checking if the action is right click...");
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                logger.info("Action is right click!");
                logger.info("Opening the inventory...");

                CompassTrackerInventoryHolder compassTrackerInventoryHolder = new CompassTrackerInventoryHolder(
                        Bitchtag.getInstance(),
                        gameManager,
                        3,
                        "Compass Tracker"
                );
                Inventory inventory = compassTrackerInventoryHolder.getInventory();
                player.openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void optionView(PlayerInteractEvent event) {
        if (gameManager.getGameState() != GameState.LOBBY) {
            logger.info("Not in lobby. State: " + gameManager.getGameState().name());
            return;
        }
        logger.info("checking if its right click...");
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            logger.info("it is a right click!");
            logger.info("Checking if its a redstone torch...");
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.REDSTONE_TORCH) {
                logger.info("It is a redstone torch!");
                Player player = event.getPlayer();
                logger.info("getting the inventory...");
                OptionsInventoryHolder optionsInventoryHolder = new OptionsInventoryHolder(plugin, 3, "Option view");
                logger.info("opening the inventory to player: " + player.getName());
                player.openInventory(optionsInventoryHolder.getInventory());
            }
        }


    }

    private boolean isPlayerOnCooldown(Player player) {
        logger.info("Checking if " + player.getName() + " is on cooldown...");
        if (this.cooldownPlayers.contains(player.getName())) {
            logger.info("Player: " + player.getName() + " is on cooldown!");
            return true;
        }
        return false;
    }
}
