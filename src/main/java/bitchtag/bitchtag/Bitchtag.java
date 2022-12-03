package bitchtag.bitchtag;

import bitchtag.bitchtag.commands.LeaveCommand;
import bitchtag.bitchtag.commands.OptionCommand;
import bitchtag.bitchtag.listeners.InventoryItemsTriggerEvents;
import bitchtag.bitchtag.listeners.ItemsTriggerEvents;
import bitchtag.bitchtag.listeners.PlayerEvents;
import bitchtag.bitchtag.managers.GameManager;
import bitchtag.bitchtag.managers.ItemManager;
import bitchtag.bitchtag.managers.PlayerManager;
import bitchtag.bitchtag.options.DoubleJump;
import bitchtag.bitchtag.world.PlayerTravelWorldsListener;
import bitchtag.bitchtag.world.WorldTraveler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Bitchtag extends JavaPlugin {
    private static Bitchtag instance;
    public static Bitchtag getInstance() {
        return instance;
    }

    private static final boolean DEBUG = false;

    private WorldTraveler worldTraveler;

    private GameManager gameManager;
    private ItemManager itemManager;
    private PlayerManager playerManager;

    private World world;

    @Override
    public void onEnable() {
        instance = this;
        init();
        managers();

        //commands
        initCommands();

        //listeners
        initListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void managers(){
        this.itemManager = new ItemManager(instance);
        this.playerManager = new PlayerManager(instance, itemManager);
        this.gameManager = new GameManager(instance, playerManager, worldTraveler);
    }

    @SuppressWarnings("all")
    private void initCommands(){
        getCommand("l").setExecutor(new LeaveCommand(instance, gameManager));
        getCommand("leave").setExecutor(new LeaveCommand(instance, gameManager));
        getCommand("option").setExecutor(new OptionCommand());
    }

    private void initListeners(){
        getServer().getPluginManager().registerEvents(new PlayerEvents(instance, gameManager, playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerTravelWorldsListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryItemsTriggerEvents(itemManager), this);
        getServer().getPluginManager().registerEvents(new ItemsTriggerEvents(instance, itemManager, gameManager), this);
        if(DEBUG) {
            new DoubleJump(instance).init();
        }
    }

    private void init(){
        this.worldTraveler = new WorldTraveler("./worlds_generated/");
        this.world = Bukkit.getWorld("world");
    }

    public @NotNull WorldTraveler getWorldTraveler(){
        return this.worldTraveler;
    }

    public World getDefaultWorld() {
        return world;
    }
}
