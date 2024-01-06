package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import net.msterhuj.skydefenderreboot.core.teams.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        TeamManager teamManager = SkyDefenderReboot.getData().getTeamManager();
        TeamPlayer teamPlayer = teamManager.getTeamPlayer(event.getEntity());
        if (SkyDefenderReboot.getData().isGameStatus(GameStatus.STARTED)) {
            if (teamPlayer.getTeamType() == TeamType.ATTACKER) {
                // check if he's the last one in the attacker team
                if (teamManager.getTeamPlayers().stream().filter(teamPlayer1 -> teamPlayer1.getTeamType() == TeamType.ATTACKER).count() == 1) {
                    // defender team wins
                    Bukkit.broadcastMessage("§aDefender team wins!");
                    SkyDefenderReboot.getData().setGameStatus(GameStatus.FINISH);
                    SkyDefenderReboot.getInstance().saveData();
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // check if it's the banner
        Location bannerLocation = SkyDefenderReboot.getData().getBannerLocation().getLocation();
        if (event.getBlock().getLocation().equals(bannerLocation)) {
            // attacker team wins
            Bukkit.broadcastMessage("§aAttacker team wins!");
            SkyDefenderReboot.getData().setGameStatus(GameStatus.FINISH);
            SkyDefenderReboot.getInstance().saveData();
        }
    }

    /*
     * when player right-clicks on a banner and has the metadata "setup_banner"
     * if the game is in WAITING status
     * set the banner location in the game data
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("setup_banner")) {
            player.removeMetadata("setup_banner", SkyDefenderReboot.getInstance());
            if (SkyDefenderReboot.getData().getGameStatus() != GameStatus.WAITING) {
                player.sendMessage("§cYou can't do this now (game is started)");
                return;
            }
            // if Material contains BANNER then set the banner
            if (event.getClickedBlock().getType().toString().contains("BANNER")) {
                GameData gameData = SkyDefenderReboot.getData();
                gameData.setBannerLocation(new BannerLocation(event.getClickedBlock().getLocation()));
                SkyDefenderReboot.getInstance().saveData();
                player.sendMessage("§aBanner set!");
            } else {
                player.sendMessage("§cYou must click on a banner!");
            }
        }
    }
}

