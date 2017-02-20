package me.brainmix.itemapi.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.brainmix.itemapi.api.utils.Actionbar;
import me.brainmix.itemapi.api.utils.ItemSlot;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.util.Vector;

/**
 * Player wrapper with some extra utils
 */
public class ItemUser {

    private Player player;
    private ItemRegister register;
    private Set<ItemStack> freezedItems = new HashSet<>();

    public ItemUser(Player player, ItemRegister register) {
        this.player = player;
        this.register = register;
    }

    public boolean hasItemInHand(CustomItem item) {
        return item.compare(player.getItemInHand());
    }

    public void sendFormatted(String text, Object... replace) {
        player.sendMessage(ItemUtils.format(text, replace));
    }

    public void sendFormatted(List<String> lines, Object... replace) {
        lines.forEach(l -> sendFormatted(l, replace));
    }

    public void playSound(Sound sound) {
        playSound(sound, 1, 1);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        playSound(new ClickSound(sound, volume, pitch));
    }

    public void playSound(ClickSound sound) {
        sound.play(player);
    }

    public void playSound(ClickSound sound, Location location) {
        sound.playAt(player, location);
    }

    public void sendActionbar(String text, Object... replace) {
        Actionbar.send(player, ItemUtils.format(text, replace));
    }

    public void startDelay(CustomItem item) {
        item.getDelayManager().startDelay(player, item.getOptions().getItemDelay());
    }

    public void stopDelay(CustomItem item) {
        item.getDelayManager().stopDelay(player);
    }

    public boolean isDelayRunning(CustomItem item) {
        return item.getDelayManager().isRunning(player);
    }

    public int getDelayTimeLeft(CustomItem item) {
        return item.getDelayManager().getTimeLeft(player);
    }

    public int getSlot(ItemStack item) {
        return ItemUtils.getSlot(player, item);
    }

    public ItemSlot getItemSlot(ItemStack item) {
        int slot = getSlot(item);
        return new ItemSlot(slot, player, item);
    }

    public ItemSlot getItemSlot(CustomItem item) {
        int slot = getSlot(item.getOptions().getItemStack());
        return new ItemSlot(slot, player, item.getOptions().getItemStack());
    }

    public boolean hasItem(ItemStack item) {
        return ItemUtils.hasItem(player, item);
    }

    public void clearItem(ItemStack item) {
        ItemUtils.clearItem(player, item);
    }

    public void setUnmoveable(int time) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, 128, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 255, false, false));
    }

    public void setUnmoveable() {
        setUnmoveable(1000000);
    }

    public void setMoveable() {
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.SLOW);
    }

    public boolean isUnmoveable() {
        return player.hasPotionEffect(PotionEffectType.JUMP) && player.hasPotionEffect(PotionEffectType.JUMP);
    }

    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public void freezeItem(ItemStack item) {
        freezedItems.add(item);
    }

    public void freezeItem(CustomItem item) {
        freezeItem(item.getOptions().getItemStack());
    }

    public void unfreezeItem(ItemStack item) {
        Set<ItemStack> toRemove = freezedItems.stream().filter(i -> ItemUtils.compare(i, item)).collect(Collectors.toSet());
        toRemove.forEach(freezedItems::remove);
    }

    public void unfreezeItem(CustomItem item) {
        unfreezeItem(item.getOptions().getItemStack());
    }

    public boolean isFreezed(ItemStack item) {
        return !freezedItems.stream().filter(i -> ItemUtils.compare(i, item)).collect(Collectors.toList()).isEmpty();
    }

    public Set<ItemStack> getFreezedItems() {
        return freezedItems;
    }

    // method just for debug purposes
    public void sendValue(String name, Object value) {
        sendFormatted("&8[&7" + name + "&8]: &e" + value);
    }

    // method just for debug purposes
    public void sendValue(Object... values) {
        List<String> r = new ArrayList<>();
        List<String> o = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) {
                r.add(String.valueOf(values[i]));
            } else {
                o.add(String.valueOf(values[i]));
            }
        }

        String output = "";

        for(String name : r) {
            String value = o.get(r.indexOf(name));
            output += "&8[&7" + name + "&8]: &e" + value + " ";
        }
        sendFormatted(output);

    }

    public Item throwItem(CustomItem item) {
        return register.getThrowManager().throwItem(item, this);
    }

    public <T extends Projectile> T shootProjectile(CustomItem item, Class<T> type) {
        return register.getProjectileManager().shoot(item, this, type);
    }

    public <T extends Projectile> T shootProjectile(CustomItem item, Class<T> type, Vector velocity) {
        T projectile = shootProjectile(item, type);
        projectile.setVelocity(velocity);
        return projectile;
    }

    public Player getPlayer() {
        return player;
    }

}
