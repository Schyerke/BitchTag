package bitchtag.bitchtag.inventory.listener;

import bitchtag.bitchtag.inventory.InventoryHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static bitchtag.bitchtag.item.ItemNames.COMPASS_TRACKER;

public class InventoryOpenTriggerListener implements Listener {
    // on hunter compass inventory open
    @EventHandler
    public void onHunterCompassInventoryOpen(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (item==null)return;
        if(Objects.requireNonNull(item.getItemMeta().displayName()).examinableName().equals(COMPASS_TRACKER.getName())){
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                player.openInventory(InventoryHelper.INSTANCE.getCompassTrackerInventory());
            }
        }
    }
}
