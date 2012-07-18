package com.github.thebiologist13;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShearListener implements Listener {
	
	private NeverBreak plugin = null;
	
	public ShearListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerShear(PlayerShearEntityEvent ev) {
		//Player
		Player p = ev.getPlayer();
		
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p);
	}
}
