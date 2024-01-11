package net.msterhuj.skydefenderreboot.core.teams;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.GameManager;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamListener implements Listener {
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) { // todo move to function for handling player join
        Player player = event.getPlayer();
        Server server = SkyDefenderReboot.getInstance().getServer();
        TeamManager teamManager = GameManager.getInstance().getTeamManager();
        if (teamManager.registerPlayer(player)) SkyDefenderReboot.getInstance().saveGameManager();
        
        switch (SkyDefenderReboot.getGameManager().getGameStatus()) {
            case LOBBY:
                server.broadcastMessage("§aWaiting for players...");
                if (!player.isOp()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    if (GameManager.getInstance().getSpawnLocation() == null) {
                        player.sendMessage("§cSpawn location not set!");
                        SkyDefenderReboot.getInstance().getLogger().warning("Spawn location not set player " + player.getName() + " cannot be teleported to spawn!");
                    } else player.teleport(SkyDefenderReboot.getGameManager().getSpawnLocation().getLocation().add(0.5, 0.2, 0.5));
                }
                break;

            case RUNNING:
                server.broadcastMessage("§aGame started!");
                TeamPlayer teamPlayer = teamManager.getTeamPlayer(player);
                if (teamPlayer.isTeam(TeamType.SPECTATOR)) player.setGameMode(GameMode.SPECTATOR);
                if (!teamPlayer.isAlive()) player.setGameMode(GameMode.SPECTATOR);
                break;

            case PAUSED:
                server.broadcastMessage("§aGame paused!");
                break;

            case FINISH:
                server.broadcastMessage("§aGame finished!");
                player.setGameMode(GameMode.SPECTATOR);
                break;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        // todo move to function for handling player death
        if (GameManager.getInstance().isGameStatus(GameStatus.LOBBY)) return;
        Player player = event.getEntity();
        TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
        TeamPlayer teamPlayer = teamManager.getTeamPlayer(player);
        if(teamPlayer.getTeamType() == TeamType.DEFENDER) return;
        teamPlayer.setAlive(false);
        player.setGameMode(GameMode.SPECTATOR);
    }
}
