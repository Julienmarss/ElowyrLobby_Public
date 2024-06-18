package fr.elowyr.lobby.utils.menus;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MenuManager implements Listener {
    private static MenuManager instance = new MenuManager();
    private Plugin plugin = null;
    private Map<UUID, GUI> guis = Maps.newHashMap();

    public MenuManager() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof VirtualHolder) {
            ((VirtualHolder)event.getInventory().getHolder()).getGui().onInventoryClick(event);
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player && event.getInventory().getHolder() instanceof VirtualHolder) {
            GUI gui = ((VirtualHolder)event.getInventory().getHolder()).getGui();
            gui.onInventoryClose(event);
            if (gui instanceof VirtualGUI) {
                VirtualGUI virtualGUI = (VirtualGUI)gui;
                this.guis.put(event.getPlayer().getUniqueId(), virtualGUI);
            }
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            closeOpenMenus();
            this.plugin = null;
        }

    }

    public void register(JavaPlugin plugin) {
        if (!this.isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(instance, plugin);
            this.plugin = plugin;
        }

    }

    public boolean isRegistered(JavaPlugin plugin) {
        if (plugin.equals(this.plugin)) {
            Iterator var2 = HandlerList.getRegisteredListeners(plugin).iterator();

            while(var2.hasNext()) {
                RegisteredListener listener = (RegisteredListener)var2.next();
                if (listener.getListener().equals(instance)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void closeOpenMenus() {
        Player[] var0 = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Player player = var0[var2];
            if (player.getOpenInventory() != null) {
                Inventory inventory = player.getOpenInventory().getTopInventory();
                if (inventory.getHolder() instanceof VirtualHolder) {
                    player.closeInventory();
                }
            }
        }

    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public Map<UUID, GUI> getGuis() {
        return this.guis;
    }

    public static MenuManager getInstance() {
        return instance;
    }
}
