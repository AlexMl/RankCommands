package me.Aubli.VantiUtilities.Rank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.Aubli.VantiUtilities.VantiUtilities;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class RankManager {
    
    private static RankManager instance;
    
    private static File rankFile;
    
    private static List<Rank> ranks = new ArrayList<Rank>();
    
    public RankManager() {
	instance = this;
	rankFile = new File(VantiUtilities.getInstance().getDataFolder(), "ranks.yml");
	loadContent();
    }
    
    private void loadContent() {
	
	FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(rankFile);
	
	if (!rankFile.exists()) {
	    try {
		rankFile.createNewFile();
		
		fileConfig.options().header("This is the rank configuration of " + VantiUtilities.getInstance().getName() + "!\nUse '<player>' to address the player and '<tag>' for additional command arguments\nAvailable options are:\n'permission:' Permission to execute /rank rankname player\n'commands:' the commands in list format\n'message:' the message send to the player who received the rank\n'cost:' the cost the receiver has to pay\n");
		fileConfig.set("ranks", "[]");
		
		fileConfig.options().copyHeader();
		fileConfig.save(rankFile);
	    } catch (IOException e) {
		VantiUtilities.getPluginLogger().log(getClass(), Level.WARNING, "Could not save rank file: " + e.getMessage(), true, false, e);
	    }
	    return;
	}
	
	if (fileConfig.get("ranks") != null) {
	    
	    if (fileConfig.getConfigurationSection("ranks") != null) {
		for (Entry<String, Object> entry : fileConfig.getConfigurationSection("ranks").getValues(false).entrySet()) {
		    
		    String rankName = entry.getKey();
		    String rankPermission = fileConfig.getString("ranks." + entry.getKey() + ".permission");
		    List<String> commandList = fileConfig.getStringList("ranks." + entry.getKey() + ".commands");
		    String message = fileConfig.getString("ranks." + entry.getKey() + ".message");
		    double price = fileConfig.getDouble("ranks." + entry.getKey() + ".cost");
		    
		    Rank rank = new Rank(rankName, rankPermission, commandList, message, price);
		    ranks.add(rank);
		    VantiUtilities.getPluginLogger().log(getClass(), Level.FINE, "Successfully loaded " + rank, true, true);
		}
	    }
	    
	}
    }
    
    private static RankManager getManager() {
	return instance;
    }
    
    public static void reloadRanks() {
	ranks.clear();
	getManager().loadContent();
    }
    
    public static Rank getRank(String name) {
	for (Rank rank : ranks) {
	    if (rank.getName().equalsIgnoreCase(name)) {
		return rank;
	    }
	}
	return null;
    }
    
    public static void execute(Rank rank, OfflinePlayer player) throws NullPointerException, EconomyException {
	execute(rank, player, "");
    }
    
    public static void execute(Rank rank, OfflinePlayer player, String tag) throws NullPointerException, EconomyException {
	if (rank == null || player == null) {
	    throw new NullPointerException("Rank and Player can not be null!");
	}
	
	if (rank.getPrice() > 0) {
	    EconomyResponse response;
	    if (VantiUtilities.getEconomyProvider().has(player, rank.getPrice())) {
		response = VantiUtilities.getEconomyProvider().withdrawPlayer(player, rank.getPrice());
	    } else {
		throw new EconomyException("Player has not enough money!");
	    }
	    
	    if (response != null && response.type != ResponseType.SUCCESS) {
		throw new EconomyException(response.errorMessage);
	    }
	}
	
	for (String command : rank.getCommandList()) {
	    VantiUtilities.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", player.getName()).replace("<tag>", tag));
	}
	
	RankMessages.sendMessage(player, rank.getMessage());
	VantiUtilities.getPluginLogger().log(RankManager.class, Level.INFO, "Player " + player.getName() + " moved to '" + rank.getName() + "' successfully!", true, true);
    }
    
    public static class EconomyException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public EconomyException() {
	    super();
	}
	
	public EconomyException(String message) {
	    super(message);
	}
    }
}
