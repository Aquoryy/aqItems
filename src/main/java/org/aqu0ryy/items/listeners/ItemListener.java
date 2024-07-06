package org.aqu0ryy.items.listeners;

import org.aqu0ryy.items.Loader;
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
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemListener implements Listener {

    private static final ConcurrentHashMap<UUID, Long> cooldownsMage = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Long> cooldownsAngel = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Long> cooldownsTeleport = new ConcurrentHashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Loader.getInst(), "mage_materia"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsMage.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsMage.get(player.getUniqueId()) < Loader.getInst().getConfig().getLong("items.mage-materia.cooldown.time") * 1000) {
                    ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.mage-materia.cooldown.message").replace("{time}", String.valueOf((((Loader.getInst().getConfig().getLong("items.mage-materia.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsMage.get(player.getUniqueId()))) / 1000))));
                } else {
                    List<Player> playersInRadius = player.getWorld().getPlayers().stream()
                            .filter(p -> p != player && p.getLocation().distanceSquared(player.getLocation())
                                    <= Loader.getInst().getConfig().getInt("items.mage-materia.settings.radius.blocks") * Loader.getInst()
                                    .getConfig().getInt("items.mage-materia.settings.radius.blocks"))
                            .toList();

                    if (!playersInRadius.isEmpty()) {
                        for (Player target : playersInRadius) {
                            for (String effect : Loader.getInst().getConfig().getStringList("items.mage-materia.settings.effects")) {
                                String[] effectData = effect.split(" ");
                                target.addPotionEffect(new PotionEffect(
                                        Objects.requireNonNull(PotionEffectType.getByName(effectData[0])), Integer.parseInt(effectData[2]) * 20,
                                        Integer.parseInt(effectData[1]) - 1));
                            }
                        }

                        ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.mage-materia.settings.message"));

                        cooldownsMage.put(player.getUniqueId(), System.currentTimeMillis());
                        item.setAmount(item.getAmount() - 1);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                cooldownsMage.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Loader.getInst(), 20L * (Loader.getInst().getConfig().getLong("items.mage-materia.cooldown.time") * 1000));
                    } else {
                        ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.mage-materia.settings.radius.not-players"));
                    }
                }
            }
        }

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Loader.getInst(), "holy_angel"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsAngel.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsAngel.get(player.getUniqueId()) < Loader.getInst().getConfig().getLong("items.holy-angel.cooldown.time") * 1000) {
                    ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.holy-angel.cooldown.message").replace("{time}", String.valueOf((((Loader.getInst().getConfig().getLong("items.holy-angel.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsAngel.get(player.getUniqueId()))) / 1000))));
                } else {
                    List<Player> playersInRadius = player.getWorld().getPlayers().stream()
                            .filter(p -> p != player && p.getLocation().distanceSquared(player.getLocation())
                                    <= Loader.getInst().getConfig().getInt("items.holy-angel.settings.radius.blocks") * Loader.getInst()
                                    .getConfig().getInt("items.holy-angel.settings.radius.blocks"))
                            .toList();

                    if (!playersInRadius.isEmpty()) {
                        for (Player target : playersInRadius) {
                            for (String effect : Loader.getInst().getConfig().getStringList("items.holy-angel.settings.effects")) {
                                String[] effectData = effect.split(" ");
                                target.addPotionEffect(new PotionEffect(
                                        Objects.requireNonNull(PotionEffectType.getByName(effectData[0])), Integer.parseInt(effectData[2]) * 20,
                                        Integer.parseInt(effectData[1]) - 1));
                            }
                        }

                        ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.holy-angel.settings.message"));

                        cooldownsAngel.put(player.getUniqueId(), System.currentTimeMillis());
                        item.setAmount(item.getAmount() - 1);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                cooldownsAngel.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Loader.getInst(), 20L * (Loader.getInst().getConfig().getLong("items.holy-angel.cooldown.time") * 1000));
                    } else {
                        ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.holy-angel.settings.radius.not-players"));
                    }
                }
            }
        }

        if (meta != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Loader.getInst(), "speed_teleport"), PersistentDataType.STRING)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (cooldownsTeleport.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownsTeleport.get(player.getUniqueId()) < Loader.getInst().getConfig().getLong("items.speed-teleport.cooldown.time") * 1000) {
                    ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.speed-teleport.cooldown.message").replace("{time}", String.valueOf((((Loader.getInst().getConfig().getLong("items.speed-teleport.cooldown.time") * 1000) - (System.currentTimeMillis() - cooldownsTeleport.get(player.getUniqueId()))) / 1000))));
                } else {
                    Block playerTargetBlock = player.getTargetBlock(null, Loader.getInst().getConfig().getInt("items.speed-teleport.settings.max-distance"));
                    Location blockLoc = playerTargetBlock.getLocation();

                    player.teleport(blockLoc);

                    ChatUtil.sendMessage(player, Loader.getInst().getConfig().getString("items.speed-teleport.settings.message"));

                    cooldownsTeleport.put(player.getUniqueId(), System.currentTimeMillis());
                    item.setAmount(item.getAmount() - 1);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            cooldownsTeleport.remove(player.getUniqueId());
                        }
                    }.runTaskLater(Loader.getInst(), 20L * (Loader.getInst().getConfig().getLong("items.speed-teleport.cooldown.time") * 1000));
                }
            }
        }
    }
}
