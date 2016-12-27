package me.brainmix.itemapi.api.delay;

import org.bukkit.entity.Player;

public class ItemDelayText extends ItemDelay implements DelayDisplay {

    private String text;

    public ItemDelayText(int delay, String text) {
        super(delay);
        this.text = text;
    }

    public ItemDelayText(int delay, String pattern, String text) {
        super(delay, pattern);
        this.text = text;
    }

    public ItemDelayText(int delay) {
        this(delay, "random text");
    }

    @Override
    public void display(Player player, int timeLeft) {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
