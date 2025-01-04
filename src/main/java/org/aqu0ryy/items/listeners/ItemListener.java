package org.aqu0ryy.items.listeners;

import org.aqu0ryy.items.Loader;
import org.aqu0ryy.items.configs.Config;
import org.aqu0ryy.items.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemListener implements Listener {

    private final ConcurrentHashMap<UUID, Long> cooldownsMage = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Long> cooldownsAngel = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Long> cooldownsTeleport = new ConcurrentHashMap<>();

    private final Loader plugin;
    private final Config config;
    private final ChatUtil chatUtil;

    public ItemListener(Loader plugin, Config config, ChatUtil chatUtil) {
        this.plugin = plugin;
        this.config = config;
        this.chatUtil = chatUtil;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "mage_materia"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsMage.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsMage.get(player.getUniqueId()) < config.get().getLong("items.mage-materia.cooldown.time") * 1000) {
                    chatUtil.sendMessage(player, config.get().getString("items.mage-materia.cooldown.message").replace("{time}", String.valueOf((((config.get().getLong("items.mage-materia.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsMage.get(player.getUniqueId()))) / 1000))));
                } else {
                    List<Player> playersInRadius = player.getWorld().getPlayers().stream()
                            .filter(p -> p != player && p.getLocation().distanceSquared(player.getLocation())
                                    <= config.get().getInt("items.mage-materia.settings.radius.blocks") * config.get().getInt("items.mage-materia.settings.radius.blocks"))
                            .toList();

                    if (!playersInRadius.isEmpty()) {
                        for (Player target : playersInRadius) {
                            for (String effect : config.get().getStringList("items.mage-materia.settings.effects")) {
                                String[] effectData = effect.split(" ");
                                target.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectData[0]), Integer.parseInt(effectData[2]) * 20,
                                        Integer.parseInt(effectData[1]) - 1));
                            }
                        }

                        chatUtil.sendMessage(player, config.get().getString("items.mage-materia.settings.message"));

                        cooldownsMage.put(player.getUniqueId(), System.currentTimeMillis());
                        item.setAmount(item.getAmount() - 1);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                cooldownsMage.remove(player.getUniqueId());
                            }
                        }.runTaskLater(plugin, 20L * (config.get().getLong("items.mage-materia.cooldown.time") * 1000));
                    } else {
                        chatUtil.sendMessage(player, config.get().getString("items.mage-materia.settings.radius.not-players"));
                    }
                }
            }
        }

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "holy_angel"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsAngel.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsAngel.get(player.getUniqueId()) < config.get().getLong("items.holy-angel.cooldown.time") * 1000) {
                    chatUtil.sendMessage(player, config.get().getString("items.holy-angel.cooldown.message").replace("{time}", String.valueOf((((config.get().getLong("items.holy-angel.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsAngel.get(player.getUniqueId()))) / 1000))));
                } else {
                    List<Player> playersInRadius = player.getWorld().getPlayers().stream()
                            .filter(p -> p != player && p.getLocation().distanceSquared(player.getLocation())
                                    <= config.get().getInt("items.holy-angel.settings.radius.blocks") * config.get().getInt("items.holy-angel.settings.radius.blocks"))
                            .toList();

                    if (!playersInRadius.isEmpty()) {
                        for (Player target : playersInRadius) {
                            for (String effect : config.get().getStringList("items.holy-angel.settings.effects")) {
                                String[] effectData = effect.split(" ");
                                target.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectData[0]), Integer.parseInt(effectData[2]) * 20,
                                        Integer.parseInt(effectData[1]) - 1));
                            }
                        }

                        chatUtil.sendMessage(player, config.get().getString("items.holy-angel.settings.message"));

                        cooldownsAngel.put(player.getUniqueId(), System.currentTimeMillis());
                        item.setAmount(item.getAmount() - 1);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                cooldownsAngel.remove(player.getUniqueId());
                            }
                        }.runTaskLater(plugin, 20L * (config.get().getLong("items.holy-angel.cooldown.time") * 1000));
                    } else {
                        chatUtil.sendMessage(player, config.get().getString("items.holy-angel.settings.radius.not-players"));
                    }
                }
            }
        }

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "speed_teleport"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsTeleport.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsTeleport.get(player.getUniqueId()) < config.get().getLong("items.speed-teleport.cooldown.time") * 1000) {
                    chatUtil.sendMessage(player, config.get().getString("items.speed-teleport.cooldown.message").replace("{time}", String.valueOf((((config.get().getLong("items.speed-teleport.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsTeleport.get(player.getUniqueId()))) / 1000))));
                } else {
                    Block playerTargetBlock = player.getTargetBlock(null, config.get().getInt("items.speed-teleport.settings.max-distance"));
                    Location blockLoc = playerTargetBlock.getLocation();

                    player.teleport(blockLoc);

                    chatUtil.sendMessage(player, config.get().getString("items.speed-teleport.settings.message"));

                    cooldownsTeleport.put(player.getUniqueId(), System.currentTimeMillis());
                    item.setAmount(item.getAmount() - 1);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            cooldownsTeleport.remove(player.getUniqueId());
                        }
                    }.runTaskLater(plugin, 20L * (config.get().getLong("items.speed-teleport.cooldown.time") * 1000));
                }
            }
        }
    }
}
