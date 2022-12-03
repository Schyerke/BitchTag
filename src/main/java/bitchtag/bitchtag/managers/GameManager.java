package bitchtag.bitchtag.managers;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.helpers.PlayerHelper;
import bitchtag.bitchtag.options.*;
import bitchtag.bitchtag.world.WorldTraveler;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.title.Title.title;

public class GameManager {
    private static final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Bitchtag plugin;

    private boolean gameStarted = false;
    private final List<String> bitches;
    private final List<String> readyQueue;
    private final List<OptionListener> optionListeners = new ArrayList<>();

    private GameState gameState = GameState.LOBBY;
    public static final int DEFAULT_LAST_MINUTES = 5;
    private int minuteLast = DEFAULT_LAST_MINUTES;

    private final PlayerManager playerManager;
    private final WorldTraveler worldTraveler;

    public GameManager(Bitchtag plugin, PlayerManager playerManager, WorldTraveler worldTraveler){
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.worldTraveler = worldTraveler;

        this.bitches = new ArrayList<>();
        this.readyQueue = new ArrayList<>();
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
        switch(this.gameState){
            case LOBBY -> lobby();
            case STARTING -> starting();
            case IN_GAME -> inGame();
            case ENDING -> ending();
        }
    }

    private void lobby(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            logger.info("Teleporting " + player.getName() + " to spawn...");
            playerManager.teleportToSpawn(player);
        });
    }

    private void starting(){
        if(!isEveryoneReady()){
            Bukkit.broadcast(text(ChatColor.RED + "Someone is not ready!"));
            this.gameState = GameState.LOBBY;
            return;
        }
        Bukkit.broadcast(text(ChatColor.GREEN + "Game starting..."));

        gameStarted = true;

        worldTraveler.setWorldType(Options.getWorldTypeGeneration());

        worldTraveler.createNormalWorlds();
        logger.info("worlds are created");

        setGameState(GameState.IN_GAME);
    }

    private void inGame(){
        logger.info("Game has started");
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setInvulnerable(false);
            logger.info("Set " + player.getName() + " invulnerable to false!");
            player.setFlying(false);
            player.setAllowFlight(false);
            logger.info("Set " + player.getName() + " flying to false!");
            player.getInventory().clear();
            logger.info("Cleared " + player.getName() + "'s inventory!");

            player.teleport(Bitchtag.getInstance().getWorldTraveler().getWorldByWorldEnv(World.Environment.NORMAL).getSpawnLocation());
            logger.info("Teleporting " + player.getName() + " to the game world...");

            player.sendMessage(ChatColor.RED + "Game started!");
            player.showTitle(title(text(ChatColor.YELLOW + "Game started!"), text(ChatColor.RED + "Enjoy! :)")));
        });

        @SuppressWarnings("all")
        Player bitch = Bukkit.getOnlinePlayers().stream().findAny().get();

        addBitch(bitch);
        setupOptions();

        // TODO
        Bukkit.getScheduler().runTaskLater(Bitchtag.getInstance(),
                () -> setGameState(GameState.ENDING),
                5 * 20L + minuteLast * 20L * 60);
    }

    private void ending(){
        gameStarted = false;

        this.bitches.forEach(bitch -> {
            Player playerBitch = PlayerHelper.getPlayer(bitch);
            Bukkit.broadcast(text(ChatColor.RED + playerBitch.getName() + " is a bitch :)"));
            playerBitch.showTitle(Title.title(text(ChatColor.RED + "You lost!"), text("you ass")));
        });

        Bukkit.getOnlinePlayers().forEach(player -> {
            if(!isBitch(player)){
                player.showTitle(Title.title(text(ChatColor.GREEN + "You won!"), text("You so good")));
            }
        });

        this.optionListeners.forEach(OptionListener::disable);
        this.optionListeners.clear();

        this.bitches.clear();
        this.readyQueue.clear();

        Bukkit.getScheduler().runTaskLater(Bitchtag.getInstance(),
                () -> {
                    logger.info("Game has ended");
                    setGameState(GameState.LOBBY);
                } ,
                3*20L);
    }



    public boolean isEveryoneReady() {
        int readySize = 0;
        int allPlayersSize = 0;
        logger.info("Checking ready queue list...");
        for(String playerReady : readyQueue){
            logger.info(playerReady + " is ready!");
            readySize++;
        }
        for(Player ignored : Bukkit.getOnlinePlayers()){
            allPlayersSize++;
        }
        return readySize == allPlayersSize;
    }

    private void setupOptions(){
        // control options here, and then we will see if i should create an instance of the Listener
        // every option listener will implement OpenListener which has an "init" method and it will start the Option
        // which is used to give kits and register the listener to the plugin.

        // Weird Knockback
        // Explosive Arrow
        // Teleport Arrow
        // Explosive snows
        // Explosive eggs
        // Double jump
        // Elytra

        if(Options.isWeirdKnockback()) {
            logger.warning("Weird knockback enabled");
            optionListeners.add(new WeirdKnockback(plugin));
        }
        if(Options.isExplosiveArrow()){
            logger.warning("Explosive arrow enabled");
            optionListeners.add(new ExplosiveArrow(plugin));
        }
        if(Options.isTeleportArrow()){
            logger.warning("Teleport arrow enabled");
            optionListeners.add(new TeleportArrow(plugin));
        }
        if(Options.isExplosiveSnows()){
            logger.warning("Explosive snow enabled");
            optionListeners.add(new ExplosiveSnows(plugin));
        }
        if(Options.isExplosiveEggs()){
            logger.warning("Explosive eggs is enabled");
            optionListeners.add(new ExplosiveEggs(plugin));
        }
        if(Options.isDoubleJump()){
            logger.warning("Double jump is enabled");
            optionListeners.add(new DoubleJump(plugin));
        }
        if(Options.isElytra()){
            logger.warning("Elytra is enabled");
            optionListeners.add(new Elytra(plugin));
        }

        int i = 0;
        logger.info("Enabling listeners...");
        for(OptionListener optionListener : optionListeners){
            i++;
            logger.info(optionListener.getClassName() + " enabling...");
            optionListener.init();
            logger.info(optionListener.getClassName() + ChatColor.RED + " ENABLED!");
        }
        logger.info("Enabled " + i + " listeners!");
    }

    public void handleHits(Player victim, Player damager) {
        logger.info("Checking if the victim is a bitch...");
        if(isBitch(victim)){
            logger.info("It is a bitch! So nothing will change!");
            return;
        }
        logger.info("Checking if the victim is not a bitch AND the damager is a bitch...");
        if(!isBitch(victim) && isBitch(damager)){
            logger.info("Condition true! Damager will not be a bitch anymore and the victim will be a bitch");
            addBitch(victim);
            removeBitch(damager);
        }
    }

    public void addBitch(Player bitch){
        this.bitches.add(bitch.getName());
        Bukkit.broadcast(text(ChatColor.RED + bitch.getName() + " is a bitch!"));

        bitch.getInventory().addItem(new ItemStack(Material.COMPASS));

//        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//        Objective obj = board.registerNewObjective("dummy", "scoreboard", text("bitch"));
//        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
//
//        bitch.setScoreboard(board);
    }

    public void removeBitch(Player bitch){
        this.bitches.remove(bitch.getName());
        Bukkit.broadcast(text(ChatColor.GREEN + bitch.getName() + " is NOT a bitch anymore!"));

        bitch.getInventory().remove(Material.COMPASS);

        bitch.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public boolean isBitch(Player player){
        return this.bitches.contains(player.getName());
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void addOnQueue(Player player){
        if(readyQueue.contains(player.getName())){
            player.sendMessage(ChatColor.RED + "You are already in the queue!");
            return;
        }
        readyQueue.add(player.getName());
    }

    public List<String> getReadyQueue() {
        return readyQueue;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getMinuteLast() {
        return minuteLast;
    }

    public void setMinuteLast(int minuteLast) {
        this.minuteLast = minuteLast;
    }

    public void add5MinuteLast(){
        if(this.minuteLast + 5 >= 65){
            Bukkit.broadcast(text("Max time is 60 minutes!"));
            return;
        }
        this.minuteLast = this.minuteLast + 5;
    }

    public void remove5MinuteLast(){
        if(this.minuteLast - 5 <= 0){
            return;
        }
        this.minuteLast = this.minuteLast - 5;
    }
}
