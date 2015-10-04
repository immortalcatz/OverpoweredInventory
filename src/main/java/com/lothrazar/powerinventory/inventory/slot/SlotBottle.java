package com.lothrazar.powerinventory.inventory.slot;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBottle extends Slot
{
	public static String background = "textures/items/empty_bottle.png";
	public int slotIndex;//overrides the private internal one
	
	public SlotBottle(IInventory inventoryIn, int index, int xPosition,int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
 
		slotIndex = index;
		//this.setBackgroundName(background);//doesnt actually work
	}
	
	@Override
	public int getSlotIndex()
    {
        return slotIndex;
    }
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack != null && 
				(stack.getItem() == Items.glass_bottle || 
				 stack.getItem() == Items.experience_bottle ));
    }
}
