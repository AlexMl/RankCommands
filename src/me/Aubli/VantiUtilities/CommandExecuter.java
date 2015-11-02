package me.Aubli.VantiUtilities;

import java.util.logging.Level;

import me.Aubli.VantiUtilities.Rank.Rank;
import me.Aubli.VantiUtilities.Rank.RankManager;
import me.Aubli.VantiUtilities.Rank.RankManager.EconomyException;
import me.Aubli.VantiUtilities.Rank.RankMessages;
import me.Aubli.VantiUtilities.Rank.RankMessages.RankMessage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CommandExecuter implements CommandExecutor {
    
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	if (cmd.getName().equalsIgnoreCase("rank")) {
	    if (VantiPermission.hasPermission(sender, VantiPermission.command_rank_change)) {
		
		if (args.length == 2) {
		    OfflinePlayer argPlayer = Bukkit.getOfflinePlayer(args[0]);
		    Rank rank = RankManager.getRank(args[1]);
		    
		    if (rank != null) {
			if (rank.getRankPermission() == null || rank.getRankPermission().isEmpty() || sender.hasPermission(rank.getRankPermission())) {
			    try {
				RankManager.execute(rank, argPlayer);
			    } catch (NullPointerException e) {
				VantiUtilities.getPluginLogger().log(getClass(), Level.WARNING, "Error in executing rank! " + e.getMessage(), true, false, e);
			    } catch (EconomyException e) {
				RankMessages.sendInstantMessage(sender, RankMessage.not_enough_money, argPlayer.getName(), rank.getName());
				VantiUtilities.getPluginLogger().log(getClass(), Level.INFO, "Economy answer: " + e.getMessage(), true, true, e);
				return true;
			    }
			    RankMessages.sendInstantMessage(sender, RankMessage.rank_changed, argPlayer.getName(), rank.getName());
			    return true;
			} else {
			    RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
			    return true;
			}
		    } else {
			RankMessages.sendInstantMessage(sender, RankMessage.rank_not_found, args[1]);
			return true;
		    }
		}
	    } else {
		RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
		return true;
	    }
	    return false;
	}
	
	if (cmd.getName().equalsIgnoreCase("tag")) {
	    if (VantiPermission.hasPermission(sender, VantiPermission.command_tag)) {
		if (args.length == 3) {
		    OfflinePlayer argPlayer = Bukkit.getOfflinePlayer(args[0]);
		    Rank rank = RankManager.getRank(args[1]);
		    
		    if (rank != null) {
			if (rank.getRankPermission() == null || rank.getRankPermission().isEmpty() || sender.hasPermission(rank.getRankPermission())) {
			    try {
				RankManager.execute(rank, argPlayer, args[2]);
			    } catch (NullPointerException e) {
				VantiUtilities.getPluginLogger().log(getClass(), Level.WARNING, "Error in executing rank! " + e.getMessage(), true, false, e);
			    } catch (EconomyException e) {
				RankMessages.sendInstantMessage(sender, RankMessage.not_enough_money, argPlayer.getName(), rank.getName());
				VantiUtilities.getPluginLogger().log(getClass(), Level.INFO, "Economy answer: " + e.getMessage(), true, true, e);
				return true;
			    }
			    RankMessages.sendInstantMessage(sender, RankMessage.rank_changed, argPlayer.getName(), rank.getName());
			    return true;
			}
		    } else {
			RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
			return true;
		    }
		}
	    } else {
		RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
		return true;
	    }
	    return false;
	}
	
	if (cmd.getName().equalsIgnoreCase("nightvision")) {
	    if (VantiPermission.hasPermission(sender, VantiPermission.player_nightvision)) {
		Player player = null;
		
		if (args.length == 0) {
		    if (sender instanceof Player) {
			player = (Player) sender;
		    }
		} else if (args.length == 1) {
		    player = Bukkit.getPlayer(args[0]);
		}
		
		if (player != null) {
		    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			RankMessages.sendInstantMessage(player, RankMessage.nightvision_disabled);
		    } else {
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1), true);
			RankMessages.sendInstantMessage(player, RankMessage.nightvision_enabled);
		    }
		    return true;
		}
		
		return false;
	    } else {
		RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
		return true;
	    }
	}
	
	if (cmd.getName().equalsIgnoreCase("vanti")) {
	    
	    if (args.length == 1) {
		if (VantiPermission.hasPermission(sender, VantiPermission.command_config_reload)) {
		    if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			VantiUtilities.getInstance().reloadPluginConfig();
			RankMessages.sendInstantMessage(sender, RankMessage.config_reloaded);;
			return true;
		    }
		} else {
		    RankMessages.sendInstantMessage(sender, RankMessage.no_permission);
		    return true;
		}
	    }
	    return false;
	}
	return false;
    }
}
