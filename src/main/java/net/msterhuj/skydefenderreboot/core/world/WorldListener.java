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
        int allowNetherAtDay = SkyDefenderReboot.getInstance().getConfig().getInt("allow_nether_at_day");
        int currentDay = WorldManager.getDay();
        TeamPlayer teamPlayer = SkyDefenderReboot.getData().getTeamManager().getTeamPlayer(event.getPlayer());
        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (currentDay < allowNetherAtDay && !teamPlayer.isTeam(TeamType.DEFENDER)) {
                event.setCancelled(true);
            }
        }
    }
}