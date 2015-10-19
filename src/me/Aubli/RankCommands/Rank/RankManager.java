package me.Aubli.RankCommands.Rank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.Aubli.RankCommands.RankCommands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


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
		
		fileConfig.set("ranks", null);
		fileConfig.save(rankFile);
	    } catch (IOException e) {
		e.printStackTrace(); // TODO logger
	    }
	    return;
	}
	
	// TODO load actuall content
	
    }
    
    private static RankManager getManager() {
	return instance;
    }
    
}
