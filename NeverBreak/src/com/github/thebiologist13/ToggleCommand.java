package com.github.thebiologist13;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements CommandExecutor{
	
	/*
	 * VERY IMPORTANT VARIABLE!
	 * 
	 * The mode HashMap stores current mode for all players.
	 */
	//public static HashMap<Player, Boolean> mode = new HashMap<Player, Boolean>();
	
	private NeverBreak plugin;

	public ToggleCommand(NeverBreak neverBreak) {
		this.plugin = neverBreak;
	}

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Player
		Player p = null;
		
		if(arg0 instanceof Player) {
			p = (Player) arg0;
		}

		if(arg1.getName().equalsIgnoreCase("neverbreak")) {

			if(p != null) {

				//If command is /neverbreak
				if(arg3.length == 0) {
					//Has perm?
					if(p.hasPermission("neverbreak.toggle")) {
						plugin.toggleMode(p);
						//Send message
						plugin.sendMessage(arg0, ChatColor.GREEN + "NeverBreak mode toggled to " + ChatColor.GOLD + String.valueOf(plugin.getMode(p)) + ChatColor.GREEN + "!");
					}
					return true;

					//Info command
				} else if(arg3.length == 1 && arg3[0].equalsIgnoreCase("info")) {

					final String[] INFO_MESSAGE = {
							ChatColor.AQUA + "* * * NeverBreak v" + ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.AQUA + " by thebiologist13 * * *",
							ChatColor.AQUA + "",
							ChatColor.AQUA + "NeverBreak on BukkitDev:",
							ChatColor.BLUE + "http://dev.bukkit.org/server-mods/neverbreak/",
							ChatColor.AQUA + "",
							ChatColor.AQUA + "thebiologist13 on BukkitDev:",
							ChatColor.BLUE + "http://dev.bukkit.org/profiles/thebiologist13/",
							ChatColor.AQUA + "",
							ChatColor.AQUA + "* * * * * * * * * * * * * * * *"
					};

					plugin.sendMessage(arg0, INFO_MESSAGE);

					return true;

					//If command is /neverbreak with an argument for another player	
				} else if(arg3.length == 1) {
					
					Player other = plugin.getServer().getPlayer(arg3[0]);
					
					if(p.hasPermission("neverbreak.toggle.others")) {
						//If the player is not online
						if(other == null) {
							//Send error message
							plugin.sendMessage(p, ChatColor.RED + "Player " + arg3[0] + " is not currently online!");
						} else {
							plugin.toggleMode(other);
							//Send message to player who issued command
							plugin.sendMessage(p, ChatColor.GREEN + "NeverBreak mode toggled to " + ChatColor.GOLD + String.valueOf(plugin.getMode(other)) + ChatColor.GREEN + " for player " + other.getName() + "!");
							//Send message to player who's mode was changed
							plugin.sendMessage(other, ChatColor.GREEN + "Your NeverBreak mode has been toggled to " + ChatColor.GOLD + String.valueOf(plugin.getMode(other)) + ChatColor.GREEN + " by " + p.getName() + ".");
						}
						return true;
					}
					
				}

			} else {
				
				if(arg3.length == 1) {
					
					//Get the other player by name
					Player other = plugin.getServer().getPlayer(arg3[0]);
					
					//If the player is not online
					if(other == null) {
						//Send error message
						plugin.sendMessage(arg0, ChatColor.RED + "Player " + arg3[0] + " is not currently online!");
					} else {
						plugin.toggleMode(other);
						//Send message to player who issued command
						plugin.sendMessage(arg0, ChatColor.GREEN + "NeverBreak mode toggled to " + ChatColor.GOLD + String.valueOf(plugin.getMode(other)) + ChatColor.GREEN + " for player " + other.getName() + "!");
						//Send message to player who's mode was changed
						plugin.sendMessage(other, ChatColor.GREEN + "Your NeverBreak mode has been toggled to " + ChatColor.GOLD + String.valueOf(plugin.getMode(other)) + ChatColor.GREEN + " by console.");
					}
					
					return true;
					
				}
				
			}

		}
		
		return false;
	}
	
}
