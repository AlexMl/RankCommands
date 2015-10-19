package me.Aubli.RankCommands.Rank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.Aubli.RankCommands.RankCommands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class RankManager {
    
    private static RankManager instance;
    
    private static File rankFile;
    
    private static List<Rank> ranks = new ArrayList<Rank>();
    
    public RankManager() {
	instance = this;
	rankFile = new File(RankCommands.getInstance().getDataFolder(), "ranks.yml");
	loadContent();
    }
    
    private void loadContent() {
	
	FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(rankFile);
	
	if (!rankFile.exists()) {
	    try {
		rankFile.createNewFile();
		
		fileConfig.options().header(""); // TODO add header
		fileConfig.set("ranks", "");
		
		fileConfig.options().copyHeader();
		fileConfig.save(rankFile);
	    } catch (IOException e) {
		e.printStackTrace(); // TODO logger
	    }
	    return;
	}
	
	if (fileConfig.get("ranks") != null) {
	    
	    for (Entry<String, Object> entry : fileConfig.getConfigurationSection("ranks").getValues(false).entrySet()) {
		Rank rank = new Rank(entry.getKey(), fileConfig.getStringList("ranks." + entry.getKey() + ".commands"));
		System.out.println(rank.toString());
		ranks.add(rank);
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
	    // TODO ask if rank name is case sensitive
	    if (rank.getName().equals(name)) {
		return rank;
	    }
	}
	return null;
    }
    
    public static void execute(Rank rank, Player player) {
	
	if (rank == null || player == null) {
	    // TODO maybe throw exception
	    return;
	}
	
	for (String command : rank.getCommandList()) {
	    RankCommands.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", player.getName()));
	}
	
    }
}
