package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreativeModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (sender.isOp()) {
                player.setGameMode(GameMode.CREATIVE);
                sender.sendMessage(Utils.color("&6&lElowyr &7◆ &fMode de jeu &6créatif&f pour &e" + player.getName() + "&f."));
            } else {
                sender.sendMessage(Utils.color("&6&lElowyr &7◆ &fVous n'avez pas la &cpermission&f de faire cela."));
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.color("&6&lElowyr &7◆ &fLe joueur &cn'existe&f pas."));
                return false;
            }
            target.setGameMode(GameMode.CREATIVE);
            sender.sendMessage(Utils.color("&6&lElowyr &7◆ &fMode de jeu &6créatif&f pour &e" + target.getName() + "&f."));
            target.sendMessage(Utils.color("&6&lElowyr &7◆ &fMode de jeu &6créatif&f pour &e" + target.getName() + "&f."));
        } else {
            sender.sendMessage(Utils.color("&6&lElowyr &7◆ &c/gmc <player>."));
        }
        return false;
    }
}
