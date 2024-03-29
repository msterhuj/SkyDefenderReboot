package net.msterhuj.skydefenderreboot.utils;

import lombok.Getter;
import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class GameConfig {
    private boolean debug;
    private int spreadDistanceFromSpawnMin;
    private int spreadDistanceFromSpawnMax;

    private int allowNetherAtDay;

    private int worldborderStartReduceAtDay;
    private int worldborderFinishReduceAtDay;
    private double worldborderStartRadius;
    private double worldborderFinishRadius;
    private long worldborderMovementTime;

    public GameConfig() {
        load();
    }

    public void load() {
        FileConfiguration fileConfiguration = SkyDefenderReboot.getInstance().getConfig();

        debug = fileConfiguration.getBoolean("debug");
        spreadDistanceFromSpawnMin = fileConfiguration.getInt("spread_distance_from_spawn.min");
        spreadDistanceFromSpawnMax = fileConfiguration.getInt("spread_distance_from_spawn.max");

        allowNetherAtDay = fileConfiguration.getInt("allow_nether_at_day");

        worldborderStartReduceAtDay = fileConfiguration.getInt("worldborder.start_reduce_at_day");
        worldborderFinishReduceAtDay = fileConfiguration.getInt("worldborder.finish_reduce_at_day");
        worldborderStartRadius = fileConfiguration.getDouble("worldborder.start_radius");
        worldborderFinishRadius = fileConfiguration.getDouble("worldborder.finish_radius");
        worldborderMovementTime = fileConfiguration.getLong("worldborder.movement_time");
    }

    public void reload() {
        SkyDefenderReboot.getInstance().reloadConfig();
        load();
    }

    public boolean validate() {
        return true;
    }
}
