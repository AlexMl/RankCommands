package me.Aubli.VantiUtilities;

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
	
    }
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
	
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
