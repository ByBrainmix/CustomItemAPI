package me.brainmix.itemapi.api.delay;

import me.brainmix.itemapi.api.utils.Actionbar;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ItemDelayChar extends ItemDelay implements DelayDisplay {

    private String character;
    private int size;
    private String pattern = "< %delay% >";
    private ChatColor colorFull = ChatColor.AQUA;
    private ChatColor colorLeft = ChatColor.RED;
    private ChatColor colorRight = ChatColor.GREEN;

    public ItemDelayChar(int delay, String character, int size) {
        super(delay);
        this.character = character;
        this.size = size;
    }

    public ItemDelayChar(int delay, String pattern) {
        super(delay);
        this.pattern = pattern;
        this.character = "|";
        this.size = 50;
    }

    public ItemDelayChar(int delay, String pattern, String character, int size) {
        super(delay);
        this.pattern = pattern;
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
