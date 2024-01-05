package net.msterhuj.skydefenderreboot.core.teams;

import lombok.Data;

import java.util.UUID;

@Data
public class TeamPlayer {

    private UUID uuid;
    private String pseudo;
    private boolean isAlive;
    private TeamType teamType;

    public TeamPlayer() {
        this.isAlive = true;
    }
}
