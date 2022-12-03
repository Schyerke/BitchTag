package bitchtag.bitchtag.commands;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.managers.GameManager;
import bitchtag.bitchtag.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;

public class LeaveCommand implements CommandExecutor {
    private final Logger logger = JavaPlugin.getPlugin(Bitchtag.class).getLogger();

    private final Bitchtag plugin;
    private final GameManager gameManager;

    public LeaveCommand(Bitchtag plugin, GameManager gameManager){
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        logger.info("Checking if the sender is a player...");
        if(sender instanceof Player player){
            logger.info("The sender is a player!");
            Bukkit.broadcast(text(ChatColor.YELLOW + player.getName() + " left the game"));
            logger.info("Ending the game...");
            gameManager.setGameState(GameState.ENDING);
            return true;
        }
        if(sender instanceof ConsoleCommandSender){
            logger.info("The sender is the console!");
            logger.info("Ending the game...");
            gameManager.setGameState(GameState.ENDING);
            return true;
        }
        logger.info("The sender is not a player!");
        return false;
    }
}
