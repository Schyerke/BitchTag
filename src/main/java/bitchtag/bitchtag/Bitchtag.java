package bitchtag.bitchtag;

import bitchtag.bitchtag.inventory.listener.InventoryInteractListener;
import bitchtag.bitchtag.inventory.listener.InventoryOpenTriggerListener;
import bitchtag.bitchtag.item.listener.ItemInteractListener;
import bitchtag.bitchtag.player.listener.FoodLevelListener;
import bitchtag.bitchtag.player.listener.PlayerDamageListener;
import bitchtag.bitchtag.player.listener.PlayerJoinListener;
import bitchtag.bitchtag.world.listeners.PlayerTravelWorldsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bitchtag extends JavaPlugin {
    private static Bitchtag instance;
    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTravelWorldsListener(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryOpenTriggerListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
