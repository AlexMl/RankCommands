package me.Aubli.VantiUtilities;

import me.Aubli.VantiUtilities.Regenerator.RegenerationType;
import me.Aubli.VantiUtilities.Rank.RankMessages;
import me.Aubli.VantiUtilities.Rank.RankMessages.RankMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class PlayerListener implements Listener {
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player eventPlayer = (Player) event.getEntity();
	    
	    if (VantiPermission.hasPermission(eventPlayer, VantiPermission.player_healt_regen)) {
		if (eventPlayer.getFoodLevel() <= RegenerationType.FOOD.getStartValue()) {
		    new Regenerator(eventPlayer, RegenerationType.FOOD);
		    return;
		}
	    } else {
		RankMessages.sendInstantMessage(eventPlayer, RankMessage.no_permission);
		return;
	    }
	}
    }
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player eventPlayer = (Player) event.getEntity();
	    
	    System.out.println("Health: " + eventPlayer.getHealth());
	    System.out.println("Damage: " + event.getDamage());
	    System.out.println("FDamage: " + event.getFinalDamage());
	    
	    double playerHealth = eventPlayer.getHealth() - event.getFinalDamage();
	    System.out.println("calcHealth: " + playerHealth);
	    
	    if (playerHealth > 0 && playerHealth <= RegenerationType.HEALTH.getStartValue()) {
		new Regenerator(eventPlayer, RegenerationType.HEALTH);
		return;
	    }
	}
    }
    
    @EventHandler
    public void onToolBreak(PlayerItemBreakEvent event) {
	// dont break
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
	
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
	
    }
    
}
