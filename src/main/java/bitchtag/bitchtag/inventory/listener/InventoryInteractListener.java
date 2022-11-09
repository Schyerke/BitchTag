package bitchtag.bitchtag.inventory.listener;

import bitchtag.bitchtag.compass.CompassTracker;
import bitchtag.bitchtag.inventory.InventoryTitles;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class InventoryInteractListener implements Listener {
    // on hunter compass inventory click (preventing getting items and fire event)
    @EventHandler
    public void onHunterCompassInventoryClick(InventoryClickEvent event){
        if(event.getView().title().examinableName().equals(InventoryTitles.COMPASS_TRACKER_INVENTORY_TITLE.getTitle())){
            event.setCancelled(true);
            final ItemStack itemClicked = event.getCurrentItem();
            if (itemClicked == null || itemClicked.getType().isAir()) {
                return;
            }
            final CompassTracker compassTracker = CompassTracker.getInstance();

            Player playerWhoClicked = (Player) event.getWhoClicked();

            playerWhoClicked.closeInventory();

            ItemMeta itemMeta = itemClicked.getItemMeta();
            OfflinePlayer offlinePlayer = ((SkullMeta)itemMeta).getOwningPlayer();
            if(offlinePlayer == null){
                return;
            }

            Player player = Bukkit.getPlayer(Objects.requireNonNull(offlinePlayer.getName()));
            compassTracker.startTracking(player, playerWhoClicked);
        }
    }

}
