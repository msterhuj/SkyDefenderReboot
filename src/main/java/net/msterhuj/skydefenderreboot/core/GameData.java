package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import org.bukkit.Bukkit;
import lombok.Data;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterManager;
import org.bukkit.GameMode;

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
                for (TeamPlayer teamPlayer : this.teamManager.getTeamPlayers()) {
                    teamPlayer.getPlayerByUUID().setGameMode(GameMode.CREATIVE);
                }
                break;
        }
    }

    /*
     * when a block is broken, check if it's the banner
     * when player dies, check if he's the last one in the attacker team
     */
}
