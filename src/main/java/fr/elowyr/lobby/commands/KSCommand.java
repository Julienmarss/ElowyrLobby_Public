package fr.elowyr.lobby.commands;

import fr.elowyr.lobby.profiles.ProfileManager;
import fr.elowyr.lobby.profiles.data.ProfileData;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
            player.sendMessage(Utils.color("&7◆ &6&lEliminations &7◆ &f" + profileData.getKills()));
            player.sendMessage(Utils.color("&7◆ &6&lMorts &7◆ &f" + profileData.getDeaths()));
            if (profileData.getKills() != 0 || profileData.getDeaths() != 0) {
                player.sendMessage(Utils.color("&7◆ &6&lRatio &7◆ &f" + (profileData.getKills() / profileData.getDeaths())));
            } else {
                player.sendMessage(Utils.color("&7◆ &6&lRatio &7◆ &f0"));
            }
            return false;
        }
        return false;
    }
}
