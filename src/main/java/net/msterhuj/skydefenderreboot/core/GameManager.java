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
public class GameManager {

    private GameStatus gameStatus;
    private SpawnLocation spawnLocation;
    private BannerLocation bannerLocation;
    private GameCache gameCache;

    private TeleporterManager teleporterManager;
    private TeamManager teamManager;
    private WorldManager worldManager;

    public GameManager() {
        setGameStatus(GameStatus.LOBBY);
        this.teleporterManager = new TeleporterManager();
        this.teamManager = new TeamManager();
        this.worldManager = new WorldManager();
        this.gameCache = new GameCache();
    }

    public boolean isGameStatus(GameStatus gameStatus) {
        return this.gameStatus == gameStatus;
    }

    public void setGameStatus(GameStatus newGameStatus) {
        if (isGameStatus(newGameStatus)) return;
        this.gameStatus = newGameStatus;
        switch (newGameStatus) {
            case RUNNING:
                applyStarting();
                break;
            case FINISH:
                applyRunning();
                break;
        }
    }

    private boolean applyLobby() {
        return true;
    }

    private boolean applyStarting() {
        if (!isGameStatus(GameStatus.STARTING)) return false;

        WorldManager.setDay(0);
        WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

        return true;
    }

    private boolean applyRunning() {
        if (!isGameStatus(GameStatus.RUNNING)) return false;
        return true;
    }

    private boolean applyPaused() {
        if (!isGameStatus(GameStatus.PAUSED)) return false;

        WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        return true;
    }

    private boolean applyResuming() {
        if (!isGameStatus(GameStatus.RESUMING)) return false;

        WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

        return true;
    }

    private boolean applyFinish() {
        if (!isGameStatus(GameStatus.FINISH)) return false;

        Bukkit.broadcastMessage("§aGame finished!");
        for (TeamPlayer teamPlayer : this.teamManager.getTeamPlayers()) {
            if (!teamPlayer.isOnline()) continue;
            teamPlayer.getPlayerByUUID().setGameMode(GameMode.CREATIVE);
        }
        WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        return true;
    }

    private boolean applyResetting() {
        if (!isGameStatus(GameStatus.RESETTING)) return false;
        return true;
    }

    public static GameManager getInstance() {
        return SkyDefenderReboot.getGameManager();
    }
}
