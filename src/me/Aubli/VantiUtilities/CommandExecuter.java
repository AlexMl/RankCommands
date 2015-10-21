package me.Aubli.VantiUtilities;

import me.Aubli.VantiUtilities.Rank.Rank;
import me.Aubli.VantiUtilities.Rank.RankManager;
import me.Aubli.VantiUtilities.Rank.RankMessages;
import me.Aubli.VantiUtilities.Rank.RankMessages.RankMessage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CommandExecuter implements CommandExecutor {
    
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	if (cmd.getName().equalsIgnoreCase("rank")) {
	    
	    if (args.length == 2) {
		OfflinePlayer argPlayer = Bukkit.getOfflinePlayer(args[0]);
		Rank rank = RankManager.getRank(args[1]);
		
		if (rank != null) {
		    if (rank.getRankPermission().isEmpty() || sender.hasPermission(rank.getRankPermission())) {
			RankManager.execute(rank, argPlayer);
			RankMessages.sendMessage(sender, RankMessage.rank_changed, argPlayer.getName(), rank.getName());
			return true;
		    } else {
			RankMessages.sendMessage(sender, RankMessage.no_permission);
			return true;
		    }
		} else {
		    RankMessages.sendMessage(sender, RankMessage.rank_not_found, args[1]);
		    return true;
		}
	    }
	    return false;
	}
	
	if (cmd.getName().equalsIgnoreCase("vanti")) {
	    
	    if (args.length == 1) {
		if (sender.hasPermission("")) {// TODO permission
		    if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			VantiUtilities.getInstance().reloadPluginConfig();
			RankMessages.sendMessage(sender, RankMessage.config_reloaded);;
			return true;
		    }
		} else {
		    RankMessages.sendMessage(sender, RankMessage.no_permission);
		    return true;
		}
	    }
	    
	    return true;
	}
	
	return false;
    }
}
