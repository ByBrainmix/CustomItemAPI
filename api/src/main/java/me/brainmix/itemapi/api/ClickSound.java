package me.brainmix.itemapi.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ClickSound {

    private Sound sound;
    private float volume;
    private float pitch;

    public ClickSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playAt(Player player, Location location) {
        player.playSound(location, sound, volume, pitch);
    }

    public void playAt(Location location) {
        Bukkit.getOnlinePlayers().forEach(p -> playAt(p, location));
    }

    public void play(Player player) {
        playAt(player, player.getLocation());
    }

    public void play() {
        Bukkit.getOnlinePlayers().forEach(this::play);
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
