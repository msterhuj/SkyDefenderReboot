package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.locations.BannerLocation;
import net.msterhuj.skydefenderreboot.core.locations.SpawnLocation;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import org.bukkit.Bukkit;
import lombok.Data;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterManager;
import org.bukkit.GameMode;
import org.bukkit.GameRule;

@Data
public class GameData {

    private GameStatus gameStatus;
    private SpawnLocation spawnLocation;
    private BannerLocation bannerLocation;

    private TeleporterManager teleporterManager;
    private TeamManager teamManager;

    public GameData() {
        this.gameStatus = GameStatus.WAITING;
        this.teleporterManager = new TeleporterManager();
        this.teamManager = new TeamManager();
    }

    public boolean isGameStatus(GameStatus gameStatus) {
        return this.gameStatus == gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        if (this.gameStatus == gameStatus) return;
        this.gameStatus = gameStatus;
        switch (gameStatus) {
            case STARTING:
                WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                break;
            case FINISH:
                Bukkit.broadcastMessage("§aGame finished!"); // todo do it only with online players
                for (TeamPlayer teamPlayer : this.teamManager.getTeamPlayers()) {
                    teamPlayer.getPlayerByUUID().setGameMode(GameMode.CREATIVE);
                }
                WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                break;
        }
    }
}
