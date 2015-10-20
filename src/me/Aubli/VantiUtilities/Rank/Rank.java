package me.Aubli.VantiUtilities.Rank;

import java.util.List;


public class Rank {
    
    private String name;
    private List<String> commandList;
    private String message;
    
    public Rank(String name, List<String> commandList, String promotionMessage) {
	this.name = name;
	this.commandList = commandList;
	this.message = promotionMessage;
    }
    
    public String getName() {
	return this.name;
    }
    
    public List<String> getCommandList() {
	return this.commandList;
    }
    
    public String getMessage() {
	return this.message;
    }
    
    @Override
    public String toString() {
	return getClass().getSimpleName() + "[Name:" + getName() + ", Commands:" + getCommandList().toString() + "]";
    }
}
