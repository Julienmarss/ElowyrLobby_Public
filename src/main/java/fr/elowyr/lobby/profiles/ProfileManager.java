package fr.elowyr.lobby.profiles;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.profiles.data.provider.ProfileProvider;
import fr.elowyr.lobby.profiles.data.utils.IOUtil;
import org.bukkit.Bukkit;

public class ProfileManager {
    private static ProfileManager instance;
    private IOUtil ioUtil;
    private ProfileProvider provider;

    public ProfileManager() {
        instance = this;
        registerProvider();

        Bukkit.getPluginManager().registerEvents(new ProfileListener(), ElowyrLobby.getInstance());
        System.out.println("[Module] Profiles Loaded");
    }

    private void registerProvider() {
        this.ioUtil = new IOUtil();
        this.provider = new ProfileProvider();
        this.provider.read();
    }

    public boolean isBlacklistUsername(String name) {
        return name.equalsIgnoreCase("Notch") || name.equalsIgnoreCase("Dinnerbone") || name.equalsIgnoreCase("Grumm") || name.equalsIgnoreCase("Dustin") || name.equalsIgnoreCase("Hagrid") || name.equalsIgnoreCase("C3PO") || name.equalsIgnoreCase("R2D2") || name.equalsIgnoreCase("md_5");
    }

    public static ProfileManager getInstance() {
        return instance;
    }

    public IOUtil getIoUtil() {
        return ioUtil;
    }

    public ProfileProvider getProvider() {
        return provider;
    }
}
