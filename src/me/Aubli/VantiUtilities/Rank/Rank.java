package me.Aubli.VantiUtilities.Rank;

import java.util.List;


public class Rank {
    
    private String name;
    private String permission;
    private List<String> commandList;
    private String message;
    private double price;
    
    public Rank(String name, String permission, List<String> commandList, String promotionMessage, double price) {
	this.name = name;
	this.permission = permission;
	this.commandList = commandList;
	this.message = promotionMessage;
	this.price = price;
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
    
    public double getPrice() {
	return this.price;
    }
    
    @Override
    public String toString() {
	return getClass().getSimpleName() + "[Name:" + getName() + ", Permission:" + getRankPermission() + ", Price:" + getPrice() + ", Commands:" + getCommandList().toString() + "]";
    }
}
