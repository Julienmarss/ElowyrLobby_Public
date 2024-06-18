package fr.elowyr.lobby.utils.menus.items;

import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.menus.actions.ClickAction;
import org.bukkit.inventory.ItemStack;

public class VirtualItem {
    private final ItemStack item;
    private ClickAction action;
    private boolean allowClick;

    public VirtualItem(ItemStack item) {
        this.item = item;
        this.action = null;
    }

    public VirtualItem( ItemBuilder item) {
        this.item = item.toItemStack();
        this.action = null;
        this.allowClick = false;
    }

    public VirtualItem onItemClick(ClickAction action) {
        this.action = action;
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ClickAction getAction() {
        return this.action;
    }

    public boolean isAllowClick() {
        return this.allowClick;
    }

    public void setAction(ClickAction action) {
        this.action = action;
    }

    public void setAllowClick(boolean allowClick) {
        this.allowClick = allowClick;
    }
}
