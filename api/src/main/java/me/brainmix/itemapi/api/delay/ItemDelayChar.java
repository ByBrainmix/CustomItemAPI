package me.brainmix.itemapi.api.delay;

import me.brainmix.itemapi.api.utils.Actionbar;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.entity.Player;

public class ItemDelayChar extends ItemDelay implements DelayDisplay {

    private String character;
    private int size;

    public ItemDelayChar(int delay, String character, int size) {
        super(delay);
        this.character = character;
        this.size = size;
    }

    public ItemDelayChar(int delay, String pattern) {
        super(delay, pattern);
        this.character = "|";
        this.size = 50;
    }

    public ItemDelayChar(int delay, String pattern, String character, int size) {
        super(delay, pattern);
        this.character = character;
        this.size = size;
    }

    public ItemDelayChar(int delay) {
        this(delay, "|", 50);
    }

    @Override
    public void display(Player player, int timeLeft) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            if(timeLeft == 0) {
                sb.append(getColorFull());
            } else if(i < (double) timeLeft / getDelay() * size) {
                sb.append(getColorLeft());
            } else {
                sb.append(getColorRight());
            }
            sb.append(character);
        }
        Actionbar.send(player, ItemUtils.format(getPattern(), "%delay%", sb.toString()));
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
