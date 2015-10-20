package me.Aubli.VantiUtilities;

import me.Aubli.VantiUtilities.Rank.Rank;
import me.Aubli.VantiUtilities.Rank.RankManager;
import me.Aubli.VantiUtilities.Rank.RankMessages;
import me.Aubli.VantiUtilities.Rank.RankMessages.RankMessage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandExecuter implements CommandExecutor {
    
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	if (cmd.getName().equalsIgnoreCase("rank")) {
	    // TODO permission check
	    if (args.length == 2) {
		Player argPlayer = Bukkit.getPlayer(args[0]);
		
		// TODO maybe handle offlineplayers as well
		if (argPlayer != null && argPlayer.isOnline()) {
		    Rank rank = RankManager.getRank(args[1]);
		    RankManager.execute(rank, argPlayer);
		    
		    System.out.println(argPlayer.getName() + " changed rank to " + rank);
		    argPlayer.sendMessage(rank.getMessage());
		    RankMessages.sendMessage(argPlayer, RankMessage.rank_changed, rank.getName());
		    return true;
		} else {
		    // TODO message
		}
	    }
	    
	}
	
	return false;
    }
}
