package net.msterhuj.skydefenderreboot.core.world;

import lombok.Getter;

public enum WorldTime {
    SUNRISE(0, "Sunrise"),
    DAY(1000, "Day"),
    NOON(6000, "Noon"),
    SUNSET(12000, "Sunset"),
    NIGHT(13000, "Night"),
    MIDNIGHT(18000, "Midnight");

    @Getter
    private final long time;
    @Getter
    private final String name;

    WorldTime(long tick, String name) {
        this.time = tick;
        this.name = name;
    }
}
