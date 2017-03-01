package me.brainmix.itemapi.api;

import java.util.HashSet;
import java.util.Set;

import me.brainmix.itemapi.api.controllers.*;
import me.brainmix.itemapi.api.events.ItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.brainmix.itemapi.api.delay.DelayManager;
import me.brainmix.itemapi.api.utils.CustomItemEnum;

public class ItemRegister {

    private Set<CustomItem> items = new HashSet<>();
    private ItemManager itemManager;
    private ItemEventManager eventManager;
    private ItemUserManager userManager;
    private ItemThrowManager throwManager;
    private ItemProjectileManager projectileManager;
    private Plugin plugin;

    public ItemRegister(Plugin plugin) {
        this.plugin = plugin;
        this.eventManager = new ItemEventManager();
        this.itemManager = new ItemManager(this, items);
        this.userManager = new ItemUserManager(this);
        this.throwManager = new ItemThrowManager(this);
        this.projectileManager = new ItemProjectileManager(this);
        ItemEvent.setUserManager(userManager);
    }

    public void register(CustomItem item) {
        items.add(item);
        eventManager.register(item);
        itemManager.setItems(items);
        item.register(new DelayManager(item, eventManager, plugin), this);
        item.init(item.getOptions());

    }

    public void registerAll(Class<? extends CustomItemEnum> customItemEnum) {
        if(customItemEnum.isEnum()) {
            for(CustomItemEnum item : customItemEnum.getEnumConstants()) {
                register(item.getCustomItem());
            }
        }
    }

    public void unregister(CustomItem item) {
        items.stream().filter(i -> i.getId().equals(item.getId())).forEach(items::remove);
    }

    public Set<CustomItem> getItems() {
        return items;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public ItemEventManager getEventManager() {
        return eventManager;
    }

    public ItemUserManager getUserManager() {
        return userManager;
    }

    public ItemThrowManager getThrowManager() {
        return throwManager;
    }

    public ItemProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public Plugin getPlugin() {
        return plugin;
    }

}
