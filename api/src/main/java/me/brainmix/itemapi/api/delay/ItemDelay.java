package me.brainmix.itemapi.api.delay;

import org.bukkit.ChatColor;

public class ItemDelay {

    private int delay;
    private String pattern;
    private ChatColor colorFull;
    private ChatColor colorLeft;
    private ChatColor colorRight;

    public ItemDelay(int delay, String pattern, ChatColor colorFull, ChatColor colorLeft, ChatColor colorRight) {
        this.delay = delay;
        this.pattern = pattern;
        this.colorFull = colorFull;
        this.colorLeft = colorLeft;
        this.colorRight = colorRight;
    }

    public ItemDelay(int delay, String pattern) {
        this(delay, pattern, ChatColor.AQUA, ChatColor.RED, ChatColor.GREEN);
    }

    public ItemDelay(int delay) {
        this(delay, "%delay%");
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public ChatColor getColorFull() {
        return colorFull;
    }

    public void setColorFull(ChatColor colorFull) {
        this.colorFull = colorFull;
    }

    public ChatColor getColorLeft() {
        return colorLeft;
    }

    public void setColorLeft(ChatColor colorLeft) {
        this.colorLeft = colorLeft;
    }

    public ChatColor getColorRight() {
        return colorRight;
    }

    public void setColorRight(ChatColor colorRight) {
        this.colorRight = colorRight;
    }

}
