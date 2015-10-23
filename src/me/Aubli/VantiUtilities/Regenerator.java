package me.Aubli.VantiUtilities;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Regenerator extends BukkitRunnable {
    
    public enum RegenerationType {
	HEALTH(10, 10),
	FOOD(10, 10);
	
	private long regenInterval;
	private int startValue;
	
	private RegenerationType(long interval, int start) {
	    this.regenInterval = interval;
	    this.startValue = start;
	}
	
	public long getRegenerationInterval() {
	    return this.regenInterval;
	}
	
	public int getStartValue() {
	    return this.startValue;
	}
    }
    
    private RegenerationType type;
    private Player player;
    
    public Regenerator(Player player, RegenerationType type) {
	this.player = player;
	this.type = type;
	
	this.runTaskTimer(VantiUtilities.getInstance(), type.getRegenerationInterval(), type.getRegenerationInterval());
    }
    
    @Override
    public void run() {
	
	// System.out.println("Regeneration of " + this.type.name());
	
	switch (this.type) {
	    case HEALTH:
		// System.out.println("H:" + this.player.getHealth());
		
		if (this.player.getHealth() <= 0) {
		    this.cancel();
		    return;
		}
		
		if (Math.ceil(this.player.getHealth()) < this.player.getMaxHealth()) {
		    this.player.setHealth(this.player.getHealth() + 1.0);
		    return;
		}
		this.cancel();
		break;
	    
	    case FOOD:
		// System.out.println("F:" + this.player.getFoodLevel());
		if (this.player.getFoodLevel() <= 20) {
		    this.player.setFoodLevel(this.player.getFoodLevel() + 1);
		} else {
		    this.cancel();
		}
		break;
	}
    }
}
