package fr.elowyr.lobby.utils.menus.items;

import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.menus.actions.ClickAction;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CloseItem extends VirtualItem {
    public CloseItem() {
        super((new ItemBuilder(Material.BARRIER)).setName("&6&lFermer le menu").toItemStack());
        this.onItemClick(new ClickAction() {
            public void onClick(InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
            }
        });
    }
}
