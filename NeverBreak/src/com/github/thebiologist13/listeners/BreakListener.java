package com.github.thebiologist13.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.NeverBreak;

public class BreakListener implements Listener{
	
	private NeverBreak plugin = null;
	
	public BreakListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		//Player Variable
		Player p = ev.getPlayer();
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p, true);
	}
}
