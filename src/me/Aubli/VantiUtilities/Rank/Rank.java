package me.Aubli.VantiUtilities.Rank;

import java.util.List;


public class Rank {
    
    private String name;
    private String permission;
    private List<String> commandList;
    private String message;
    
    public Rank(String name, String permission, List<String> commandList, String promotionMessage) {
	this.name = name;
	this.permission = permission;
	this.commandList = commandList;
	this.message = promotionMessage;
    }
    
    public String getName() {
	return this.name;
    }
    
    public String getRankPermission() {
	return this.permission;
    }
    
    public List<String> getCommandList() {
	return this.commandList;
    }
    
    public String getMessage() {
	return this.message;
    }
    
    @Override
    public String toString() {
	return getClass().getSimpleName() + "[Name:" + getName() + ", Permission:" + getRankPermission() + ", Commands:" + getCommandList().toString() + "]";
    }
}
