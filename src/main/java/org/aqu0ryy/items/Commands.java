package org.aqu0ryy.items;

import org.aqu0ryy.items.utils.ChatUtil;
import org.aqu0ryy.items.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("aqitems.admin")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Loader.getInst().reloadConfig();
                    ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.reload.success"));
                } else if (args[0].equalsIgnoreCase("give")) {
                    if (args.length == 4) {
                        Player target = Bukkit.getPlayerExact(args[1]);

                        if (target != null) {
                            if (args[2].equalsIgnoreCase("mage-materia")) {
                                if (Integer.parseInt(args[3]) > 0) {
                                    if (!(target.getInventory().firstEmpty() == -1)) {
                                        try {
                                            Integer.parseInt(args[3]);
                                        } catch (NumberFormatException error) {
                                            ChatUtil.sendMessage(sender,
                                                    Loader.getInst().getConfig().getString("commands.give.no-int"));
                                            return true;
                                        }

                                        ItemUtil.giveMageMateria(target, Integer.parseInt(args[3]));
                                        ChatUtil.sendMessage(sender,
                                                Loader.getInst().getConfig().getString("commands.give.success")
                                                        .replace("{target}", target.getName()).replace("{ID}",
                                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        ChatUtil.sendMessage(sender,
                                                Loader.getInst().getConfig().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    ChatUtil.sendMessage(sender,
                                            Loader.getInst().getConfig().getString("commands.give.no-zero"));
                                }
                            } else if (args[2].equalsIgnoreCase("holy-angel")) {
                                if (Integer.parseInt(args[3]) > 0) {
                                    if (!(target.getInventory().firstEmpty() == -1)) {
                                        try {
                                            Integer.parseInt(args[3]);
                                        } catch (NumberFormatException error) {
                                            ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-int"));
                                            return true;
                                        }

                                        ItemUtil.giveHolyAngel(target, Integer.parseInt(args[3]));
                                        ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.success").replace("{target}", target.getName()).replace("{ID}",
                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-zero"));
                                }
                            } else if (args[2].equalsIgnoreCase("speed-teleport")) {
                                if (Integer.parseInt(args[3]) > 0) {
                                    if (!(target.getInventory().firstEmpty() == -1)) {
                                        try {
                                            Integer.parseInt(args[3]);
                                        } catch (NumberFormatException error) {
                                            ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-int"));
                                            return true;
                                        }

                                        ItemUtil.giveSpeedTeleport(target, Integer.parseInt(args[3]));
                                        ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.success").replace("{target}", target.getName()).replace("{ID}",
                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-zero"));
                                }
                            } else {
                                ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-id"));
                            }
                        } else {
                            ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.no-player"));
                        }
                    } else {
                        ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("commands.give.usage").replace("{label}", label));
                    }
                } else {
                    ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("messages.no-arg"));
                }
            } else {
                for (String line : Loader.getInst().getConfig().getStringList("messages.help")) {
                    line = line.replace("{label}", label);
                    ChatUtil.sendMessage(sender, line);
                }
            }
        } else {
            ChatUtil.sendMessage(sender, Loader.getInst().getConfig().getString("messages.no-permission"));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (sender.hasPermission("aqitems.admin")) {
                list.add("reload");
                list.add("give");
            }
            return list;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            List<String> list = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
            return list;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            List<String> list = new ArrayList<>();
            list.add("mage-materia");
            list.add("holy-angel");
            list.add("speed-teleport");
            return list;
        } else if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
            List<String> list = new ArrayList<>();
            list.add("1");
            list.add("5");
            list.add("10");
            list.add("25");
            return list;
        }
        return null;
    }
}
