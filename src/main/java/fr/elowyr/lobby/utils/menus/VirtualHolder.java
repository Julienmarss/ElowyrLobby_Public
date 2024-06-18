package fr.elowyr.lobby.utils.menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class VirtualHolder implements InventoryHolder {
    private GUI gui;
    private Inventory inventory;
    private int nextPage;

    public VirtualHolder(GUI gui, Inventory inventory) {
        this.gui = gui;
        this.inventory = inventory;
        this.nextPage = -1;
    }

    public GUI getGui() {
        return this.gui;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
