package me.Aubli.RankCommands.Rank;

import java.util.List;


public class Rank {
    
    private String name;
    
    private List<String> commandList;
    
    public Rank(String name, List<String> commandList) {
	this.name = name;
	this.commandList = commandList;
    }
    
    public String getName() {
	return this.name;
    }
    
    public List<String> getCommandList() {
	return this.commandList;
    }
}
