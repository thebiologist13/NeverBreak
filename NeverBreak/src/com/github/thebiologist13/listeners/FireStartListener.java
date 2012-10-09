package com.github.thebiologist13.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.NeverBreak;

public class FireStartListener implements Listener {

	private NeverBreak plugin;
	
	public FireStartListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFireStart(BlockIgniteEvent ev) {
		
		if(!ev.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
			return;
		}
		
		//Player
		Player p = ev.getPlayer();
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p, true);
	}
}
