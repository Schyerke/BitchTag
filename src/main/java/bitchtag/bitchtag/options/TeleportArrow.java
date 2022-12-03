package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class TeleportArrow implements Listener, OptionListener {

    private static final List<Component> BOW_LORE = List.of(text("Bow, or enderbow?"));
    private static final List<Component> ARROW_LORE = List.of(text("An arrow inside your neck"));

    private final Bitchtag plugin;

    public TeleportArrow(Bitchtag plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onArrowShot(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Player player && event.getEntity() instanceof Arrow arrow){
            Location location = arrow.getLocation();
            location.setPitch(player.getLocation().getPitch());
            location.setYaw(player.getLocation().getYaw());
            player.teleport(location);
        }
    }

    @Override
    public void init() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getOnlinePlayers().forEach(player -> {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setUnbreakable(true);
            bowMeta.lore(BOW_LORE);
            bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            bow.setItemMeta(bowMeta);

            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.lore(ARROW_LORE);
            arrowMeta.displayName(text(ChatColor.RED + "The weirdest arrow in the world!"));
            arrow.setItemMeta(arrowMeta);

            player.getInventory().addItem(bow, arrow);
        });
    }

    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void disable(){
        HandlerList.unregisterAll(this);
    }
}
