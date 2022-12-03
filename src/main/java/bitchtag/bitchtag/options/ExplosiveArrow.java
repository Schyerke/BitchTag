package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class ExplosiveArrow implements Listener, OptionListener {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private static final List<Component> ARROW_LORE = List.of(text("This arrow is a bit TOO MUCH LOL"));
    private static final List<Component> BOW_LORE = List.of(text("With this bow... you can make pasta!"));

    private final Bitchtag plugin;
    public ExplosiveArrow(Bitchtag plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Arrow arrow && event.getEntity().getShooter() instanceof Player) {
            Location location = arrow.getLocation();

            World world = location.getWorld();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            world.createExplosion(
                    x,
                    y,
                    z,
                    10, //size
                    false, // set fire
                    true); //block break
        }
    }

    @EventHandler
    public void onKitItemDrop(PlayerDropItemEvent event){
        ItemMeta itemMeta = event.getItemDrop().getItemStack().getItemMeta();
        if(itemMeta == null){
            logger.info("Item meta is null!!!");
            return;
        }
        if(itemMeta.lore() == null){
            logger.info("the lore of this item is null!!");
            return;
        }
        //compare the lore of the item dropped with the lore of the items in the kit
        if(Objects.equals(itemMeta.lore(), BOW_LORE) || Objects.equals(itemMeta.lore(), ARROW_LORE)){
            event.getPlayer().sendMessage(ChatColor.RED + "You can't drop this item!");
            event.setCancelled(true);
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
            bowMeta.displayName(text("Bow bow, bau bau"));
            bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            bow.setItemMeta(bowMeta);

            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.lore(ARROW_LORE);
            arrowMeta.displayName(text(ChatColor.RED + "An arrow inside your ear"));
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
