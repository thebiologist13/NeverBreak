package com.github.thebiologist13;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements CommandExecutor{
	
	public static HashMap<Player, Boolean> mode = new HashMap<Player, Boolean>();
	
	Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = null;
		
		if(arg0 instanceof Player) {
			p = (Player) arg0;
		}
		
		if(p != null && arg3.length == 0 && arg1.getName().equalsIgnoreCase("neverbreak")) {
			if(p.hasPermission("neverbreak.toggle")) {
				if(mode.containsKey(p)) {
					if(mode.get(p) == true) {
						mode.put(p, false);
					} else {
						mode.put(p, true);
					}
				} else {
					mode.put(p, true);
				}
				p.sendMessage(ChatColor.GREEN + "NeverBreak mode toggled to " + String.valueOf(mode.get(p)) + "!");
			}
			return true;
		}
		return false;
	}
}
