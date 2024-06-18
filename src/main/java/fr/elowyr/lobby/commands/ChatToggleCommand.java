package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Utils.color("&6&lElowyr &7◆ &c/chat <on|off>."));
            return false;
        }
        if (args[0].equalsIgnoreCase("on")) {
            ElowyrLobby.getInstance().setChatToggle(true);
            Bukkit.broadcastMessage(Utils.color("&6&lElowyr &7◆ &fLe chat est &aactivé&f !"));
        } else if (args[0].equalsIgnoreCase("off")){
            ElowyrLobby.getInstance().setChatToggle(false);
            Bukkit.broadcastMessage(Utils.color("&6&lElowyr &7◆ &fLe chat est &cdésactivé &f!"));
        }
        return false;
    }
}
