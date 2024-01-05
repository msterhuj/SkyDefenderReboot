package net.msterhuj.skydefenderreboot;


public class CommandManager {
    public CommandManager(SkyDefenderReboot skyDefenderReboot) {
        skyDefenderReboot.getCommand("skydefenderreboot").setExecutor(new Commands());
        skyDefenderReboot.getCommand("test").setExecutor(new TestCommands());
    }
}
