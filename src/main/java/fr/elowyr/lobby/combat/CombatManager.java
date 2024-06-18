package fr.elowyr.lobby.combat;

import fr.elowyr.lobby.ElowyrLobby;
import fr.elowyr.lobby.profiles.ProfileManager;
import fr.elowyr.lobby.profiles.data.ProfileData;
import fr.elowyr.lobby.utils.ItemBuilder;
import fr.elowyr.lobby.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatManager {
    private static CombatManager instance;

    public CombatManager() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new CombatListener(), ElowyrLobby.getInstance());
    }

    public void giveStuff(Player player) {
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        profileData.setOnCombat(true);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setName("&eEpée de &6" + player.getName()).toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.BARRIER).setName("&cQuitter le Combat").toItemStack());
        player.sendMessage(Utils.color("&fVous êtes &adésormais&f en mode &cCombat&f !"));
        player.sendMessage(Utils.color("&7&oVous pouvez désormais attaquer les autres joueurs."));
    }

    public void restoreStuff(Player player) {
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        profileData.setOnCombat(false);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 102, 0.5));
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.getInventory().setArmorContents(null);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setName("&6&lNavigation &7(&fClic-droit&7)")
                .setLore(
                        "&7▪ &fConnectez au serveur que",
                        " &fvous voulez grâce à cet item !"
                ).toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6&lCombat &7(&fClic-droit&7)")
                .setLore(
                        "&7▪ &fAmusez-vous en &ccombattant",
                        " &fdans le &eserveur&f lobby !"
                ).toItemStack());
        player.setAllowFlight(false);

    }

    public static CombatManager getInstance() {
        return instance;
    }
}
