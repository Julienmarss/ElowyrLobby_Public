package fr.elowyr.lobby.utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class CooldownUtils {
    private static HashMap<String, HashMap<UUID, Long>> cooldowns = new HashMap();

    public CooldownUtils() {
    }

    public static void createCooldown(String alias) {
        if (!cooldowns.containsKey(alias)) {
            cooldowns.put(alias, new HashMap());
        }
    }

    public static void addCooldown(String alias, Player player, int seconds) {
        if (!cooldowns.containsKey(alias)) {
            createCooldown(alias);
        }

        long next = System.currentTimeMillis() + (long)seconds * 1000L;
        ((HashMap)cooldowns.get(alias)).put(player.getUniqueId(), next);
    }

    public static void removeCooldown(String alias, Player player) {
        if (cooldowns.containsKey(alias)) {
            ((HashMap)cooldowns.get(alias)).remove(player.getUniqueId());
        }
    }

    public static HashMap<UUID, Long> getCooldownMap(String alias) {
        return cooldowns.getOrDefault(alias, null);
    }

    public static boolean isOnCooldown(String alias, Player player) {
        return cooldowns.containsKey(alias) && (cooldowns.get(alias)).containsKey(player.getUniqueId()) && System.currentTimeMillis() <= (Long)((HashMap)cooldowns.get(alias)).get(player.getUniqueId());
    }

    public static int getCooldownForPlayerInt(String alias, Player player) {
        return (int)getCooldownForPlayerLong(alias, player);
    }

    public static long getCooldownForPlayerLong(String alias, Player player) {
        return (Long)((HashMap)cooldowns.get(alias)).get(player.getUniqueId()) - System.currentTimeMillis();
    }

    public static void clearCooldowns() {
        cooldowns.clear();
    }
}

