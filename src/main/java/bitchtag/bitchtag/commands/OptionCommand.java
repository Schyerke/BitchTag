package bitchtag.bitchtag.commands;

import bitchtag.bitchtag.Bitchtag;
import bitchtag.bitchtag.gui.OptionsInventoryHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OptionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            OptionsInventoryHolder optionsInventoryHolder = new OptionsInventoryHolder(Bitchtag.getInstance(), 3, "Option view");
            player.openInventory(optionsInventoryHolder.getInventory());
            return true;
        }
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("You can't open this! You are from the console!");
            return true;
        }
        return false;
    }
}
