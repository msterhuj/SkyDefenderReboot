package net.msterhuj.skydefenderreboot.core;

import lombok.Data;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import net.msterhuj.skydefenderreboot.core.teleporter.Teleporter;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterManager;

import java.util.HashMap;

@Data
public class SkyDefenderRebootData {

    private GameStatus gameStatus;

    private TeleporterManager teleporterManager;

    public SkyDefenderRebootData() {
        this.gameStatus = GameStatus.WAITING;
        this.teleporterManager = new TeleporterManager();
    }
}
