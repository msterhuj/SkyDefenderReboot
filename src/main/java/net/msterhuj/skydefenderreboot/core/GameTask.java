package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private int lastDay = 0;
    @Override
    public void run() {
        // run every second
        SkyDefenderReboot instance = SkyDefenderReboot.getInstance();
        GameData data = SkyDefenderReboot.getData();
        // check if game is started
        // if the game is started check the date and announce
    }
}
