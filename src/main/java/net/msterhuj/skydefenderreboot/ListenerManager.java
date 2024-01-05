package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterListener;

public class ListenerManager {
    public ListenerManager(SkyDefenderReboot skyDefenderReboot) {
        skyDefenderReboot.getServer().getPluginManager().registerEvents(new TeleporterListener(), skyDefenderReboot);
    }
}
