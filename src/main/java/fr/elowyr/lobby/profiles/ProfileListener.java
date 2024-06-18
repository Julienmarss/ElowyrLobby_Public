package fr.elowyr.lobby.profiles;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.profiles.data.ProfileData;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        if (ProfileManager.getInstance().isBlacklistUsername(player.getName())) {
            player.kickPlayer(Utils.color("&fImpossible de &crejoindre&f Elowyr avec ce pseudo."));
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(ElowyrLobby.getInstance(), () -> {
            ProfileData newProfileData = new ProfileData(player);
            ProfileManager.getInstance().getProvider().provide(player.getUniqueId().toString(), newProfileData);
            ProfileManager.getInstance().getProvider().write(newProfileData);
        }, 10L);
    }
}
