package bitchtag.bitchtag.item;

import bitchtag.bitchtag.game.GameMinuteLastHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;

public enum ItemHelper {
    INSTANCE;

    public ItemStack createItem(Material material, String name, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(text(name));
        itemMeta.lore(Arrays.stream(lore).map(Component::text).collect(Collectors.toList()));
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getCompassTracker(){
        return createItem(Material.COMPASS, ItemNames.COMPASS_TRACKER.getName(), "Click me to track a player!");
    }

    public ItemStack[] getItemsOnPlayerJoin() {
        return new ItemStack[]{
                ItemHelper.INSTANCE.createItem(
                        Material.DIAMOND_SWORD,
                        ItemNames.START_GAME.getName(),
                        "Click me to start a game!"
                ),
                ItemHelper.INSTANCE.createItem(
                        Material.STICK,
                        ItemNames.GAME_LENGTH.getName(GameMinuteLastHelper.INSTANCE.getMinuteLast()),
                        "Click me to choose how long the game should last!"
                )
        };
    }
}
