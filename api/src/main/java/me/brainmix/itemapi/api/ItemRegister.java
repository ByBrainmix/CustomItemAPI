package me.brainmix.itemapi.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.brainmix.itemapi.api.controllers.ItemEventManager;
import me.brainmix.itemapi.api.controllers.ItemManager;
import me.brainmix.itemapi.api.controllers.ThrownItemManager;
import me.brainmix.itemapi.api.controllers.UserManager;
import me.brainmix.itemapi.api.delay.DelayManager;
import me.brainmix.itemapi.api.utils.CustomItemEnum;

public class ItemRegister {

    private Set<CustomItem> items;
    private ItemManager manager;
    private ItemEventManager eventManager;
    private UserManager userManager;
    private ThrownItemManager thrownItemManager;
    private Plugin plugin;

    public ItemRegister(Plugin plugin) {
        this.items = new HashSet<>();
        this.eventManager = new ItemEventManager();
        this.manager = new ItemManager(plugin, eventManager, items);
        this.thrownItemManager = new ThrownItemManager(plugin, eventManager);
        this.userManager = new UserManager(plugin, thrownItemManager);
        Bukkit.getPluginManager().registerEvents(manager, plugin);
        this.plugin = plugin;
    }

    public void register(CustomItem item) {
        items.add(item);
        eventManager.register(item);
        manager.setItems(items);
        item.init(item.getOptions());
        item.setDelayManager(new DelayManager(item, eventManager, plugin));

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

}
