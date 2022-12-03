package bitchtag.bitchtag.listeners;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.gui.CompassTrackerInventoryHolder;
import bitchtag.bitchtag.gui.OptionsInventoryHolder;
import bitchtag.bitchtag.gui.icon.ClickAction;
import bitchtag.bitchtag.gui.icon.Icon;
import bitchtag.bitchtag.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class InventoryItemsTriggerEvents implements Listener {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();
    // on hunter compass inventory click (preventing getting items and fire event)

    private final ItemManager itemManager;

    public InventoryItemsTriggerEvents(ItemManager itemManager){
        this.itemManager = itemManager;
    }

    @EventHandler
    public void compassTrackingPlayer(InventoryClickEvent event){
        logger.info(event.getView().getTopInventory().getHolder().toString() + " this is the holder");
        logger.info("Checking if the inventory is the compass tracker inventory...");
        if(event.getView().getTopInventory().getHolder() instanceof CompassTrackerInventoryHolder compassTrackerInventoryHolder){
            logger.info("Inventory is the compass tracker inventory!");
            logger.info("Canceling the event...");
            event.setCancelled(true);
            logger.info("Checking if the clicked item is a player head...");
            final ItemStack itemClicked = event.getCurrentItem();
            if (itemClicked == null || itemClicked.getType().isAir()) {
                logger.info("Clicked item is not a player head!");
                return;
            }

            Player playerWhoClicked = (Player) event.getWhoClicked();
            logger.info("Closing the inventory...");
            playerWhoClicked.closeInventory();

            //Check if the clicked slot is any icon
            Icon icon = compassTrackerInventoryHolder.getIcon(event.getRawSlot());
            if (icon == null) return;

            //Execute all the actions
            for (ClickAction clickAction : icon.getClickActions()) {
                clickAction.execute(playerWhoClicked);
            }
        }
    }

    @EventHandler
    public void optionView(InventoryClickEvent event){
        logger.info(event.getView().getTopInventory().getHolder().toString() + " this is the holder");
        logger.info("Checking if the inventory is the option inventory...");
        if(event.getView().getTopInventory().getHolder() instanceof OptionsInventoryHolder optionsInventoryHolder){
            logger.info("The inventory is the option inventory!");
            logger.info("Canceling the event...");
            event.setCancelled(true);
            final ItemStack itemClicked = event.getCurrentItem();
            if (itemClicked == null || itemClicked.getType().isAir()) {
                logger.info("Clicked item is not a valid item");
                return;
            }

            Player playerWhoClicked = (Player) event.getWhoClicked();

            //Check if the clicked slot is any icon
            Icon icon = optionsInventoryHolder.getIcon(event.getRawSlot());
            if (icon == null) return;

            //Execute all the actions
            for (ClickAction clickAction : icon.getClickActions()) {
                clickAction.execute(playerWhoClicked);
            }

            logger.info("Opening the updated option inventory to everyone!");
            Bukkit.getOnlinePlayers().forEach(player -> {
                InventoryHolder topInventoryHolder = player.getOpenInventory().getTopInventory().getHolder();
                if(topInventoryHolder instanceof OptionsInventoryHolder){
                    logger.info(player.getName() + " updated his inventory!");
                    OptionsInventoryHolder optionsInventoryHolderUpdated = new OptionsInventoryHolder(Bitchtag.getInstance(), 3, "Option view");
                    logger.info("opening the inventory to player: " + player.getName());
                    player.openInventory(optionsInventoryHolderUpdated.getInventory());

                }
            });
        }
    }
}

