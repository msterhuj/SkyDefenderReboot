package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/* TODO rework structure of this class with other class for event managing */
public class GameTask extends BukkitRunnable {

    private SkyDefenderReboot instance;

    private GameManager gameManager;

    private GameCache cache;

    public GameTask(SkyDefenderReboot instance, GameManager gameManager) {
        this.instance = instance;
        this.gameManager = gameManager;
        this.cache = gameManager.getGameCache();
    }

    @Override
    public void run() { // run every second
        // check if game is started
        if (!this.gameManager.isGameStatus(GameStatus.RUNNING)) return;

        if (this.cache.getCurrentDay() != WorldManager.getDay()) {
            this.cache.setCurrentDay(WorldManager.getDay());
            Bukkit.broadcastMessage("§aDay " + this.cache.getCurrentDay());
        }
        // annouce todo add a function for announcer
        // announce nether if it's not announced yet, and it's day from the config
        if (!this.cache.isNetherOpenAnnounced() && this.cache.getCurrentDay() == SkyDefenderReboot.getGameConfig().getAllowNetherAtDay()) {
            Bukkit.broadcastMessage("§aNether is now open for everyone!");
            this.cache.setNetherOpenAnnounced(true);
        }
        // check if worldborder should be shrunk
        if (SkyDefenderReboot.getGameConfig().getWorldborderStartReduceAtDay() >= this.cache.getCurrentDay()) {
            WorldManager.applyWorldBorder();
        }
    }
}
