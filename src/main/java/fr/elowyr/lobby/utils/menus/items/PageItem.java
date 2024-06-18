package fr.elowyr.lobby.utils.menus.items;

import fr.elowyr.lobby.utils.ItemBuilder;
import org.bukkit.Material;

public class PageItem extends VirtualItem {
    private int page;
    private boolean previous;

    public PageItem(int page) {
        this(page, "&6Page suivante: &f");
    }

    public PageItem(int page, boolean previous) {
        this(page, "&6Page précédente: &f");
    }

    public PageItem(int page, String value) {
        super((new ItemBuilder(Material.PAPER)).setName(value + page).toItemStack());
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public boolean isPrevious() {
        return this.previous;
    }
}
