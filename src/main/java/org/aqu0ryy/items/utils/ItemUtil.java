package org.aqu0ryy.items.utils;

import org.aqu0ryy.items.Loader;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemUtil {

    public static void giveMageMateria(Player player, int amount) {
        ItemStack item = new ItemStack(Objects.requireNonNull(
                Material.matchMaterial(
                        Objects.requireNonNull(Loader.getInst().getConfig().getString("items.mage-materia.material")).toLowerCase())), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatUtil.color(Loader.getInst().getConfig().getString("items.mage-materia.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : Loader.getInst().getConfig().getStringList("items.mage-materia.lore")) {
            loreLine = ChatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (Loader.getInst().getConfig().getBoolean("items.mage-materia.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(Loader.getInst(), "mage_materia"), PersistentDataType.STRING, "mage_materia");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }

    public static void giveHolyAngel(Player player, int amount) {
        ItemStack item = new ItemStack(Objects.requireNonNull(
                Material.matchMaterial(
                        Objects.requireNonNull(Loader.getInst().getConfig().getString("items.holy-angel.material")).toLowerCase())), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatUtil.color(Loader.getInst().getConfig().getString("items.holy-angel.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : Loader.getInst().getConfig().getStringList("items.holy-angel.lore")) {
            loreLine = ChatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (Loader.getInst().getConfig().getBoolean("items.holy-angel.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(Loader.getInst(), "holy_angel"), PersistentDataType.STRING, "holy_angel");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }

    public static void giveSpeedTeleport(Player player, int amount) {
        ItemStack item = new ItemStack(Objects.requireNonNull(
                Material.matchMaterial(
                        Objects.requireNonNull(Loader.getInst().getConfig().getString("items.speed-teleport.material")).toLowerCase())), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatUtil.color(Loader.getInst().getConfig().getString("items.speed-teleport.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : Loader.getInst().getConfig().getStringList("items.speed-teleport.lore")) {
            loreLine = ChatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (Loader.getInst().getConfig().getBoolean("items.speed-teleport.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(Loader.getInst(), "speed_teleport"), PersistentDataType.STRING, "speed_teleport");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }
}
