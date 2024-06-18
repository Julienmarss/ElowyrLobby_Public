package fr.elowyr.lobby.utils;

import fr.elowyr.lobby.ElowyrLobby;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static long oneMinute = TimeUnit.MINUTES.toMillis(1L);
    private static long oneHour = TimeUnit.HOURS.toMillis(1L);
    private static long oneDay = TimeUnit.DAYS.toMillis(1L);
    public static SimpleDateFormat frenchDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static ThreadLocal<DecimalFormat> remainingSeconds = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
    public static ThreadLocal<DecimalFormat> remainingSecondsTrailing = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));

    public static String color(final String value) {
        if (value == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public static List<String> colorAll(final List<String> value) {
        final ListIterator<String> iterator = value.listIterator();
        while (iterator.hasNext()) {
            iterator.set(color(iterator.next()));
        }
        return value;
    }

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < oneMinute) {
            return ((trail ? remainingSecondsTrailing : remainingSeconds).get()).format((double)duration * 0.001D) + 's';
        } else {
            return duration >= oneDay ? DurationFormatUtils.formatDuration(duration, "dd-HH:mm:ss") : DurationFormatUtils.formatDuration(duration, (duration >= oneHour ? "HH:" : "") + "mm:ss");
        }
    }

    public static void sendToServer(Player player, String server) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(buffer)) {
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(ElowyrLobby.getInstance(), "BungeeCord", buffer.toByteArray());
        } catch (Throwable ex) {
            ex.printStackTrace();
            //Lang.send(player, "send-to-server-failed", new Object[] { "server", server });
        }
    }
}
