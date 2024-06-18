package fr.elowyr.lobby.combat;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.profiles.ProfileManager;
import fr.elowyr.lobby.profiles.data.ProfileData;
import fr.elowyr.lobby.utils.CooldownUtils;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
            ItemStack itemStack = event.getItem();

            if (itemStack != null && itemStack.getType() == Material.DIAMOND_SWORD && !profileData.isOnCombat()) {
                if (!ElowyrLobby.getInstance().isCombatToggle()) {
                    player.sendMessage(Utils.color("&6&lCombat &7◆ &fLe mode &ecombat&f est &cdésactivé&f."));
                    event.setCancelled(true);
                    return;
                }
                CombatManager.getInstance().giveStuff(player);
                event.setCancelled(true);
            } else if (itemStack != null && itemStack.getType() == Material.BARRIER) {
                if (CooldownUtils.isOnCooldown("combat", player)) {
                    player.sendMessage(Utils.color("&6&lCombat &7◆ &fVous êtes en &ccombat&f. &7(&f" + Utils.getRemaining(CooldownUtils.getCooldownForPlayerLong("combat", player), false) + "&7)"));
                    event.setCancelled(true);
                } else {
                    CombatManager.getInstance().restoreStuff(player);
                }
            } else {
                event.setCancelled(true);
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() == null) return;
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getEntity();
                Player target = (Player) event.getDamager();
                ProfileData victim = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
                ProfileData damager = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

                if (!victim.isOnCombat() || !damager.isOnCombat()) {
                    event.setCancelled(true);
                    return;
                }
                CooldownUtils.addCooldown("combat", player, 5);
                CooldownUtils.addCooldown("combat", target, 5);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() == null)
            return;
        if (event.getEntityType() != EntityType.PLAYER)
            return;
        Player player = event.getEntity();
        ProfileData victim = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        int playerDeath = victim.getDeaths();
        victim.setDeaths(playerDeath + 1);
        victim.setKillStreak(0.0D);
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        ProfileData kill = ProfileManager.getInstance().getProvider().get(killer.getUniqueId().toString());
        int lastKills = kill.getKills();
        double lastKs = kill.getKillStreak();
        kill.setKills(lastKills + 1);
        kill.setKillStreak(lastKs + 1.0D);
        event.getDrops().clear();
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 101, 0.5));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        CombatManager.getInstance().restoreStuff(event.getPlayer());
    }
}
