package me.Aubli.VantiUtilities;

import org.bukkit.command.CommandSender;


public enum VantiPermission {
    
    config_reload("vanti.reload"),
    player_food_regen("vanti.regen.food"),
    player_healt_regen("vanti.regen.health"),
    player_keep_inventory("vanti.keepinventory"),
    player_tool_repair("vanti.unbreakabletool");
    
    private String permission;
    
    private VantiPermission(String permission) {
	this.permission = permission;
    }
    
    public String getPermission() {
	return this.permission;
    }
    
    public static boolean hasPermission(CommandSender sender, VantiPermission permission) {
	return sender.hasPermission(permission.getPermission());
    }
    
}
