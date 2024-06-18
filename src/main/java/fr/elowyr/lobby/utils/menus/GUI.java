package fr.elowyr.lobby.utils.menus;

import fr.elowyr.lobby.utils.menus.items.VirtualItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public interface GUI {
    GUI setItem(int var1, VirtualItem var2);

    GUI addItem(VirtualItem var1);

    void open(Player var1);

    void apply(Inventory var1, Player var2);

    void onInventoryClick(InventoryClickEvent var1);

    void onInventoryClose(InventoryCloseEvent var1);
}
