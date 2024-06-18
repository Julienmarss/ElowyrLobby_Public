package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServersToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Utils.color("&6&lElowyr &7◆ &c/servers <on|off>."));
            return false;
        }
        if (args[0].equalsIgnoreCase("on")) {
            ElowyrLobby.getInstance().setServersToggle(true);
            Bukkit.broadcastMessage(Utils.color("&6&lElowyr &7◆ &fLes &eserveurs &fsont &adisponible&f !"));
        } else if (args[0].equalsIgnoreCase("off")) {
            ElowyrLobby.getInstance().setServersToggle(false);
            Bukkit.broadcastMessage(Utils.color("&6&lElowyr &7◆ &fLes &eserveurs&f sont &cindisponible &f!"));
        }
        return false;
    }
}
