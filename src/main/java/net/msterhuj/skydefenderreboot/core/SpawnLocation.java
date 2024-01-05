package net.msterhuj.skydefenderreboot.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpawnLocation {
    private UUID worldUUID;
    private final int x, y, z;

    public SpawnLocation(Player player) {
        this.worldUUID = player.getWorld().getUID();
        this.x = player.getLocation().getBlockX();
        this.y = player.getLocation().getBlockY();
        this.z = player.getLocation().getBlockZ();
    }

    public World getWorld() {
        return Bukkit.getWorld(this.worldUUID);
    }

    public Location getLocation() {
        return new Location(this.getWorld(), this.x, this.y, this.z);
    }
}
