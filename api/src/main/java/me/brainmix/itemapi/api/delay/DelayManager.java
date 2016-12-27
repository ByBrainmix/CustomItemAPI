package me.brainmix.itemapi.api.delay;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.controllers.ItemEventManager;
import me.brainmix.itemapi.api.events.ItemDelayEndEvent;
import me.brainmix.itemapi.api.events.ItemDelayTickEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class DelayManager {

    private Map<Player, ItemDelay> delays = new HashMap<>();
    private Map<Player, Integer> timeLefts = new HashMap<>();

    private CustomItem item;
    private ItemEventManager eventManager;
    private Plugin plugin;

    public DelayManager(CustomItem item, ItemEventManager eventManager, Plugin plugin) {
        this.item = item;
        this.eventManager = eventManager;
        this.plugin = plugin;
    }

    public void startDelay(Player player, ItemDelay delay) {
        stopDelay(player);
        delays.put(player, delay);
        timeLefts.put(player, delay.getDelay());
        DelayDisplay delayDisplay = delay instanceof DelayDisplay ? (DelayDisplay) delay : null;

        new BukkitRunnable() {

            int timeLeft = delay.getDelay();

            @Override
            public void run() {

                if(!hasDelay(player)) {
                    timeLeft = 0;
                }

                if(item.compare(player.getItemInHand()) && delayDisplay != null) delayDisplay.display(player, timeLeft);

                if(timeLeft <= 0) {
                    this.cancel();
                    stopDelay(player);
                    eventManager.callEvent(item, new ItemDelayEndEvent(player, item.getOptions().getItemStack(), -1));
                    return;
                }

                timeLefts.put(player, timeLeft);
                eventManager.callEvent(item, new ItemDelayTickEvent(player, item.getOptions().getItemStack(), timeLeft));
                timeLeft--;
            }
        }.runTaskTimer(plugin, 0, 0);
    }

    public void stopDelay(Player player) {
        if(hasDelay(player)) {
            delays.remove(player);
            timeLefts.remove(player);
        }
    }

    public void resetTimer(Player player) {
        if(!hasDelay(player)) return;
        ItemDelay delay = delays.get(player);
        stopDelay(player);
        startDelay(player, delay);
    }

    public boolean hasDelay(Player player) {
        return delays.containsKey(player);
    }

    public boolean isRunning(Player player) {
        return hasDelay(player);
    }

    public int getTimeLeft(Player player) {
        return hasDelay(player) ? timeLefts.get(player) : -1;
    }

}
