package me.brainmix.itemapi.api;

import org.bukkit.inventory.ItemStack;

import me.brainmix.itemapi.api.delay.ItemDelay;

public class ItemOptions {

    private ItemStack itemStack;
    private ItemStack itemAfterUse;
    private ClickSound clickSound;
    private ItemDelay itemDelay;
    private ItemDelay autoItemDelay;
    private int removeAfterUse;
    private boolean drop = true;
    private boolean move = true;
    private boolean despawnable = true;
    private boolean cancellDefaults;
    private boolean disabled;
    private boolean interactWithOthers;

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemAfterUse() {
        return itemAfterUse;
    }

    public void setItemAfterUse(ItemStack itemAfterUse) {
        this.itemAfterUse = itemAfterUse;
    }

    public ClickSound getClickSound() {
        return clickSound;
    }

    public void setClickSound(ClickSound clickSound) {
        this.clickSound = clickSound;
    }

    public ItemDelay getItemDelay() {
        return itemDelay;
    }

    /**
     * Delay can manually be started with ItemUser#startDelay(CustomItem)
     * @param itemDelay Delay
     */
    public void setItemDelay(ItemDelay itemDelay) {
        this.itemDelay = itemDelay;
    }

    public ItemDelay getAutoItemDelay() {
        return autoItemDelay;
    }

    /**
     * The delay automaticly starts, if an clickevent gets called
     * don't work with ItemRightClickEntity- or ItemLeftClickEntity-events
     * @param autoItemDelay Delay
     */
    public void setAutoItemDelay(ItemDelay autoItemDelay) {
        this.autoItemDelay = autoItemDelay;
    }

    public int getRemoveAfterUse() {
        return removeAfterUse;
    }

    public void setRemoveAfterUse(int removeAfterUse) {
        this.removeAfterUse = removeAfterUse;
    }

    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isDespawnable() {
        return despawnable;
    }

    public void setDespawnable(boolean despawnable) {
        this.despawnable = despawnable;
    }

    public boolean isCancellDefaults() {
        return cancellDefaults;
    }

    public void setCancellDefaults(boolean cancellDefaults) {
        this.cancellDefaults = cancellDefaults;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isInteractWithOthers() {
        return interactWithOthers;
    }

    /**
     * If set to 'true' the clickevents won't handle if the user clicks on an interactable block,
     * like doors, craftingtables, buttons, etc...
     * @param interactWithOthers interactWithOthers
     */
    public void setInteractWithOthers(boolean interactWithOthers) {
        this.interactWithOthers = interactWithOthers;
    }

}
