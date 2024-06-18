package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String[] arg = args;
        if (arg.length < 1) {
            sender.sendMessage(Utils.color("&6&lElowyr &7◆ &c/broadcast <message>."));
            return false;
        }
        Bukkit.broadcastMessage(Utils.color(String.join(" ", arg).replace('&', '§')));
        return false;
    }
}
