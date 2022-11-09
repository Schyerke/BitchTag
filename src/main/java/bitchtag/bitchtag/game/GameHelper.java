package bitchtag.bitchtag.game;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.item.ItemHelper;
import bitchtag.bitchtag.player.PlayerHelper;
import bitchtag.bitchtag.world.WorldHelper;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.kyori.adventure.text.Component.text;

public enum GameHelper {
    INSTANCE;

    private boolean gameStarted = false;
    private final List<String> readyQueue;

    private final List<Player> bitches;

    GameHelper() {
        this.readyQueue = new ArrayList<>();
        this.bitches = new ArrayList<>();
    }

    public void addOnQueue(String playerName){
        readyQueue.add(playerName);
    }

    public boolean isEveryoneReady() {
        int readySize = 0;
        int onlineSize = 0;

        for(var ignored : Bukkit.getOnlinePlayers()){
            onlineSize++;
        }
        for(var ignored : readyQueue){
            readySize++;
        }
        return onlineSize == readySize;
    }

    public boolean startGame() {
        if(!isEveryoneReady()){
            return false;
        }
        gameStarted = true;

        Bukkit.getScheduler().runTask(Bitchtag.getInstance(), () -> {
            WorldHelper.INSTANCE.init();
            PlayerHelper.INSTANCE.onGameStart();

            int size = 0;
            for(var ignored : this.readyQueue){
                size++;
            }

            int random = new Random().nextInt(size-1);
            Player bitch = Bukkit.getPlayer(this.readyQueue.get(random));
            addBitch(bitch);
            if(bitch == null){
                System.out.println("bitch is null");
            }
            bitch.getInventory().addItem(ItemHelper.INSTANCE.getCompassTracker());
        });

        Bukkit.getScheduler().runTaskLater(Bitchtag.getInstance(), () -> Bukkit.getScheduler().runTaskLater(Bitchtag.getInstance(), this::endGame, GameMinuteLastHelper.INSTANCE.getMinuteLast()), 5 * 20L);
        return true;
    }

    public void endGame(){

        gameStarted = false;

        List<Player> playersWon = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            for(Player bitch : this.bitches){
                Bukkit.broadcast(text(ChatColor.RED + bitch.displayName().examinableName() + " is a bitch :)"));
                bitch.showTitle(Title.title(text(ChatColor.RED + "You lost!"), text("you ass")));
                if(bitch.getName().equals(player.getName())){
                    playersWon.add(player);
                }
            }
        }

        playersWon.forEach(player -> player.showTitle(Title.title(text(ChatColor.GREEN + "You won!"), text("You so good"))));

        Bukkit.getScheduler().runTask(Bitchtag.getInstance(), () -> {
            this.bitches.clear();
            this.readyQueue.clear();
        });

        Bukkit.getScheduler().runTaskLater(Bitchtag.getInstance(), PlayerHelper.INSTANCE::onGameEnd, 3*20L);
    }

    public List<Player> getBitches() {
        return bitches;
    }

    public void addBitch(Player bitch){
        this.bitches.add(bitch);

        bitch.getInventory().addItem(ItemHelper.INSTANCE.getCompassTracker());

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("dummy", "scoreboard", text("bitch"));
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        bitch.setScoreboard(board);
    }

    public void removeBitch(Player bitch){
        this.bitches.remove(bitch);

        bitch.getInventory().remove(ItemHelper.INSTANCE.getCompassTracker());

        bitch.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public boolean isBitch(Player player){
        return this.bitches.contains(player);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}
