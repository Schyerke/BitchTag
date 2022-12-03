package bitchtag.bitchtag.options;

import bitchtag.bitchtag.Bitchtag;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WeirdKnockback implements Listener, OptionListener {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();
    private final Bitchtag plugin;

    public WeirdKnockback(Bitchtag plugin){
        this.plugin = plugin;
    }


    @Override
    public void init() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
