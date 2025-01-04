package org.aqu0ryy.items;

import org.aqu0ryy.items.commands.CommandItem;
import org.aqu0ryy.items.configs.Config;
import org.aqu0ryy.items.listeners.ItemListener;
import org.aqu0ryy.items.utils.ChatUtil;
import org.aqu0ryy.items.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class Loader extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        config = new Config(this);

        ChatUtil chatUtil = new ChatUtil();
        ItemUtil itemUtil = new ItemUtil(this, config, chatUtil);

        new CommandItem(this, config, chatUtil, itemUtil);
        Bukkit.getPluginManager().registerEvents(new ItemListener(this, config, chatUtil), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    public void reload() {
        config.reload();
    }
}
