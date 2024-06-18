package fr.elowyr.lobby.utils.menus;

import fr.elowyr.lobby.utils.Utils;
import fr.elowyr.lobby.utils.menus.actions.CloseAction;
import fr.elowyr.lobby.utils.menus.items.BackItem;
import fr.elowyr.lobby.utils.menus.items.UpdaterItem;
import fr.elowyr.lobby.utils.menus.items.VirtualItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class VirtualGUI implements GUI {
    private String name;
    private Size size;
    private VirtualItem[] items;
    private CloseAction closeAction;
    private boolean allowBackItem;
    private boolean allowClick;

    public VirtualGUI(String name, Size size) {
        this.name = Utils.color(name);
        this.size = size;
        this.items = new VirtualItem[size.getSize()];
    }

    public VirtualGUI allowBackItem() {
        this.allowBackItem = true;
        return this;
    }

    public VirtualGUI allowClick() {
        this.allowClick = true;
        return this;
    }

    public VirtualGUI onCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction;
        return this;
    }

    public VirtualGUI setItem(int position, VirtualItem menuItem) {
        this.items[position] = menuItem;
        return this;
    }

    public VirtualGUI addItem(VirtualItem item) {
        int next = this.getNextEmptySlot();
        if (next == -1) {
            return this;
        } else {
            this.items[next] = item;
            return this;
        }
    }

    private int getNextEmptySlot() {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] == null) {
                return i;
            }
        }

        return -1;
    }

    public VirtualGUI fillEmptySlots(VirtualItem menuItem) {
        for (int i = 0; i < this.items.length; ++i) {
            if ((!this.allowBackItem || i != this.size.getSize() - 1) && this.items[i] == null) {
                this.items[i] = menuItem;
            }
        }

        return this;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new VirtualHolder(this, Bukkit.createInventory(player, this.size.getSize())), this.size.getSize(), this.name);
        this.apply(inventory, player);
        player.openInventory(inventory);
    }

    public void apply(Inventory inventory, Player player) {
        if (this.allowBackItem && this.items[inventory.getSize() - 1] == null) {
            this.items[inventory.getSize() - 1] = new BackItem();
        }

        for (int i = 0; i < this.items.length; ++i) {
            VirtualItem item = this.items[i];
            if (item != null) {
                inventory.setItem(i, item.getItem());
                if (item instanceof UpdaterItem) {
                    UpdaterItem updater = (UpdaterItem) item;
                    updater.startUpdate(player, i);
                }
            }
        }

    }

    public void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        boolean cancelled = false;
        if (!this.allowClick) {
            cancelled = true;
        }

        if (slot >= 0 && slot < this.size.getSize() && this.items[slot] != null) {
            VirtualItem item = this.items[slot];
            if (!item.isAllowClick()) {
                cancelled = true;
            } else if (cancelled && item.isAllowClick()) {
                cancelled = false;
            }

            if (item.getAction() != null) {
                item.getAction().onClick(event);
            }

            if (event.isCancelled()) {
                cancelled = true;
            }
        }

        event.setCancelled(cancelled);
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        if (this.closeAction != null) {
            this.closeAction.onClose(event);
            this.closeAction = null;
        }

        for (int i = 0; i < this.items.length; ++i) {
            VirtualItem item = this.items[i];
            if (item != null && item instanceof UpdaterItem) {
                UpdaterItem updater = (UpdaterItem) item;
                updater.cancelUpdate();
            }
        }

    }

    public void destroy() {
        this.name = null;
        this.size = null;
        this.items = null;
    }

    public String getName() {
        return this.name;
    }

    public Size getSize() {
        return this.size;
    }

    public VirtualItem[] getItems() {
        return this.items;
    }

    public CloseAction getCloseAction() {
        return this.closeAction;
    }

    public boolean isAllowBackItem() {
        return this.allowBackItem;
    }

    public boolean isAllowClick() {
        return this.allowClick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setItems(VirtualItem[] items) {
        this.items = items;
    }

    public void setCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction;
    }

    public void setAllowBackItem(boolean allowBackItem) {
        this.allowBackItem = allowBackItem;
    }

    public void setAllowClick(boolean allowClick) {
        this.allowClick = allowClick;
    }

    protected boolean canEqual(Object other) {
        return other instanceof VirtualGUI;
    }
}
