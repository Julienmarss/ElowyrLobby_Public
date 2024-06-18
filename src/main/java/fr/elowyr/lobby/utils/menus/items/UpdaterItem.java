package fr.elowyr.lobby.utils.menus.items;

import fr.elowyr.lobby.utils.menus.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpdaterItem extends VirtualItem {
    private int delay;
    private UpdateAction updateAction;
    private int TASK_ID = -1;

    public UpdaterItem(ItemStack item, int delay) {
        super(item);
        this.delay = delay;
    }

    public UpdaterItem setUpdateAction(UpdateAction updateAction) {
        this.updateAction = updateAction;
        return this;
    }

    public void startUpdate(final Player player, final int slot) {
        if (this.updateAction != null) {
            this.TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MenuManager.getInstance().getPlugin(), new Runnable() {
                public void run() {
                    Inventory inv = player.getOpenInventory().getTopInventory();
                    inv.setItem(slot, UpdaterItem.this.updateAction.action(player, UpdaterItem.this.getItem()));
                }
            }, 1L, (long)this.delay);
        }
    }

    public void cancelUpdate() {
        Bukkit.getScheduler().cancelTask(this.TASK_ID);
    }

    public int getDelay() {
        return this.delay;
    }

    public UpdateAction getUpdateAction() {
        return this.updateAction;
    }

    public int getTASK_ID() {
        return this.TASK_ID;
    }

    public abstract static class UpdateAction {
        public UpdateAction() {
        }

        public abstract ItemStack action(Player var1, ItemStack var2);
    }
}
