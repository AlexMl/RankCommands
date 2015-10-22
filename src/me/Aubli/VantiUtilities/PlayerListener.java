package me.Aubli.VantiUtilities;

import me.Aubli.VantiUtilities.Regenerator.RegenerationType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerListener implements Listener {
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player eventPlayer = (Player) event.getEntity();
	    
	    if (VantiPermission.hasPermission(eventPlayer, VantiPermission.player_food_regen)) {
		if (eventPlayer.getFoodLevel() <= RegenerationType.FOOD.getStartValue()) {
		    new Regenerator(eventPlayer, RegenerationType.FOOD);
		    return;
		}
	    }
	}
    }
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player eventPlayer = (Player) event.getEntity();
	    
	    if (VantiPermission.hasPermission(eventPlayer, VantiPermission.player_healt_regen)) {
		// System.out.println("Health: " + eventPlayer.getHealth());
		// System.out.println("Damage: " + event.getDamage());
		// System.out.println("FDamage: " + event.getFinalDamage());
		
		double playerHealth = eventPlayer.getHealth() - event.getFinalDamage();
		// System.out.println("calcHealth: " + playerHealth);
		
		if (playerHealth > 0 && playerHealth <= RegenerationType.HEALTH.getStartValue()) {
		    new Regenerator(eventPlayer, RegenerationType.HEALTH);
		    return;
		}
	    }
	}
    }
    
    @EventHandler
    public void onToolInteraction(PlayerInteractEvent event) {
	if (VantiPermission.hasPermission(event.getPlayer(), VantiPermission.player_tool_repair)) {
	    event.getItem().setDurability((short) 0);
	}
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
	if (VantiPermission.hasPermission(event.getEntity(), VantiPermission.player_keep_inventory)) {
	    event.setKeepInventory(true);
	} else {
	    event.setKeepInventory(false);
	}
    }
}
