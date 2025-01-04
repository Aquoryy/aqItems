package org.aqu0ryy.items.utils;

import org.aqu0ryy.items.Loader;
import org.aqu0ryy.items.configs.Config;
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

    private final Loader plugin;
    private final Config config;
    private final ChatUtil chatUtil;

    public ItemUtil(Loader plugin, Config config, ChatUtil chatUtil) {
        this.plugin = plugin;
        this.config = config;
        this.chatUtil = chatUtil;
    }

    public void giveMageMateria(Player player, int amount) {
        ItemStack item = new ItemStack(Material.matchMaterial(config.get().getString("items.mage-materia.material").toLowerCase()), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(chatUtil.color(config.get().getString("items.mage-materia.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : config.get().getStringList("items.mage-materia.lore")) {
            loreLine = chatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (config.get().getBoolean("items.mage-materia.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(plugin, "mage_materia"), PersistentDataType.STRING, "mage_materia");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }

    public void giveHolyAngel(Player player, int amount) {
        ItemStack item = new ItemStack(Material.matchMaterial(config.get().getString("items.holy-angel.material").toLowerCase()), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(chatUtil.color(config.get().getString("items.holy-angel.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : config.get().getStringList("items.holy-angel.lore")) {
            loreLine = chatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (config.get().getBoolean("items.holy-angel.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(plugin, "holy_angel"), PersistentDataType.STRING, "holy_angel");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }

    public void giveSpeedTeleport(Player player, int amount) {
        ItemStack item = new ItemStack(Material.matchMaterial(config.get().getString("items.speed-teleport.material").toLowerCase()), amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(chatUtil.color(config.get().getString("items.speed-teleport.name")));

        List<String> lore = new ArrayList<>();
        for (String loreLine : config.get().getStringList("items.speed-teleport.lore")) {
            loreLine = chatUtil.color(loreLine);
            lore.add(loreLine);
        }

        meta.addItemFlags(ItemFlag.values());

        if (config.get().getBoolean("items.speed-teleport.settings.glow")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        data.set(new NamespacedKey(plugin, "speed_teleport"), PersistentDataType.STRING, "speed_teleport");

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }
}
