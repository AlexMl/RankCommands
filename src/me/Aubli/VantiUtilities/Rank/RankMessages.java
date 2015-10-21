package me.Aubli.VantiUtilities.Rank;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import me.Aubli.VantiUtilities.VantiUtilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class RankMessages {
    
    public enum RankMessage {
	
	rank_changed("Your rank changed to %s!"),
	config_reloaded("Reloaded the plugin configuration!");
	
	private String message;
	
	private RankMessage(String message) {
	    this.message = message;
	}
	
	public String getMessage() {
	    return this.message;
	}
	
	public String getFormatedMessage(Object... args) {
	    return String.format(getMessage(), args);
	}
    }
    
    public RankMessages() {
	Bukkit.getPluginManager().registerEvents(new JoinListener(), VantiUtilities.getInstance());
    }
    
    public static void sendMessage(Player player, RankMessage message) {
	MessageDelivery.sendMessage(player, message.getMessage());
    }
    
    public static void sendMessage(Player player, RankMessage message, Object... args) {
	MessageDelivery.sendMessage(player, message.getFormatedMessage(args));
    }
    
    public static void pushMessages() {
	MessageDelivery.pushMessages();
    }
    
    private static class MessageDelivery {
	
	private static Map<UUID, String> messageMap = new TreeMap<UUID, String>();
	
	public static void sendMessage(Player player, String message) {
	    messageMap.put(player.getUniqueId(), message);
	    pushMessages();
	}
	
	private static void pushMessages() {
	    
	    Map<UUID, String> queuedMessages = new TreeMap<UUID, String>();
	    
	    for (Entry<UUID, String> entry : messageMap.entrySet()) {
		if (Bukkit.getOfflinePlayer(entry.getKey()).isOnline()) {
		    Bukkit.getPlayer(entry.getKey()).sendMessage(entry.getValue());
		} else {
		    queuedMessages.put(entry.getKey(), entry.getValue());
		}
	    }
	    
	    messageMap = queuedMessages;
	}
    }
    
    private class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
	    MessageDelivery.pushMessages();
	}
    }
}
