package net.msterhuj.skydefenderreboot.core.world;

import net.msterhuj.skydefenderreboot.utils.ServerPropertiesParser;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager {

    public static World getWorld() {
        String worldName = ServerPropertiesParser.getString("level-name");
        return Bukkit.getWorld(worldName);
    }

    public static int getDay() {
        World world = getWorld();
        return (int) (world.getFullTime() / 24000);
    }

    public static void setDay(long day) {
        World world = getWorld();
        world.setFullTime(day * 24000);
    }

    private static long getNextTime(WorldTime worldTime) {
        // Helper method to get the next time for the given WorldTime
        WorldTime[] values = WorldTime.values();
        int nextIndex = (worldTime.ordinal() + 1) % values.length;
        return values[nextIndex].getTime();
    }

    public static WorldTime getCurrentWorldTime() {
        int time = (int) (getWorld().getTime());

        for (WorldTime worldTime : WorldTime.values()) {
            if (time >= worldTime.getTime() && time < getNextTime(worldTime)) {
                return worldTime;
            }
        }
        // Default to MIDNIGHT if no match is found (should not happen)
        return WorldTime.MIDNIGHT;
    }

    public static boolean isNearNextTimeSet(int thresholdTicks) {
        WorldTime currentWorldTime = getCurrentWorldTime();
        long currentTime = getWorld().getTime();
        long nextTimeSet = getNextTime(currentWorldTime);

        // Check if we are within the threshold ticks before transitioning to the next time set
        return currentTime >= nextTimeSet - thresholdTicks && currentTime < nextTimeSet;
    }
}
