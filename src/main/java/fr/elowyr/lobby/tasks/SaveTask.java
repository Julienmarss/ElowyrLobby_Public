package fr.elowyr.lobby.tasks;

import fr.elowyr.lobby.profiles.ProfileManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {

    @Override
    public void run() {
        ProfileManager.getInstance().getProvider().write();
    }
}
