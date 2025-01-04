package org.aqu0ryy.items.commands;

import com.google.common.collect.Lists;
import org.aqu0ryy.items.Loader;
import org.aqu0ryy.items.configs.Config;
import org.aqu0ryy.items.utils.ChatUtil;
import org.aqu0ryy.items.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandItem extends AbstractCommand {

    private final Loader plugin;
    private final Config config;
    private final ChatUtil chatUtil;
    private final ItemUtil itemUtil;

    public CommandItem(Loader plugin, Config config, ChatUtil chatUtil, ItemUtil itemUtil) {
        super(plugin, "aqitems");
        this.plugin = plugin;
        this.config = config;
        this.chatUtil = chatUtil;
        this.itemUtil = itemUtil;
    }

    @Override public void execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("aqitems.admin")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reload();
                    chatUtil.sendMessage(sender, config.get().getString("commands.reload.success"));
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
                                            chatUtil.sendMessage(sender,
                                                    config.get().getString("commands.give.no-int"));
                                            return;
                                        }

                                        itemUtil.giveMageMateria(target, Integer.parseInt(args[3]));
                                        chatUtil.sendMessage(sender,
                                                config.get().getString("commands.give.success")
                                                        .replace("{target}", target.getName()).replace("{ID}",
                                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        chatUtil.sendMessage(sender,
                                                config.get().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    chatUtil.sendMessage(sender,
                                            config.get().getString("commands.give.no-zero"));
                                }
                            } else if (args[2].equalsIgnoreCase("holy-angel")) {
                                if (Integer.parseInt(args[3]) > 0) {
                                    if (!(target.getInventory().firstEmpty() == -1)) {
                                        try {
                                            Integer.parseInt(args[3]);
                                        } catch (NumberFormatException error) {
                                            chatUtil.sendMessage(sender, config.get().getString("commands.give.no-int"));
                                            return;
                                        }

                                        itemUtil.giveHolyAngel(target, Integer.parseInt(args[3]));
                                        chatUtil.sendMessage(sender, config.get().getString("commands.give.success").replace("{target}", target.getName()).replace("{ID}",
                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        chatUtil.sendMessage(sender, config.get().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    chatUtil.sendMessage(sender, config.get().getString("commands.give.no-zero"));
                                }
                            } else if (args[2].equalsIgnoreCase("speed-teleport")) {
                                if (Integer.parseInt(args[3]) > 0) {
                                    if (!(target.getInventory().firstEmpty() == -1)) {
                                        try {
                                            Integer.parseInt(args[3]);
                                        } catch (NumberFormatException error) {
                                            chatUtil.sendMessage(sender, config.get().getString("commands.give.no-int"));
                                            return;
                                        }

                                        itemUtil.giveSpeedTeleport(target, Integer.parseInt(args[3]));
                                        chatUtil.sendMessage(sender, config.get().getString("commands.give.success").replace("{target}", target.getName()).replace("{ID}",
                                                args[2]).replace("{amount}", args[3]));
                                    } else {
                                        chatUtil.sendMessage(sender, config.get().getString("commands.give.full-inv"));
                                    }
                                } else {
                                    chatUtil.sendMessage(sender, config.get().getString("commands.give.no-zero"));
                                }
                            } else {
                                chatUtil.sendMessage(sender, config.get().getString("commands.give.no-id"));
                            }
                        } else {
                            chatUtil.sendMessage(sender, config.get().getString("commands.give.no-player"));
                        }
                    } else {
                        chatUtil.sendMessage(sender, config.get().getString("commands.give.usage").replace("{label}", label));
                    }
                } else {
                    chatUtil.sendMessage(sender, config.get().getString("messages.no-arg"));
                }
            } else {
                for (String message : config.get().getStringList("messages.help")) {
                    chatUtil.sendMessage(sender, message.replace("{label}", label));
                }
            }
        } else {
            chatUtil.sendMessage(sender, config.get().getString("messages.no-permission"));
        }
    }

    @Override public List<String> complete(CommandSender sender, String[] args) {
        if (sender.hasPermission("aqitems.admin")) {
            if (args.length == 1) {
                return Lists.newArrayList("reload", "give");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    return Lists.newArrayList(player.getName());
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                return Lists.newArrayList("mage-materia", "holy-angel", "speed-teleport");
            } else if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
                return Lists.newArrayList("1", "5", "25");
            }
        }

        return Lists.newArrayList();
    }
}
