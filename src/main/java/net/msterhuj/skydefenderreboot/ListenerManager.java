package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameListener;
import net.msterhuj.skydefenderreboot.core.teams.TeamListener;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterListener;
import net.msterhuj.skydefenderreboot.core.world.WorldListener;

public class ListenerManager {
    public ListenerManager(SkyDefenderReboot skyDefenderReboot) {
        skyDefenderReboot.getServer().getPluginManager().registerEvents(new TeleporterListener(), skyDefenderReboot);
        skyDefenderReboot.getServer().getPluginManager().registerEvents(new TeamListener(), skyDefenderReboot);
        skyDefenderReboot.getServer().getPluginManager().registerEvents(new GameListener(), skyDefenderReboot);
        skyDefenderReboot.getServer().getPluginManager().registerEvents(new WorldListener(), skyDefenderReboot);
    }
}
