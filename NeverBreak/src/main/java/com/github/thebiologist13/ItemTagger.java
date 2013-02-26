package com.github.thebiologist13;

import java.lang.reflect.Field;

import net.minecraft.server.v1_4_R1.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_4_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemTagger {

	private final NeverBreak PLUGIN;

	public ItemTagger(NeverBreak plugin) {
		this.PLUGIN = plugin;
	}

	public void recalculateDurability(ItemStack stack) {
		if(stack == null || stack.getType().equals(Material.AIR))
			return;

		int n; //NeverBreak usage
		int c; //Configured usage
		int m; //Vanilla max
		int x; //Calculated durability
		int p; //Previous durability
		int y; //Tool Durability

		c = PLUGIN.getConfiguredDurability(stack);
		m = stack.getType().getMaxDurability();
		if(c == m)
			return;

		if(c <= 0)
			return;
		
		try {
			net.minecraft.server.v1_4_R1.ItemStack handle = getHandle(stack);

			if(!(hasPreviousTag(stack) || hasUsageTag(stack))) { //Not initialized for NeverBreak yet.
				handle = applyTags(stack, 0, 0);
				handle.setData(0);
				return;
			} else {
				p = handle.tag.getInt("NeverBreakPrevious");
				y = handle.getData();
				n = handle.tag.getInt("NeverBreakUsage");
			}
			
			if(p == y)
				return;
			
			if(p < y) {
				x = Math.round(((n*m)/c) + 2*(m/c));
				handle = applyTags(stack, x, n + 1);
				handle.setData(x);
			} else if((y - p) < 0) {
				n = Math.round((c*y)/m);
				x = Math.round(((n*m)/c) + 2*(m/c));
				handle = applyTags(stack, x, n);
				handle.setData(x);
			}
			
			setHandle(stack, handle);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void cancelDurabilityLoss(ItemStack stack) {
		if(stack == null || stack.getType().equals(Material.AIR))
			return;

		int p;
		int n;
		
		try {
			net.minecraft.server.v1_4_R1.ItemStack handle = getHandle(stack);

			if(!(hasPreviousTag(stack) || hasUsageTag(stack))) { //Not initialized for NeverBreak yet.
				handle = applyTags(stack, 0, 0);
				handle.setData(0);
				return;
			} else {
				p = handle.tag.getInt("NeverBreakPrevious");
				n = handle.tag.getInt("NeverBreakUsage");
			}
			
			handle = applyTags(stack, p, n);
			handle.setData(p);
			
			setHandle(stack, handle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDurability(ItemStack stack, int durability) {
		if(stack == null || stack.getType().equals(Material.AIR))
			return;

		try {
			net.minecraft.server.v1_4_R1.ItemStack handle = getHandle(stack);
			int x;
			int m;
			int c;
			c = PLUGIN.getConfiguredDurability(stack);
			m = stack.getType().getMaxDurability();
			if(c == m)
				return;

			if(c <= 0)
				return;
			x = Math.round((durability*m)/c);
			handle = applyTags(stack, x, durability);
			handle.setData(x);
			setHandle(stack, handle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private net.minecraft.server.v1_4_R1.ItemStack getHandle(ItemStack stack) 
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CraftItemStack cStack = ((CraftItemStack) stack);
		Field f = CraftItemStack.class.getDeclaredField("handle");
		f.setAccessible(true);
		return (net.minecraft.server.v1_4_R1.ItemStack) f.get(cStack);
	}

	private void setHandle(ItemStack stack, net.minecraft.server.v1_4_R1.ItemStack handle) 
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CraftItemStack cStack = ((CraftItemStack) stack);
		Field f = CraftItemStack.class.getDeclaredField("handle");
		f.setAccessible(true);
		f.set(cStack, handle);
	}

	private net.minecraft.server.v1_4_R1.ItemStack applyTags(ItemStack stack, int previous, int usage) 
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		net.minecraft.server.v1_4_R1.ItemStack nms = getHandle(stack);
		makeTag(nms);
		nms.tag.setInt("NeverBreakPrevious", previous);
		nms.tag.setInt("NeverBreakUsage", usage);
		return nms;
	}

	private boolean hasPreviousTag(ItemStack stack) 
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		net.minecraft.server.v1_4_R1.ItemStack nms = getHandle(stack);
		makeTag(nms);
		return nms.tag.hasKey("NeverBreakPrevious");
	}

	private boolean hasUsageTag(ItemStack stack) 
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		net.minecraft.server.v1_4_R1.ItemStack nms = getHandle(stack);
		makeTag(nms);
		return nms.tag.hasKey("NeverBreakUsage");
	}

	private void makeTag(net.minecraft.server.v1_4_R1.ItemStack stack) {
		if(!stack.hasTag()) {
			stack.tag = new NBTTagCompound();
		}
	}

}
