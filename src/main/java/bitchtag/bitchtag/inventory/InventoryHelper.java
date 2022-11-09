package bitchtag.bitchtag.inventory;

import bitchtag.bitchtag.game.GameHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static net.kyori.adventure.text.Component.text;

public enum InventoryHelper{
    INSTANCE;


    public Inventory getCompassTrackerInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9*3, text(InventoryTitles.COMPASS_TRACKER_INVENTORY_TITLE.getTitle()));

        // get runners' minecraft skin and create a head item with its skin and add it to the inventory
        for(Player bitch : GameHelper.INSTANCE.getBitches()){
            inventory.addItem(getHead(bitch));
        }
        return inventory;
    }

    public static ItemStack getHead(Player player){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(player);

        skull.setItemMeta(skullMeta);
        return skull;
    }

}
