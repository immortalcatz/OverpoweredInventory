package com.lothrazar.powerinventory.inventory;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModConfig;
import com.lothrazar.powerinventory.UtilTextureRender;
import com.lothrazar.powerinventory.inventory.client.GuiButtonOpenInventory;
import com.lothrazar.powerinventory.inventory.slot.*;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer; 
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiCustomPlayerInventory extends GuiContainer
{
	ResourceLocation res = new ResourceLocation(Const.MODID, "inventory.png");
	public static boolean SHOW_DEBUG_NUMS = true;
	private final InventoryCustomPlayer inventory;
	private ContainerCustomPlayer container;
	private final EntityPlayer thePlayer;
	
	
	public GuiCustomPlayerInventory(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryCustomPlayer inventoryCustom)
	{
		//the player.inventory gets passed in here
		super(new ContainerCustomPlayer(player, inventoryPlayer, inventoryCustom));
		container = (ContainerCustomPlayer)this.inventorySlots;
		inventory = inventoryCustom;
		thePlayer = player;
		
		this.xSize = 338;
		this.ySize = 221;
	}
	
	@Override
	public void initGui()
    { 
		super.initGui();
		 /*
		int button_id = 199;
		int width = 26;
		final int height = 20;

		
		btnEnder = new GuiButtonOpenInventory(button_id++, 
				this.guiLeft + SlotEnderChest.posX + 19, 
				this.guiTop + SlotEnderChest.posY - 1,
				12,height, "I",Const.INV_ENDER); 
		this.buttonList.add(btnEnder); 
		//btnEnder.enabled = false;// turn it on based on ender chest present or not
		//btnEnder.visible = btnEnder.enabled;
		
 */
    }
	
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		
		if(SHOW_DEBUG_NUMS){ 
			for(Slot s : this.container.inventorySlots)
			{
				//each slot has two different numbers. the slotNumber is UNIQUE, the index is not
				this.drawString(this.fontRendererObj, "" + s.getSlotIndex(), 
						this.guiLeft + s.xDisplayPosition,
						this.guiTop + s.yDisplayPosition +  4, 
						16777120);//font color
			}
		}
	}
	@Override
	protected void drawGuiContainerForegroundLayer(	int p_146976_2_, int p_146976_3_)
	{ 
		//drawing text and such on screen
		
		
		this.checkSlotsEmpty();
	}
	
	private void checkSlotsEmpty()
	{
		final int s = 16;

		if(inventory.getStackInSlot(Const.enderPearlSlot) == null)
		{  
			UtilTextureRender.drawTextureSimple(SlotEnderPearl.background,SlotEnderPearl.posX, SlotEnderPearl.posY,s,s);
		}

		if(inventory.getStackInSlot(Const.enderChestSlot) == null)
		{  
			UtilTextureRender.drawTextureSimple(SlotEnderChest.background,SlotEnderChest.posX, SlotEnderChest.posY,s,s);
		}
	}
	private ResourceLocation bkg = new ResourceLocation(Const.MODID,  "textures/gui/inventory.png");
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,	int p_146976_2_, int p_146976_3_)
	{ 
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
		//this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID,  "textures/gui/inventory.png"));
		
		//UtilTextureRender.drawTexturedQuadFit(this.guiLeft, this.guiTop,this.xSize,this.ySize);//,0
		UtilTextureRender.drawTextureSimple(bkg, this.guiLeft, this.guiTop,this.xSize,this.ySize);
	 

        drawSlotAt(SlotEnderChest.posX, SlotEnderChest.posY);
    	drawSlotAt(SlotEnderPearl.posX, SlotEnderPearl.posY);
	}
	private void drawSlotAt(int x, int y)
	{
        UtilTextureRender.drawTextureSimple(Const.slot,this.guiLeft+ x -1, this.guiTop+ y -1,  Const.SQ, Const.SQ);
	}
}
