package com.lothrazar.powerinventory.net;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.GuiHandler;
import com.lothrazar.powerinventory.ModInv;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class OpenInventoryPacket implements IMessage , IMessageHandler<OpenInventoryPacket, IMessage>
{
	public OpenInventoryPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public OpenInventoryPacket(NBTTagCompound ptags)
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

	@Override
	public IMessage onMessage(OpenInventoryPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		
		
		if(message.tags.getInteger("i") == Const.INV_SOLO)
		{ 
			p.openGui(ModInv.instance, GuiHandler.GUI_CUSTOM_INV, p.worldObj, (int) p.posX, (int) p.posY, (int) p.posZ);
		}
		else
		{
			//test isnt needed since button is always hidden when we want it to be
			//if( p.inventory.getStackInSlot(Const.enderChestSlot) != null)
				p.displayGUIChest(p.getInventoryEnderChest());
			//else 
			//	p.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("slot.enderchest")));
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