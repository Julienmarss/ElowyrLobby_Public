package fr.elowyr.lobby;

import fr.elowyr.lobby.combat.CombatManager;
import fr.elowyr.lobby.commands.*;
import fr.elowyr.lobby.listeners.PlayerListener;
import fr.elowyr.lobby.listeners.WorldListener;
import fr.elowyr.lobby.profiles.ProfileManager;
import fr.elowyr.lobby.tasks.SaveTask;
import fr.elowyr.lobby.utils.CustomHeads;
import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.Utils;
import fr.elowyr.lobby.utils.menus.MenuManager;
import fr.elowyr.lobby.utils.menus.Size;
import fr.elowyr.lobby.utils.menus.VirtualGUI;
import fr.elowyr.lobby.utils.menus.actions.ClickAction;
import fr.elowyr.lobby.utils.menus.items.VirtualItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ElowyrLobby extends JavaPlugin {
    private static ElowyrLobby instance;

    private boolean chatToggle;
    private boolean serversToggle;
    private boolean combatToggle;

    @Override
    public void onEnable() {
        instance = this;
        chatToggle = true;
        serversToggle = true;
        combatToggle = true;
        new ProfileManager();
        new CombatManager();
        Bukkit.getPluginManager().registerEvents(new MenuManager(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("gmc").setExecutor(new CreativeModeCommand());
        getCommand("gms").setExecutor(new SurvivalModeCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("servers").setExecutor(new ServersToggleCommand());
        getCommand("chat").setExecutor(new ChatToggleCommand());
        getCommand("combat").setExecutor(new CombatToggleCommand());
        getCommand("ks").setExecutor(new KSCommand());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getScheduler().scheduleAsyncDelayedTask(this, new SaveTask(), (20 * 60L) * 3);
    }

    @Override
    public void onDisable() {
        ProfileManager.getInstance().getProvider().write();
    }

    public void openServers(Player player) {
        VirtualGUI gui = new VirtualGUI("&7Liste des serveurs", Size.CINQ_LIGNE);

        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44})
            gui.setItem(i, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 1)));

        gui.setItem(22, new VirtualItem(new ItemBuilder(Material.DIAMOND_SWORD).setName("&e&l⦙ &fServeur &6Faction")
                .setLore(
                        "",
                        "&7▪ &fRejoins le serveur &eFaction&f !",
                        "&7▪ &fOuvert depuis le &a20/04/2024"
                        //"&7◆ &aRejoins les &2" + PlaceholderAPI.setPlaceholders(player, "%pinger_players_play.hadaria.fr:25565%") + " joueurs &aen ligne. &2&l✔"
                )).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                //Utils.sendToServer(player, "dev");
                //Utils.sendToServer(player, "faction");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                player.sendMessage(Utils.color("&6&lElowyr &7◆ &fTéléportation &avers&f le &efaction&f !"));
                player.performCommand("queue faction");
            }
        }));

        ItemStack dev = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFiYmY1NGQ2ZDI1MDE0N2U2ZTdhYjA3OWM5ZThjNzYyOTAwNTBjMDA4NmUyNDRjOWZmODFjMTU4M2Q5MDg5YSJ9fX0=");
        if (player.isOp()) {
            gui.setItem(34, new VirtualItem(new ItemBuilder(dev).setName("&e&l⦙ &fServeur &6Développement")
                    .setLore(
                            "",
                            "&7▪ &fRejoins le serveur &eDéveloppement&f !"
                            //"&7◆ &aRejoins les &2" + PlaceholderAPI.setPlaceholders(player, "%pinger_players_play.hadaria.fr:25565%") + " joueurs &aen ligne. &2&l✔"
                    )).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    Utils.sendToServer(player, "dev");
                    //Utils.sendToServer(player, "faction");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                    player.sendMessage(Utils.color("&6&lElowyr &7◆ &fTéléportation &avers&f le &edéveloppement&f !"));
                    //player.performCommand("queue faction");
                }
            }));
        }

        gui.open(player);
    }

    public static ElowyrLobby getInstance() {
        return instance;
    }

    public static void setInstance(ElowyrLobby instance) {
        ElowyrLobby.instance = instance;
    }

    public boolean isChatToggle() {
        return chatToggle;
    }

    public void setChatToggle(boolean chatToggle) {
        this.chatToggle = chatToggle;
    }

    public boolean isServersToggle() {
        return serversToggle;
    }

    public void setServersToggle(boolean serversToggle) {
        this.serversToggle = serversToggle;
    }

    public boolean isCombatToggle() {
        return combatToggle;
    }

    public void setCombatToggle(boolean combatToggle) {
        this.combatToggle = combatToggle;
    }
}
