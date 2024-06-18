package fr.elowyr.lobby.utils.menus.actions;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ClickAction {
    public ClickAction() {
    }

    public abstract void onClick(InventoryClickEvent event);
}
