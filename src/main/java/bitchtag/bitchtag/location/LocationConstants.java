package bitchtag.bitchtag.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationConstants {
    @SuppressWarnings("all")
    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0, Bukkit.getWorld("world").getHighestBlockYAt(0, 0), 0);
}
