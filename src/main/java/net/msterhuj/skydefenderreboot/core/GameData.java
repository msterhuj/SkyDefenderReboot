package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import org.bukkit.Bukkit;
import lombok.Data;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterManager;

@Data
public class GameData {

    private GameStatus gameStatus;
    private SpawnLocation spawnLocation;

    private TeleporterManager teleporterManager;
    private TeamManager teamManager;

    public GameData() {
        this.gameStatus = GameStatus.WAITING;
        this.teleporterManager = new TeleporterManager();
        this.teamManager = new TeamManager();
    }

    public void setGameStatus(GameStatus gameStatus) {
        if (this.gameStatus == gameStatus) return;
        this.gameStatus = gameStatus;
        switch (gameStatus) {
            case WAITING:
                SkyDefenderReboot.getInstance().getServer().broadcastMessage("§aWaiting for players...");
                break;

            case STARTED:
                Bukkit.broadcastMessage("§aGame started!");
                break;

            case PAUSED:
                Bukkit.broadcastMessage("§aGame paused!");
                break;

            case FINISH:
                Bukkit.broadcastMessage("§aGame finished!");
                break;
        }
    }
}
