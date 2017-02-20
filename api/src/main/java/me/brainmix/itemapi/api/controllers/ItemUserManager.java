package me.brainmix.itemapi.api.controllers;

import me.brainmix.itemapi.api.ItemRegister;
import me.brainmix.itemapi.api.ItemUser;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.util.HashTreeSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemUserManager extends AbstractItemManager implements Listener{

    private Set<ItemUser> users = new HashTreeSet<>();

    public ItemUserManager(ItemRegister register) {
        super(register);
        Bukkit.getPluginManager().registerEvents(this, getRegister().getPlugin());
        users = Bukkit.getOnlinePlayers().stream().map(p -> new ItemUser(p, getRegister())).collect(Collectors.toSet());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        users.add(new ItemUser(e.getPlayer(), getRegister()));
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
