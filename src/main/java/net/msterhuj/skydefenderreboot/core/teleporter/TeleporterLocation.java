package net.msterhuj.skydefenderreboot.core.teleporter;

import lombok.Data;
import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class TeleporterLocation {

    private TeleporterType type;
    private Material oldBlock;
    private UUID worldUUID;
    private int x, y, z;

    public TeleporterLocation(Player player, TeleporterType type) {
        this.type = type;
        this.worldUUID = player.getWorld().getUID();
        this.x = player.getLocation().getBlockX();
        this.y = player.getLocation().getBlockY();
        this.z = player.getLocation().getBlockZ();
    }

    public void setupBock(Player player) {

        Location location = player.getLocation();

        if (this.type == TeleporterType.INPUT) {

            Block block = location.getBlock();
            block.setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);

            // set bedrock underfoot
            location.setY(location.getBlockY()-1);
            block = location.getBlock();
            this.oldBlock = block.getType();
            block.setType(Material.BEDROCK);

        } else if (this.type == TeleporterType.OUTPUT) {

            // set kelp block underfoot
            location.setY(location.getBlockY() - 1);
            Block block = location.getBlock();
            this.oldBlock = block.getType();
            block.setType(Material.DRIED_KELP_BLOCK);

        }
    }

    public void resetBlock() {
        if (this.type == TeleporterType.INPUT) {

            Location location = this.getLocation();
            // remove plate
            Block block = location.getBlock();
            block.setType(Material.AIR);

            // remove bedrock
            location.setY(location.getBlockY()-1);
            block = location.getBlock();
            block.setType(this.oldBlock);

        } else if (this.type == TeleporterType.OUTPUT) {

            // remove kelp block
            Location location = this.getLocation();
            location.setY(location.getBlockY()-1);
            Block block = location.getBlock();
            block.setType(this.oldBlock);

        }
    }

    public Location getLocation() {
        return new Location(SkyDefenderReboot.getInstance().getServer().getWorld(this.worldUUID), this.x, this.y, this.z);
    }
}

