package com.github.thebiologist13.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.github.thebiologist13.NeverBreak;

public class BlockDamageListener implements Listener {

	private NeverBreak plugin = null;
	
	public BlockDamageListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent ev) {
		//Player Variable
		Player p = ev.getPlayer();
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p, false);
	}
}
