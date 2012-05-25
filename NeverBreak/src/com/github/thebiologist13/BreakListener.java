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
		//Player Variable
		Player p = ev.getPlayer();
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		//Items that NeverBreak can be used with
		List<?> items = config.getList("items");
		//Loop for all items from config
		for(Object o : items) {
			//Make sure that it is specifying data IDs 
			if(o instanceof Integer) {
				//If item in hand matches one from config
				if(stack.getTypeId() == (Integer) o) {
					//If a mode has been set for the player
					if(ToggleCommand.mode.containsKey(p)) {
						//If that mode is true
						if(ToggleCommand.mode.get(p) == true) {
							//If item is unused, make it REALLY unused. i.e. if the durability is 0 or lower and not -2048, set it to -2048
							if(stack.getDurability() <= (short) 0 && stack.getDurability() != (short) -2048) {
								stack.setDurability((short) -2048);
							//Reset the durability if used
							} else {
								stack.setDurability((short) (stack.getDurability() - 1));
							}
						//If that mode is false, proceed as normal 
						} else {
							//Unless it was set to REALLY unused, then make the durability 0 again
							if(stack.getDurability() == -2047) {
								stack.setDurability((short) 0);
							}
						}
					} 
				}
			//Continue if not a data ID
			} else {
				continue;
			}
		}
	}
}
