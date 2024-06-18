package fr.elowyr.lobby.profiles.data.provider;


import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.profiles.ProfileManager;
import fr.elowyr.lobby.profiles.data.ProfileData;
import fr.elowyr.lobby.profiles.data.utils.IOUtil;
import fr.elowyr.lobby.utils.FileUtils;
import fr.elowyr.lobby.utils.io.provide.IProvider;
import fr.elowyr.lobby.utils.io.readable.IReadable;
import fr.elowyr.lobby.utils.io.writable.IWritable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProfileProvider implements IProvider<String, ProfileData>, IWritable, IReadable {

    private Map<String, ProfileData> profiles;
    private File save;

    public ProfileProvider() {
        this.profiles = new HashMap<>();
        this.save = new File(ElowyrLobby.getInstance().getDataFolder(), "/players/");
    }


    @Override
    public void provide(String key, ProfileData value) {
        this.profiles.put(key, value);
    }

    @Override
    public void remove(String key) {

        for (ProfileData user : this.getProfiles()) {
            this.profiles.remove(key);
            final File file = new File(save, user.getPlayerUUID() + ".json");
            FileUtils.deleteFile(file);
        }
    }

    @Override
    public ProfileData get(String key) {
        return this.profiles.get(key);
    }

    @Override
    public void read() {

        File[] files = save.listFiles();

        if (files == null) {
            this.profiles = new HashMap<>();
            return;
        }
        IOUtil ioUtil = ProfileManager.getInstance().getIoUtil();

        for (File file : files) {
            if (file.isFile()) {
                final String json = FileUtils.loadContent(file);
                ProfileData user = ioUtil.deserialize(json);
                this.profiles.put(user.getPlayerUUID().toString(), user);
            }
        }

    }

    @Override
    public void write() {

        if (this.profiles == null) return;

        final IOUtil ioUtil = ProfileManager.getInstance().getIoUtil();

        for (ProfileData user : this.getProfiles()) {
            final File file = new File(save, user.getPlayerUUID() + ".json");
            final String json = ioUtil.serialize(user);
            FileUtils.save(file, json);
        }
    }

    public void write(ProfileData profileData) {

        if (this.profiles == null) return;

        final IOUtil ioUtil = ProfileManager.getInstance().getIoUtil();

        final File file = new File(save, profileData.getPlayerUUID() + ".json");
        final String json = ioUtil.serialize(profileData);
        FileUtils.save(file, json);
    }

    public Collection<ProfileData> getProfiles() {
        return this.profiles.values();
    }
}
