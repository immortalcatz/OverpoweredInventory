package com.lothrazar.powerinventory.network;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.GuiHandler;
import com.lothrazar.powerinventory.ModInv;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class EnderChestPacket implements IMessage , IMessageHandler<EnderChestPacket, IMessage>
{
	public EnderChestPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public EnderChestPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}

	 boolean altMode = true;//testing my new inventory
	@Override
	public IMessage onMessage(EnderChestPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		
		if(altMode)
			p.openGui(ModInv.instance, GuiHandler.GUI_CUSTOM_INV, p.worldObj, (int) p.posX, (int) p.posY, (int) p.posZ);


		else{		
			if( p.inventory.getStackInSlot(Const.enderChestSlot) != null)
				p.displayGUIChest(p.getInventoryEnderChest());
			else 
				p.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("slot.enderchest")));
		}
		
		
		/*
		int invType = message.tags.getInteger("i");

		switch(invType)
		{
		case Const.INV_ENDER:
			p.displayGUIChest(p.getInventoryEnderChest());
		break;
		case Const.INV_PLAYER:

			//this packet should not have been sent. but keep empty branch so i remember it
			break;
		}*/

		return null;
	}
}