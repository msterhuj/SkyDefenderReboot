package net.msterhuj.skydefenderreboot.core.world;

import lombok.Getter;

@Getter
public enum WorldTime {
    SUNRISE(0, "Sunrise"),
    DAY(1000, "Day"),
    NOON(6000, "Noon"),
    SUNSET(12000, "Sunset"),
    NIGHT(13000, "Night"),
    MIDNIGHT(18000, "Midnight");

    private final long ticks;
    private final String name;

    WorldTime(long ticks, String name) {
        this.ticks = ticks;
        this.name = name;
    }
}
