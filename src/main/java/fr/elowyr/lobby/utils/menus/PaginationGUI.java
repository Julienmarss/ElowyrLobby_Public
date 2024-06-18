package fr.elowyr.lobby.utils.menus;

import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.menus.actions.ClickAction;
import fr.elowyr.lobby.utils.menus.items.PageItem;
import fr.elowyr.lobby.utils.menus.items.VirtualItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class PaginationGUI extends VirtualGUI {
    private int contentSize;
    private int page;
    private int maxItemsPerPage;

    public PaginationGUI(int page, int contentSize, String name) {
        super(name, Size.SIX_LIGNE);
        this.contentSize = contentSize;
        this.page = page;
        this.prepare();
    }

    public void prepare() {
        final int totalPages = this.getTotalPages();
        final int nextPage = this.page + 1;

        int previousPage;
        for(previousPage = 45; previousPage < 54; ++previousPage) {
            this.setItem(previousPage, new VirtualItem((new ItemBuilder(Material.STAINED_GLASS_PANE)).setName("&7&m---").toItemStack()));
        }

        if (this.page < totalPages) {
            this.setItem(53, (new PageItem(nextPage)).onItemClick(new ClickAction() {
                public void onClick(InventoryClickEvent event) {
                    if (nextPage <= totalPages) {
                        Player player = (Player)event.getWhoClicked();
                        player.closeInventory();
                        PaginationGUI.this.actionProcess(player, nextPage);
                    }
                }
            }));
        }

        if (this.page > 1) {
            previousPage = this.page - 1;
            int finalPreviousPage = previousPage;
            this.setItem(45, (new PageItem(previousPage, true)).onItemClick(new ClickAction() {
                public void onClick(InventoryClickEvent event) {
                    if (finalPreviousPage > 0) {
                        Player player = (Player)event.getWhoClicked();
                        player.closeInventory();
                        PaginationGUI.this.actionProcess(player, finalPreviousPage);
                    }
                }
            }));
        }

    }

    public abstract void actionProcess(Player var1, int var2);

    public int getMaxItemPerPage() {
        if (this.maxItemsPerPage != 0) {
            return this.maxItemsPerPage;
        } else {
            Size size = this.getSize();
            return size.getSize() - 9;
        }
    }

    public int getTotalPages() {
        int maxItemPerPage = this.getMaxItemPerPage();
        int listSize = this.contentSize;
        int size = listSize - 1;
        if (size <= maxItemPerPage) {
            size = 1;
        } else {
            size = size / maxItemPerPage + 1;
        }

        if (maxItemPerPage == 1) {
            if (listSize > 0) {
                size = listSize;
            } else {
                size = 1;
            }
        }

        return size;
    }

    public int[] getDatas() {
        int maxItemPerPage = this.getMaxItemPerPage();
        int[] data = new int[]{this.page == 1 ? 0 : maxItemPerPage * (this.page - 1), this.page == 1 ? maxItemPerPage : maxItemPerPage * this.page};
        if (data[1] > this.contentSize) {
            data[1] = this.contentSize;
        }

        return data;
    }

    public int getContentSize() {
        return this.contentSize;
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxItemsPerPage() {
        return this.maxItemsPerPage;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }
}
