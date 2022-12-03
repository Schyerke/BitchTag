package bitchtag.bitchtag.helpers;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;

public class ItemHelper {
    public static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(text(name));
        itemMeta.lore(Arrays.stream(lore).map(Component::text).collect(Collectors.toList()));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getHead(Player player){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(player);

        skull.setItemMeta(skullMeta);
        return skull;
    }
}
