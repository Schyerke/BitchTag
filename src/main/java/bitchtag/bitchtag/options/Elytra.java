package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class Elytra implements Listener, OptionListener {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private static final List<Component> BOW_LORE = List.of(text("With this bow... you can do a lot of things!"));
    private static final List<Component> ARROW_LORE = List.of(text("This arrow is a bit weird"));
    private static final List<Component> ELYTRA_LORE = List.of(text("Fly to the moon!"));

    private final Bitchtag plugin;
    public Elytra(Bitchtag plugin){
        this.plugin = plugin;
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
        if(Objects.equals(itemMeta.lore(), ELYTRA_LORE)
                || Objects.equals(itemMeta.lore(), BOW_LORE)
                || Objects.equals(itemMeta.lore(), ARROW_LORE)
                || event.getItemDrop().getItemStack().getType() == Material.FIREWORK_ROCKET
        ){
            event.getPlayer().sendMessage(ChatColor.RED + "You can't drop this item!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void fireworkRocketConsume(PlayerInteractEvent event){
        // make the firework infinite
        if(event.getItem() != null && event.getItem().getType() == Material.FIREWORK_ROCKET){
            event.getItem().setAmount(64);
        }
    }

    @Override
    public void init() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getOnlinePlayers().forEach(player -> {
            ItemStack elytra = new ItemStack(Material.ELYTRA);
            ItemMeta elytraMeta = elytra.getItemMeta();
            elytraMeta.setUnbreakable(true);
            elytraMeta.lore(ELYTRA_LORE);
            elytra.setItemMeta(elytraMeta);

            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setUnbreakable(true);
            bowMeta.lore(BOW_LORE);
            bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            bow.setItemMeta(bowMeta);

            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.lore(ARROW_LORE);
            arrowMeta.displayName(text(ChatColor.RED + "The best arrow in the world!"));
            arrow.setItemMeta(arrowMeta);

            ItemStack fireworkRocket = new ItemStack(Material.FIREWORK_ROCKET, 64);

            player.getInventory().addItem(elytra, bow, arrow, fireworkRocket);
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
