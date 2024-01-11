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
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

@Data
public class GameManager {

    private GameStatus gameStatus;
    private SpawnLocation spawnLocation;
    private BannerLocation bannerLocation;

    private TeleporterManager teleporterManager;
    private TeamManager teamManager;
    private WorldManager worldManager;

    public GameManager() {
        this.gameStatus = GameStatus.LOBBY;
        this.teleporterManager = new TeleporterManager();
        this.teamManager = new TeamManager();
        this.worldManager = new WorldManager();
    }

    public boolean isGameStatus(GameStatus gameStatus) {
        return this.gameStatus == gameStatus;
    }

    public void setGameStatus(GameStatus newGameStatus, @Nullable CommandSender commandSender) {
        // todo move other game event into this method and submethods for actions by game status
        if (isGameStatus(newGameStatus)) return;
        this.gameStatus = newGameStatus;
        switch (newGameStatus) {
            case RUNNING:
                WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                WorldManager.setDay(0);
                break;
            case FINISH:
                Bukkit.broadcastMessage("§aGame finished!");
                for (TeamPlayer teamPlayer : this.teamManager.getTeamPlayers()) {
                    if (!teamPlayer.isOnline()) continue;
                    teamPlayer.getPlayerByUUID().setGameMode(GameMode.CREATIVE);
                }
                WorldManager.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                break;
        }
    }

    public static GameManager getInstance() {
        return SkyDefenderReboot.getGameManager();
    }
}
