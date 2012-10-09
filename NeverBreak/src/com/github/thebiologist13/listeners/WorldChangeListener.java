package com.github.thebiologist13.listeners;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.github.thebiologist13.NeverBreak;
import com.github.thebiologist13.ToggleCommand;

public class WorldChangeListener implements Listener {
	
	private NeverBreak plugin;
	private HashMap<Player, Boolean> mode = ToggleCommand.mode;
	private Player p = null;
	
	public WorldChangeListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent ev) {
		p = ev.getPlayer();
		if(!plugin.getCustomConfig().getBoolean("globalNeverBreak", true)) {
			mode.put(p, plugin.getCustomConfig().getBoolean("autoNeverBreak", false));
			p.sendMessage(ChatColor.GREEN + "Changing NeverBreak mode to " + ChatColor.GOLD + String.valueOf(mode.get(p)) + ChatColor.GREEN + " for this world.");
		} 
	}
}
