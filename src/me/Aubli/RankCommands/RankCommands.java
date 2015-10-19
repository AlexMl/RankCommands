package me.Aubli.RankCommands;

import java.util.logging.Level;

import me.Aubli.RankCommands.Rank.RankManager;
import me.Aubli.Util.Logger.PluginOutput;

import org.bukkit.plugin.java.JavaPlugin;


public class RankCommands extends JavaPlugin {
    
    private static RankCommands instance;
    private static PluginOutput logger;
    
    private boolean debugMode;
    private int logLevel;
    
    @Override
    public void onDisable() {
	logger.log(getClass(), "Plugin disabled!", false);
    }
    
    @Override
    public void onEnable() {
	instance = this;
	
	loadPluginConfig();
	
	logger = new PluginOutput(getInstance(), this.debugMode, this.logLevel);
	
	new RankManager();
	getCommand("rank").setExecutor(new CommandExecuter());
	
	logger.log(getClass(), "Plugin enabled!", false);
    }
    
    public static RankCommands getInstance() {
	return instance;
    }
    
    public static PluginOutput getPluginLogger() {
	return logger;
    }
    
    private void loadPluginConfig() {
	
	getConfig().addDefault("plugin.debugMode", false);
	getConfig().addDefault("plugin.logLevel", Level.INFO.intValue());
	
	this.debugMode = getConfig().getBoolean("plugin.debugMode");
	this.logLevel = getConfig().getInt("plugin.logLevel");
	
	getConfig().options().copyDefaults(true);
	saveConfig();
    }
    
    public void reloadPluginConfig() {
	reloadConfig();
	loadPluginConfig();
	RankManager.reloadRanks();
    }
    
}
