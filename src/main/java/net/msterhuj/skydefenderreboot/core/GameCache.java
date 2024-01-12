package net.msterhuj.skydefenderreboot.core;

import lombok.Data;

@Data
public class GameCache {
    private int currentDay = 0;
    private boolean netherOpenAnnounced = false;
}
