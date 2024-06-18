package fr.elowyr.lobby.profiles.data.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.elowyr.lobby.profiles.data.ProfileData;

public class IOUtil {

    private final Gson gson;

    public IOUtil() {
        this.gson = createGsonInstance();
    }

    public Gson createGsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping();

        return gsonBuilder.create();
    }

    public String serialize(ProfileData profileData) {
        return this.gson.toJson(profileData);
    }

    public ProfileData deserialize(String json) {
        return this.gson.fromJson(json, ProfileData.class);
    }
}