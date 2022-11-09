package bitchtag.bitchtag.item.listener;

import bitchtag.bitchtag.game.GameHelper;
import bitchtag.bitchtag.game.GameMinuteLastHelper;
import bitchtag.bitchtag.player.PlayerHelper;
import bitchtag.bitchtag.item.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.title.Title.title;

public class ItemInteractListener implements Listener {

    @EventHandler
    public void onOpenStartGameMenu(PlayerInteractEvent e){
        e.getPlayer().sendMessage("onOpenStartGameMenu");
        if(e.getItem() == null){
            System.out.println("item is null");
            return;
        }
        if(e.getItem().getItemMeta() == null){
            System.out.println("item meta is null");
            return;
        }
        if(e.getItem().getItemMeta().displayName() == null){
            System.out.println("item display name is null");
            return;
        }
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            e.getPlayer().sendMessage("you balescu");
            ItemStack item = e.getItem();
            ItemMeta itemMeta = item.getItemMeta();
            if(Objects.equals(itemMeta.displayName(), text(ItemNames.START_GAME.getName()))){
                Player player = e.getPlayer();
                player.sendMessage("You clicked the start game item!");
                GameHelper.INSTANCE.addOnQueue(player.displayName().examinableName());
                if(GameHelper.INSTANCE.startGame()){
                    player.sendMessage("Game started!");
                    player.showTitle(title(text("Game started!"), text("Get ready to play!")));
                }
                else{
                    player.sendMessage("You are now on the queue!");
                }
            }
        }
    }

    @EventHandler
    public void changingGameLastMinutesByStick(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(e.getItem().getItemMeta() == null)return;
        if(e.getItem().getItemMeta().displayName() == null)return;
        if(Objects.equals(e.getItem().getItemMeta().displayName(), text(ItemNames.GAME_LENGTH.getName()))){
            Player player = e.getPlayer();
            player.sendMessage("You clicked the game length item!");
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                GameMinuteLastHelper.INSTANCE.add5MinuteLast();
            }
            else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
                GameMinuteLastHelper.INSTANCE.remove5MinuteLast();
            }
            Bukkit.broadcast(text("Game length is now " + GameMinuteLastHelper.INSTANCE.getMinuteLast() + " minutes!"));
            PlayerHelper.INSTANCE.updateGameLengthItem();
        }
    }

}
