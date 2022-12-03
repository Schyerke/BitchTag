package bitchtag.bitchtag.gui;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.gui.icon.Icon;
import bitchtag.bitchtag.helpers.ItemHelper;
import bitchtag.bitchtag.managers.Options;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class OptionsInventoryHolder implements InventoryHolder {
    private static final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Map<Integer, Icon> icons = new HashMap<>();

    private final Bitchtag plugin;
    private final int size;
    private final String title;

    public OptionsInventoryHolder(Bitchtag plugin, int size, String title){
        this.plugin = plugin;
        this.size = size;
        this.title = title;
    }

    public void setIcon(int position, Icon icon){
        this.icons.put(position, icon);
    }

    public Icon getIcon(int position){
        return this.icons.get(position);
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 9*size, text(title));
        int i = 0;
        for(Icon icon : getAllOptionIcons()){
            this.icons.put(i, icon);
            inventory.setItem(i, icon.itemStack);
            i++;
        }
        return inventory;
    }

    private List<Icon> getAllOptionIcons(){
        //weird knockback
        Icon weirdKnockback = new Icon(ItemHelper.createItem(Material.STICK, "Weird knockback", "Set to: " + Options.isWeirdKnockback()))
                .addClickAction(player -> Options.toggleWeirdKnockback());
        //explosive arrow
        Icon explosiveArrow = new Icon(ItemHelper.createItem(Material.TNT, "Explosive arrows!", "Set to: " + Options.isExplosiveArrow()))
                .addClickAction(player -> Options.toggleExplosiveArrow());
        //teleport arrow
        Icon teleportArrow = new Icon(ItemHelper.createItem(Material.ENDER_PEARL, "Teleport arrow!", "Set to: " +Options.isTeleportArrow()))
                .addClickAction(player -> Options.toggleTeleportArrow());
        //Explosive snows
        Icon explosiveSnows = new Icon(ItemHelper.createItem(Material.SNOWBALL, "Explosive snows!", "Set to: " + Options.isExplosiveSnows()))
                .addClickAction(player -> Options.toggleExplosiveSnows());
        //Explosive eggs
        Icon explosiveEggs = new Icon(ItemHelper.createItem(Material.EGG, "Explosive eggs!", "Set to: " + Options.isExplosiveEggs()))
                .addClickAction(player -> Options.toggleExplosiveEggs());
        //Double jump
        Icon doubleJump = new Icon(ItemHelper.createItem(Material.FEATHER, "Double jump!", "Set to: " + Options.isDoubleJump()))
                .addClickAction(player -> Options.toggleDoubleJump());
        //Elytra
        Icon elytra = new Icon(ItemHelper.createItem(Material.ELYTRA, "Elytra!", "Set to: " + Options.isElytra()))
                .addClickAction(player -> Options.toggleElytra());
        //Type of world generation
        Icon worldTypeGeneration = new Icon(ItemHelper.createItem(Material.GRASS_BLOCK, "World type generation!", "Set to: " + Options.getWorldTypeGeneration().name()))
                .addClickAction(player -> {
                    switch(Options.getWorldTypeGeneration()){
                        case NORMAL -> Options.setWorldTypeGeneration(WorldType.AMPLIFIED);
                        case AMPLIFIED -> Options.setWorldTypeGeneration(WorldType.LARGE_BIOMES);
                        case LARGE_BIOMES -> Options.setWorldTypeGeneration(WorldType.NORMAL);
                    }
                });
        return List.of(weirdKnockback, explosiveArrow, teleportArrow, explosiveSnows, explosiveEggs, doubleJump, elytra, worldTypeGeneration);
    }
}
