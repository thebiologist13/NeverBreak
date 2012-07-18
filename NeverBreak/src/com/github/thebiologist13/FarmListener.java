package com.github.thebiologist13;

import net.minecraft.server.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FarmListener implements Listener {
	
	private NeverBreak plugin = null;
	
	public FarmListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerFarm(PlayerInteractEvent ev) {
		//Player
		Player p = ev.getPlayer();
		
		if(!(ev.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		
		if(!(ev.getClickedBlock().getType().equals(Material.GRASS)) ||
				!(ev.getMaterial().equals(Material.EARTH))) {
			return;
		}
		
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		
		plugin.resetDurability(stack, p);
	}
}
