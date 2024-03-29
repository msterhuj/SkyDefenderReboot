package net.msterhuj.skydefenderreboot.core.teams;

import lombok.Getter;

@Getter
public enum TeamType {
    DEFENDER("defender"),
    ATTACKER("attacker"),
    SPECTATOR("spectator");

    private String name;

    TeamType(String name) {
        this.name = name;
    }

    public static TeamType getTeamType(String string) {
        for (TeamType teamType : TeamType.values()) {
            if (teamType.getName().equalsIgnoreCase(string)) return teamType;
        }
        return null;
    }
}
