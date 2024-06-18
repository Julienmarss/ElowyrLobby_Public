package fr.elowyr.lobby.utils.menus.items;

import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.menus.MenuManager;
import fr.elowyr.lobby.utils.menus.VirtualGUI;
import fr.elowyr.lobby.utils.menus.actions.ClickAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BackItem extends VirtualItem {
    public BackItem() {
        super((new ItemBuilder(Material.ARROW)).setName("&c&lRetour").toItemStack());
        this.onItemClick(new ClickAction() {
            public void onClick(InventoryClickEvent event) {
                Player player = (Player)event.getWhoClicked();
                VirtualGUI gui = (VirtualGUI) MenuManager.getInstance().getGuis().get(player.getUniqueId());
                if (gui != null) {
                    gui.open(player);
                }

            }
        });
    }
}
