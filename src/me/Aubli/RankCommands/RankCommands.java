package me.Aubli.RankCommands;

import org.bukkit.plugin.java.JavaPlugin;


public class RankCommands extends JavaPlugin {
    
    private static RankCommands instance;
    
    @Override
    public void onDisable() {
	
    }
    
    @Override
    public void onEnable() {
	instance = this;
    }
    
    public static RankCommands getInstance() {
	return instance;
    }
    
}
