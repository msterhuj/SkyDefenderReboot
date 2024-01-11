package net.msterhuj.skydefenderreboot.core;

/**
 * The {@code GameStatus} enum represents the possible states that a game can be in.
 * Each status defines the allowed actions that can be performed during that state.
 */
public enum GameStatus {
    LOBBY,      // Game is in the lobby, can only start or reset the game.
    STARTING,   // Game is in the process of starting, can only reset the game.
    RUNNING,    // Game is actively running, can only pause or reset the game.
    PAUSED,     // Game is paused, can only resume or reset the game.
    RESUMING,   // Game is in the process of resuming, can only pause or reset the game.
    FINISH,     // Game has finished, can only reset the game.
    RESETTING,  // Game is resetting, no actions can be performed before returning to LOBBY.
}
