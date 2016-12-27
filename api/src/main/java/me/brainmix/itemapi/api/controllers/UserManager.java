package me.brainmix.itemapi.api.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.util.HashTreeSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.brainmix.itemapi.api.ItemUser;

public class UserManager implements Listener {

    private Set<ItemUser> users = new HashTreeSet<>();
    private static UserManager instance;
    private ThrownItemManager thrownItemManager;

    public UserManager(Plugin plugin, ThrownItemManager thrownItemManager) {
        instance = this;
        this.thrownItemManager = thrownItemManager;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        users = Bukkit.getOnlinePlayers().stream().map(p -> new ItemUser(p, thrownItemManager)).collect(Collectors.toSet());
    }

    public static UserManager a() {
        return instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        users.add(new ItemUser(e.getPlayer(), thrownItemManager));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        if(getUser(e.getPlayer()) != null) users.remove(getUser(e.getPlayer()));
    }

    public ItemUser getUser(Player player) {
        return getUser(player.getName());
    }

    public ItemUser getUser(String name) {
        for(ItemUser user: users) {
            if(user.getPlayer().getName().equals(name)) return user;
        }
        return null;
    }

}
