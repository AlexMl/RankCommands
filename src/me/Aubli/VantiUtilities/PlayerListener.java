package me.Aubli.VantiUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.Aubli.VantiUtilities.Regenerator.RegenerationType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PlayerListener implements Listener {
    
    private List<UUID> nightVisionEnabled = new ArrayList<UUID>();
    
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
	
	if (event.getEntity().hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
	    if (VantiPermission.hasPermission(event.getEntity(), VantiPermission.player_nightvision)) {
		this.nightVisionEnabled.add(event.getEntity().getUniqueId());
	    }
	}
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
	if (this.nightVisionEnabled.contains(event.getPlayer().getUniqueId())) {
	    if (VantiPermission.hasPermission(event.getPlayer(), VantiPermission.player_nightvision)) {
		final Player eventPlayer = event.getPlayer();
		
		Bukkit.getScheduler().runTask(VantiUtilities.getInstance(), new Runnable() {
		    
		    @Override
		    public void run() {
			PlayerListener.this.nightVisionEnabled.remove(eventPlayer.getPlayer().getUniqueId());
			eventPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1), true);
		    }
		});
	    }
	}
    }
}
