package com.github.thebiologist13;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakListener implements Listener{
	private FileConfiguration config;
	Logger log = Logger.getLogger("Minecraft");
	public BreakListener(NeverBreak neverBreak) {
		config = neverBreak.getCustomConfig();
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		Player p = ev.getPlayer();
		ItemStack stack = p.getItemInHand();
		List<?> items = config.getList("items");
		for(Object o : items) {
			if(o instanceof Integer) {
				if(stack.getTypeId() == (Integer) o) {
					if(ToggleCommand.mode.containsKey(p)) {
						if(ToggleCommand.mode.get(p) == true) {
							if(stack.getDurability() <= (short) 0 && stack.getDurability() != (short) -2048) {
								stack.setDurability((short) -2048);
							} else {
								stack.setDurability((short) (stack.getDurability() - 1));
							}
						} else {
							if(stack.getDurability() == -2047) {
								stack.setDurability((short) 0);
							}
						}
					}
				}
			} else {
				continue;
			}
		}
	}
}
