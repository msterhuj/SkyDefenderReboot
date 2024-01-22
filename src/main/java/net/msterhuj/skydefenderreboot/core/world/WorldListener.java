package net.msterhuj.skydefenderreboot.core.world;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import net.msterhuj.skydefenderreboot.core.teams.TeamType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class WorldListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPortal(PlayerPortalEvent event) {
        // todo setup a function check on world manager to check if the player can go to the nether to simplify this function
        int allowNetherAtDay = SkyDefenderReboot.getGameConfig().getAllowNetherAtDay();
        int currentDay = WorldManager.getDay();
        TeamPlayer teamPlayer = SkyDefenderReboot.getGameManager().getTeamManager().getTeamPlayer(event.getPlayer());
        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (currentDay < allowNetherAtDay && !teamPlayer.isTeam(TeamType.DEFENDER)) {
                event.getPlayer().sendMessage("§cYou can't go to the nether before day " + allowNetherAtDay + "!");
                event.setCancelled(true);
            }
        }
    }
}
