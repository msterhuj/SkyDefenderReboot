package net.msterhuj.skydefenderreboot.core.teams;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamListener implements Listener {
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Server server = SkyDefenderReboot.getInstance().getServer();
        TeamManager teamManager = SkyDefenderReboot.getData().getTeamManager();
        if (teamManager.registerPlayer(player)) SkyDefenderReboot.getInstance().saveData();
        
        switch (SkyDefenderReboot.getData().getGameStatus()) {
            case WAITING:
                server.broadcastMessage("§aWaiting for players...");
                if (!player.isOp()) player.setGameMode(GameMode.ADVENTURE);
                break;

            case STARTED:
                server.broadcastMessage("§aGame started!");
                TeamPlayer teamPlayer = teamManager.getTeamPlayer(player);
                if (teamPlayer.getTeamType() == TeamType.SPECTATOR) player.setGameMode(GameMode.SPECTATOR);
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
}
