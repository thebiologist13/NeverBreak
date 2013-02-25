package com.github.thebiologist13.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.thebiologist13.NeverBreak;

public class LogoutListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogout(PlayerQuitEvent ev) {
		if(NeverBreak.mode.containsKey(ev.getPlayer()))
			NeverBreak.mode.remove(ev.getPlayer());
	}
	
}
