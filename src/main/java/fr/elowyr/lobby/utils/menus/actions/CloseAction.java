package fr.elowyr.lobby.utils.menus.actions;

import org.bukkit.event.inventory.InventoryCloseEvent;

public abstract class CloseAction {
    public CloseAction() {
    }

    public abstract void onClose(InventoryCloseEvent var1);
}

