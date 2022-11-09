package bitchtag.bitchtag.player;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.game.GameMinuteLastHelper;
import bitchtag.bitchtag.item.ItemHelper;
import bitchtag.bitchtag.item.ItemNames;
import bitchtag.bitchtag.location.LocationConstants;
import bitchtag.bitchtag.world.WorldHelper;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public enum PlayerHelper {
    INSTANCE;

    public void teleportSpawn(Player player) {
        player.teleport(LocationConstants.SPAWN);
        player.getInventory().clear();
        player.setInvulnerable(true);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setAllowFlight(true);
        player.showTitle(Title.title(text("Welcome!"), text("You are now playing BitchTag!")));

        player.getInventory().addItem(getPlayerItemsOnJoin());
    }

    private ItemStack[] getPlayerItemsOnJoin() {
        return ItemHelper.INSTANCE.getItemsOnPlayerJoin();
    }


    public void onGameStart() {
        for (Player player : LocationConstants.SPAWN.getWorld().getPlayers()) {
            player.setInvulnerable(false);
            player.setFlying(false);
            player.getInventory().clear();

            player.teleport(WorldHelper.INSTANCE.getCurrentOverWorld().getSpawnLocation());
        }
    }

    public void updateGameLengthItem() {
        Bukkit.getScheduler().runTask(Bitchtag.getInstance(), () -> {
            for (Player player : LocationConstants.SPAWN.getWorld().getPlayers()) {
                ItemStack stick = null;
                //find the stick item in the player inventory
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null) continue;
                    if (item.getItemMeta() == null) continue;
                    if (item.getItemMeta().displayName() == null) continue;
                    if (Objects.equals(item.getItemMeta().displayName(), text(ItemNames.GAME_LENGTH.getName()))) {
                        stick = item;
                        break;
                    }
                }
                if (stick != null) {
                    stick.editMeta(meta -> meta.displayName(text(ItemNames.GAME_LENGTH.getName(GameMinuteLastHelper.INSTANCE.getMinuteLast()))));
                }
                else{
                    player.sendMessage("wtf you dont have a stick?");
                }
            }
        });
    }

    public void onGameEnd() {
        for(Player player : Bukkit.getOnlinePlayers()){
            teleportSpawn(player);
        }
    }
}
