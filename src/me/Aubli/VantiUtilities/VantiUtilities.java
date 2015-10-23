package me.Aubli.VantiUtilities;

import java.util.logging.Level;

import me.Aubli.Util.Logger.PluginOutput;
import me.Aubli.VantiUtilities.Rank.RankManager;
import me.Aubli.VantiUtilities.Rank.RankMessages;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class VantiUtilities extends JavaPlugin {
    
    private static VantiUtilities instance;
    private static PluginOutput logger;
    private static Economy econProvider;
    
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
	
	econProvider = loadVault();
	
	if (econProvider == null) {
	    getServer().getPluginManager().disablePlugin(this);
	    return;
	}
	
	new RankManager();
	new RankMessages();
	getCommand("rank").setExecutor(new CommandExecuter());
	getCommand("vanti").setExecutor(new CommandExecuter());
	getCommand("nightvision").setExecutor(new CommandExecuter());
	
	getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	
	logger.log(getClass(), "Plugin enabled!", false);
    }
    
    public static VantiUtilities getInstance() {
	return instance;
    }
    
    public static PluginOutput getPluginLogger() {
	return logger;
    }
    
    public static Economy getEconomyProvider() {
	return econProvider;
    }
    
    private Economy loadVault() {
	
	if (getServer().getPluginManager().getPlugin("Vault") != null) {
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    
	    if (economyProvider != null) {
		getPluginLogger().log(this.getClass(), Level.INFO, "Successfully hooked into Vault economy!", false, false);
		return economyProvider.getProvider();
	    } else {
		getPluginLogger().log(this.getClass(), Level.SEVERE, "Could not hook into Vault! Disabling plugin ...", false);
		return null;
	    }
	} else {
	    getPluginLogger().log(this.getClass(), Level.SEVERE, "Vault is not installed! Disabling plugin ...", false);
	    return null;
	}
	
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
