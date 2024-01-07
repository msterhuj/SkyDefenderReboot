package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private int currentDay = 0;

    @Override
    public void run() { // run every second
        SkyDefenderReboot instance = SkyDefenderReboot.getInstance();
        GameData data = SkyDefenderReboot.getData();
        // check if game is started
        //if (!data.isGameStatus(GameStatus.STARTED)) return;

        if (this.currentDay != WorldManager.getDay()) {
            this.currentDay = WorldManager.getDay();
            Bukkit.broadcastMessage("§aDay " + this.currentDay);
        }

        if (WorldManager.isNearNextTimeSet(20*60*3)) { // 3 minutes before next time set
            switch (WorldManager.getCurrentWorldTime()) {
                case SUNRISE:
                    Bukkit.broadcastMessage("§aSunrise approaching");
                    break;
                case NOON:
                    Bukkit.broadcastMessage("§aNoon approaching");
                    break;
                case NIGHT:
                    Bukkit.broadcastMessage("§aNight approaching");
                    break;
                case MIDNIGHT:
                    Bukkit.broadcastMessage("§aMidnight approaching");
                    break;
            }
        }

        // annouce

    }
}
