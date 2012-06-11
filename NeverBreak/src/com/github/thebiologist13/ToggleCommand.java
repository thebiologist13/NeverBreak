package com.github.thebiologist13;

import java.util.HashMap;
import java.util.logging.Logger;

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
	public static HashMap<Player, Boolean> mode = new HashMap<Player, Boolean>();
	
	private NeverBreak plugin;
	
	Logger log = Logger.getLogger("Minecraft");

	public ToggleCommand(NeverBreak neverBreak) {
		this.plugin = neverBreak;
	}

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Player
		Player p = null;
		
		if(arg0 instanceof Player) {
			p = (Player) arg0;
		}
		
		//If command is /neverbreak
		if(p != null && arg3.length == 0 && arg1.getName().equalsIgnoreCase("neverbreak")) {
			//Has perm?
			if(p.hasPermission("neverbreak.toggle")) {
				//If contains player key. (Always true v1.1 and later)
				if(mode.containsKey(p)) {
					//If true, set false
					if(mode.get(p) == true) {
						mode.put(p, false);
					//If false, set true
					} else {
						mode.put(p, true);
					}
				} else {
					mode.put(p, true);
				}
				//Send message
				p.sendMessage(ChatColor.GREEN + "NeverBreak mode toggled to " + ChatColor.GOLD + String.valueOf(mode.get(p)) + ChatColor.GREEN + "!");
			}
			return true;
		}
		
		//If command is /neverbreak with an argument for another player
		if(p != null && arg3.length == 1 && arg1.getName().equalsIgnoreCase("neverbreak")) {
			//Has the other player toggle perm?
			if(p.hasPermission("neverbreak.toggle.others")) {
				//Get the other player by name
				Player other = plugin.getServer().getPlayer(arg3[0]);
				//If the player is not online
				if(other == null) {
					//Send error message
					p.sendMessage(ChatColor.RED + "Player " + arg3[0] + " is not currently online!");
				} else {
					//If contains player key. (Always true v1.1 and later)
					if(mode.containsKey(other)) {
						//If true, set false
						if(mode.get(other) == true) {
							mode.put(other, false);
						//If false, set true
						} else {
							mode.put(other, true);
						}
					} else {
						mode.put(other, true);
					}
					//Send message to player who issued command
					p.sendMessage(ChatColor.GREEN + "NeverBreak mode toggled to " + ChatColor.GOLD + String.valueOf(mode.get(other)) + ChatColor.GREEN + " for player " + other.getName() + "!");
					//Send message to player who's mode was changed
					other.sendMessage(ChatColor.GREEN + "Your NeverBreak mode has been toggled to " + ChatColor.GOLD + String.valueOf(mode.get(other)) + ChatColor.GREEN + " by " + p.getName() + ".");
				}
				return true;
			}
		}
		
		if(p == null && arg1.getName().equalsIgnoreCase("neverbreak")) {
			plugin.log.info(ChatColor.RED + "The " + arg1.getName() + " cannot be used from the console.");
			return true;
		}
		
		return false;
	}
}
