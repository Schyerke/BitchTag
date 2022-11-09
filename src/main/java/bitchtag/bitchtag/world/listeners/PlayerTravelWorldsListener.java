package bitchtag.bitchtag.world.listeners;

import bitchtag.bitchtag.world.WorldHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTravelWorldsListener implements Listener {
    private final WorldHelper worldHelper = WorldHelper.INSTANCE;

    @EventHandler
    public void onPlayerPortalTeleport(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        World overWorld = worldHelper.getCurrentOverWorld();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            event.setCanCreatePortal(true);
            event.setCreationRadius(3);
            World netherWorld = worldHelper.getCurrentNetherWorld();
            Location location;
            if (player.getWorld() == overWorld) {
                location = new Location(netherWorld, (float)event.getFrom().getBlockX() / 8, event.getFrom().getBlockY(), (float)event.getFrom().getBlockZ() / 8);
            } else {
                location = new Location(overWorld, event.getFrom().getBlockX() * 8, event.getFrom().getBlockY(), event.getFrom().getBlockZ() * 8);
            }
            event.setTo(location);
        }
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            if (player.getWorld() == overWorld) {
                World endWorld = worldHelper.getCurrentEndWorld();
                Location loc = new Location(endWorld, 100, 50, 0); // This is the vanilla location for obsidian platform.
                event.setTo(loc);
                Block block = loc.getBlock();
                for (int x = block.getX() - 2; x <= block.getX() + 2; x++) {
                    for (int z = block.getZ() - 2; z <= block.getZ() + 2; z++) {
                        Block platformBlock = loc.getWorld().getBlockAt(x, block.getY() - 1, z);
                        if (platformBlock.getType() != Material.OBSIDIAN) {
                            platformBlock.setType(Material.OBSIDIAN);
                        }
                        for (int yMod = 1; yMod <= 3; yMod++) {
                            Block b = platformBlock.getRelative(BlockFace.UP, yMod);
                            if (b.getType() != Material.AIR) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
        else if (player.getWorld() == worldHelper.getCurrentEndWorld()) {
            event.setTo(overWorld.getSpawnLocation());
        }
    }
}
