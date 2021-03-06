package com.github.thebiologist13;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DurabilityCommand implements CommandExecutor {

	//Plugin variable
	private NeverBreak plugin;
	
	//Assigning plugin variable
	public DurabilityCommand(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Player variable
		Player p = null; 
		
		//If a player, not the console
		if(arg0 instanceof Player) {
			p = (Player) arg0;
		}
		
		//If the command sender is a player and the command is "setdurability"
		if(p != null && arg1.getName().equalsIgnoreCase("setdurability")) {
			
			//If the player has permission
			if(p.hasPermission("neverbreak.setdurability")) {
				
				if(arg3.length == 1) {
					//Boolean value for if they are holding a valid item. i.e. one specified in configuration
					boolean holdingValidItem = false;
					
					//Finds if they are holding a valid item
					if(plugin.getItems().containsKey(p.getItemInHand().getTypeId())) {
						holdingValidItem = true;
					}
					
					int durability = 0;
					
					try {
						durability = Integer.parseInt(arg3[0]);
					} catch(NumberFormatException e) {
						plugin.sendMessage(p, ChatColor.RED + "You must use a number for durability.");
						return true;
					}
					
					
					if(holdingValidItem) {
							
						ItemStack stack = p.getItemInHand();
						ItemTagger tagger = new ItemTagger(plugin);
						plugin.sendMessage(p, ChatColor.GREEN + "The durability of your tool has been set to " +
								ChatColor.GOLD + arg3[0] + ChatColor.GREEN + "!");
						tagger.setDurability(stack, durability);
						
					} else {
						
						plugin.sendMessage(p, ChatColor.RED + "You are not holding an item that this server is configured " +
								"to allow durability setting for.");
						
					}
					
					return true;
				} else if(arg3.length == 2) {
					if(arg3[1].equalsIgnoreCase("absolute") || arg3[1].equalsIgnoreCase("normal")) {
						//Boolean value for if they are holding a valid item. i.e. one specified in configuration
						boolean holdingValidItem = false;
						
						//Finds if they are holding a valid item
						if(plugin.getItems().containsKey(p.getItemInHand().getTypeId())) {
							holdingValidItem = true;
						}
						
						int durability = 0;
						
						try {
							durability = Short.parseShort(arg3[0]);
						} catch(NumberFormatException e) {
							plugin.sendMessage(p, ChatColor.RED + "You must use a number for durability.");
							return true;
						}
						
						
						if(holdingValidItem) {
								
							ItemStack stack = p.getItemInHand();
							plugin.sendMessage(p, ChatColor.GREEN + "The absolute durability of your tool has been set to " + 
									ChatColor.GOLD + arg3[0] + ChatColor.GREEN + "!");
							stack.setDurability((short) durability);
							
						} else {
							
							plugin.sendMessage(p, ChatColor.RED + "You are not holding an item that this server is " +
									"configured to allow durability setting for.");
							
						}
					} else {
						plugin.sendMessage(p, ChatColor.RED + "You must specify \"absolute\" after the durability for direct setting.");
					}
					return true;
				} else {
					plugin.sendMessage(p, ChatColor.RED + "That is not the correct amount of arguments.");
					return true;
				}
				
			}
			
		}
		
		if(p == null && arg1.getName().equalsIgnoreCase("setdurability")) {
			
			plugin.sendMessage(arg0, "The " + arg1.getName() + " command cannot be used from the console.");
			return true;
			
		}
		
		return false;
	}

}
