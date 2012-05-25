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
	
	Logger log = Logger.getLogger("Minecraft");

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
				p.sendMessage(ChatColor.GREEN + "NeverBreak mode toggled to " + String.valueOf(mode.get(p)) + "!");
			}
			return true;
		}
		return false;
	}
}
