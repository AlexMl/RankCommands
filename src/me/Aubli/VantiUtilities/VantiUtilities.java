package me.Aubli.VantiUtilities;

import java.util.logging.Level;

import me.Aubli.Util.Logger.PluginOutput;
import me.Aubli.VantiUtilities.Rank.RankManager;
import me.Aubli.VantiUtilities.Rank.RankMessages;

import org.bukkit.plugin.java.JavaPlugin;


public class VantiUtilities extends JavaPlugin {
    
    private static VantiUtilities instance;
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
	new RankMessages();
	getCommand("rank").setExecutor(new CommandExecuter());
	getCommand("vanti").setExecutor(new CommandExecuter());
	
	logger.log(getClass(), "Plugin enabled!", false);
    }
    
    public static VantiUtilities getInstance() {
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
