package fr.elowyr.lobby.listeners;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.Utils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.imanity.imanityspigot.event.AsyncTabCompleteEvent;

import java.util.Collection;
import java.util.stream.Stream;

public class PlayerListener implements Listener {

    private String[] BAD_WORDS = {"lag", "fdp", "ez", "ezz", "tg", "ntm", "connard",
            "enfoiré", "pigeon", "suceur", "pute", "segpa", "gitan", "shivana", "herodia", "pandakmc", "shandera", "menoria", "hardfight", "ironfight", "pute",
            "getafaction", "xcraft", "voltaireloveme", "noob", "tapette", "raclure", "merde", "kikou", "arabe",
            "abruti", "batard", "kikoo", "chintok", "zgeg", "bite", "anus", "rekt", "jk", "jean-kevin", "tgg", "ezzz",
            "salope", "enculer", "salopard", "fuck", "sperme", "youporn", "xnxx", "gangbang", "gitan", "brazzer",
            "tukif", "RAT", "boot", "DDOS", "pornhub", "bouffon", "rebeu", "negro", "porc", "pd", "suceuse", "branleur",
            "branleuse", "penis", "fist", "fuck", "bouffon", "sodomie", "cul", "niked", "fucking", "con", "8-D",
            "8--D", "8---D", "chatte", "milf", "beurette", "%"};

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 102, 0.5));
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.getInventory().setArmorContents(null);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setName("&6&lNavigation &7(&fClic-droit&7)")
                .setLore(
                        "&7▪ &fConnectez au serveur que",
                        " &fvous voulez grâce à cet item !"
                ).toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6&lCombat &7(&fClic-droit&7)")
                .setLore(
                        "&7▪ &fAmusez-vous en &ccombattant",
                        " &fdans le &eserveur&f lobby !"
                ).toItemStack());
        player.setAllowFlight(false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (event.getItem() != null) {
                if (event.getItem().getType() == Material.COMPASS) {
                    if (!ElowyrLobby.getInstance().isServersToggle() && !player.isOp()) {
                        event.setCancelled(true);
                        player.sendMessage(Utils.color("&6&lElowyr &7◆ &fLe serveur n'est pas &cdisponible&f pour le moment."));
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10F, 10F);
                    ElowyrLobby.getInstance().openServers(player);
                }
            }
        }
    }

    @EventHandler
    public void onCommandExecute(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp()) {
            if (event.getMessage().equalsIgnoreCase("/spawn")
                    || event.getMessage().equalsIgnoreCase("/lobby") || event.getMessage().equalsIgnoreCase("/hub") ||
                    event.getMessage().equalsIgnoreCase("/queue faction") || event.getMessage().equalsIgnoreCase("/ks") ||
            event.getMessage().equalsIgnoreCase("/kill") || event.getMessage().equalsIgnoreCase("/deaths")) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTabComplete(AsyncTabCompleteEvent e) {
        if (!(e.getSender() instanceof Player))
            return;
        Stream<String> playerNames = Bukkit.getOnlinePlayers().stream().map(OfflinePlayer::getName);
        Collection<String> tab = e.getCompletions();
        String message = e.getBuffer();
        int index = message.lastIndexOf(' ');
        String lastToken = (index < 0) ? message : message.substring(index + 1);
        playerNames.forEach(tab::remove);
        if (!e.getSender().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        if (!ElowyrLobby.getInstance().isChatToggle() && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage(Utils.color("&6&lElowyr &7◆ &fLe chat est &cdésactivé&f."));
            return;
        }

        if (event.getMessage().length() < 2 && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage(Utils.color("&6&lElowyr &7◆ &fVotre &emessage &fdoit au moins &ccontenir&f 2 lettres."));
        }

        if (this.containsBadWords(event.getMessage()) && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage(Utils.color("&6&lElowyr &7◆ &fVotre message &econtient &fun ou plusieurs mots &cinterdit&f."));
        }

        if (player.isOp()) {
            event.setFormat(Utils.color("&cStaff " + player.getName() + " &f▸ &c" + event.getMessage()));
        } else {
            event.setFormat(Utils.color("&7Joueur " + player.getName() + " &f▸ &7" + event.getMessage()));
        }

    }

    private boolean containsBadWords(String message) {
        String[] split = message.split(" ");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            if (ArrayUtils.contains(this.BAD_WORDS, word)) {
                return true;
            }
        }
        return false;
    }
}
