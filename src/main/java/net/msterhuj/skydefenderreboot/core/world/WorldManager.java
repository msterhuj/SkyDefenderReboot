package net.msterhuj.skydefenderreboot.core.world;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.utils.GameConfig;
import net.msterhuj.skydefenderreboot.utils.ServerPropertiesParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;

// todo remove static methods and use the instance of the class on game manager
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
        return values[nextIndex].getTicks();
    }

    public static WorldTime getCurrentWorldTime() {
        int time = (int) (getWorld().getTime());

        for (WorldTime worldTime : WorldTime.values()) {
            if (time >= worldTime.getTicks() && time < getNextTime(worldTime)) {
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

    public static void setupBorderCenter(Location location) {
        WorldBorder worldBorder = getWorld().getWorldBorder();
        worldBorder.setCenter(location);
        worldBorder.setSize(SkyDefenderReboot.getGameConfig().getWorldborderStartRadius());
    }

    public static double getBorderSizeForDay(int day) {
        GameConfig config = SkyDefenderReboot.getGameConfig();
        int startDay = config.getWorldborderStartReduceAtDay();
        int finishDay = config.getWorldborderFinishReduceAtDay();
        double startRadius = config.getWorldborderStartRadius();
        double finishRadius = config.getWorldborderFinishRadius();

        if (day < startDay) return startRadius;
        else if (day > finishDay) return finishRadius;

        double blockToReducePerDay = (startRadius - finishRadius) / (finishDay - startDay);
        double reduce = startRadius - blockToReducePerDay * (day - startDay);

        return Math.max(reduce, finishRadius);
    }

    public static void applyWorldBorder() {
        getWorld().getWorldBorder().setSize(getBorderSizeForDay(getDay()));
    }
}
