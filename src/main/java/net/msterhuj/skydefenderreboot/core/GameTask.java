package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import net.msterhuj.skydefenderreboot.core.world.WorldTime;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/* TODO rework structure of this class with other class for event managing */
public class GameTask extends BukkitRunnable {

    private int currentDay = 0;
    private WorldTime approachingTime = null;
    private boolean netherAnnounced = false;

    @Override
    public void run() { // run every second
        SkyDefenderReboot instance = SkyDefenderReboot.getInstance();
        GameManager data = SkyDefenderReboot.getGameManager();
        // check if game is started
        if (!data.isGameStatus(GameStatus.RUNNING)) return;

        if (this.currentDay != WorldManager.getDay()) {
            this.currentDay = WorldManager.getDay();
            Bukkit.broadcastMessage("§aDay " + this.currentDay);
        }

        if (WorldManager.isNearNextTimeSet(20*60*3)) { // 3 minutes before next time set
            switch (WorldManager.getCurrentWorldTime()) {
                case SUNRISE:
                    if (this.approachingTime == WorldTime.SUNRISE) break;
                    this.approachingTime = WorldTime.SUNRISE;
                    Bukkit.broadcastMessage("§aSunrise approaching");
                    break;
                case NOON:
                    if (this.approachingTime == WorldTime.NOON) break;
                    this.approachingTime = WorldTime.NOON;
                    Bukkit.broadcastMessage("§aNoon approaching");
                    break;
                case NIGHT:
                    if (this.approachingTime == WorldTime.NIGHT) break;
                    this.approachingTime = WorldTime.NIGHT;
                    Bukkit.broadcastMessage("§aNight approaching");
                    break;
                case MIDNIGHT:
                    if (this.approachingTime == WorldTime.MIDNIGHT) break;
                    this.approachingTime = WorldTime.MIDNIGHT;
                    Bukkit.broadcastMessage("§aMidnight approaching");
                    break;
            }
        }
        // annouce
        // announce nether if it's not announced yet, and it's day from the config
        if (!this.netherAnnounced && this.currentDay == SkyDefenderReboot.getGameConfig().getAllowNetherAtDay()) {
            Bukkit.broadcastMessage("§aNether is now open for everyone!");
            this.netherAnnounced = true;
        }
        // check if worldborder should be shrunk
        if (SkyDefenderReboot.getGameConfig().getWorldborderStartReduceAtDay() >= this.currentDay) {
            WorldManager.setDayBorder(this.currentDay);
        }
    }
}
