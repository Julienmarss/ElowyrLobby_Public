package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 102, 0.5));
        sender.sendMessage(Utils.color("&6&lElowyr &7◆ &fVous avez été &atéléporté&f au spawn."));
        return false;
    }
}
