package com.lothrazar.powerinventory.inventory;

import java.util.Arrays;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.InventoryRenderer;
import com.lothrazar.powerinventory.PlayerPersistProperty;
import com.lothrazar.powerinventory.config.ModConfig;
import com.lothrazar.powerinventory.inventory.button.GuiButtonUnlockChest;
import com.lothrazar.powerinventory.inventory.button.GuiButtonUnlockPearl;
import com.lothrazar.powerinventory.inventory.button.GuiButtonUnlockStorage;
import com.lothrazar.powerinventory.inventory.button.IGuiTooltip;
import com.lothrazar.powerinventory.inventory.slot.*;
import com.lothrazar.powerinventory.util.UtilExperience;
import com.lothrazar.powerinventory.util.UtilTextureRender;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer; 
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiOverpowered extends GuiContainer
{
	private ResourceLocation bkg = new ResourceLocation(Const.MODID,  "textures/gui/inventory.png");
	private ResourceLocation bkg_large = new ResourceLocation(Const.MODID,  "textures/gui/inventory_large.png");
	//private ResourceLocation bkg_craft = new ResourceLocation(Const.MODID,  "textures/gui/crafting.png");
	private ResourceLocation bkg_3x9 = new ResourceLocation(Const.MODID,  "textures/gui/slots3x9.png");
	public static ResourceLocation slot = new ResourceLocation(Const.MODID,"textures/gui/inventory_slot.png");
	//public static final int craftX = 56; 
	//public static final int craftY = 10;//was 14 
	public static boolean SHOW_DEBUG_NUMS = false;
	private final InventoryOverpowered inventory;
	private ContainerOverpowered container;
	final int h = 20;
	final int w = 20;//default button dims
	final int padding = 6;//on the far outer sizes
	final EntityPlayer thePlayer;
	
	public GuiOverpowered(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryOverpowered inventoryCustom)
	{
		//the player.inventory gets passed in here
		super(new ContainerOverpowered(player, inventoryPlayer, inventoryCustom));
		container = (ContainerOverpowered)this.inventorySlots;
		inventory = inventoryCustom;
		thePlayer = player;
		
		
		//fixed numbers from the .png resource size
		this.xSize = ModConfig.getInvoWidth();
		this.ySize = ModConfig.getInvoHeight();
	}

	@Override
	public void initGui()
    { 
		super.initGui();
		
		int button_id = 99;
		String label;
		GuiButton b;

		if(container.epearlSlotEnabled == false){

			int current = (int)UtilExperience.getExpTotal(thePlayer);
			label = current + "/" + ModConfig.expCostPearl;
			
			b = new GuiButtonUnlockPearl(button_id++,
					this.guiLeft + padding,  
					this.guiTop + padding,label);
			this.buttonList.add(b);
			
			b.enabled = (current >= ModConfig.expCostPearl);
		}
		if(container.echestSlotEnabled == false){

			int current = (int)UtilExperience.getExpTotal(thePlayer);
			label = current + "/" + ModConfig.expCostEChest;
			
			b = new GuiButtonUnlockChest(button_id++,
					this.guiLeft + ModConfig.getInvoWidth() - padding - GuiButtonUnlockChest.width,  
					this.guiTop + padding,label);
			this.buttonList.add(b);
			
			b.enabled = (current >= ModConfig.expCostEChest);
		}
		
		
		//int centerHorizCol = Const.SLOTS_WIDTH/2 - GuiButtonUnlockStorage.width/2;
		//int centerVert = topspace - Const.SLOTS_HEIGHT/2 - GuiButtonUnlockStorage.height/2;

		PlayerPersistProperty prop = PlayerPersistProperty.get(thePlayer);
		
		int current = (int)UtilExperience.getExpTotal(thePlayer);
		//draw only one single button then stop
		for(int i = 1; i <= ModConfig.getMaxSections(); i++){
			if(prop.hasStorage(i) == false){
					
				label = current + "/" + ModConfig.expCostStorage + " XP";
				
				b = new GuiButtonUnlockStorage(button_id++,
						this.guiLeft + InventoryRenderer.xPosBtn(i),
						this.guiTop  + InventoryRenderer.yPosBtn(i),
						 label,1);
				
				this.buttonList.add(b);
				
				b.enabled = (current >= ModConfig.expCostStorage);
				break;
			}
		}
    }
	
	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);

		GuiButton btn;
		for (int i = 0; i < buttonList.size(); i++) 
		{
			btn = buttonList.get(i);
			if (btn instanceof IGuiTooltip && btn.isMouseOver() ) 
			{
				String tooltip = ((IGuiTooltip)btn).getTooltip();
				if (tooltip != null) 
				{
					//it takes a list, one on each line. but we use single line tooltips
					drawHoveringText(Arrays.asList(new String[]{ tooltip}), x, y, fontRendererObj);
				}
			}
		}
		
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{ 
		this.checkSlotsEmpty();
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	private void checkSlotsEmpty()
	{
		final int s = 16;

		if(container.epearlSlotEnabled && inventory.getStackInSlot(Const.SLOT_EPEARL) == null){
			UtilTextureRender.drawTextureSimple(SlotEnderPearl.background,SlotEnderPearl.posX, SlotEnderPearl.posY,s,s);
		}

		if(container.echestSlotEnabled && inventory.getStackInSlot(Const.SLOT_ECHEST) == null){  
			UtilTextureRender.drawTextureSimple(SlotEnderChest.background,SlotEnderChest.posX, SlotEnderChest.posY,s,s);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
		if(ModConfig.isLargeScreen())
			UtilTextureRender.drawTextureSimple(bkg_large, this.guiLeft, this.guiTop,this.xSize,this.ySize);
		else
			UtilTextureRender.drawTextureSimple(bkg, this.guiLeft, this.guiTop,this.xSize,this.ySize);

		int left=7,pad=4;//pad is middle padding. left is left edge padding

		PlayerPersistProperty prop = PlayerPersistProperty.get(thePlayer);
		//always render this one

		//top left
		if(prop.hasStorage(1))
			drawSlotSectionAt(
					this.guiLeft+left, 
					this.guiTop+InventoryRenderer.topspace);
		//topright is 
		if(prop.hasStorage(2))
			drawSlotSectionAt(
					this.guiLeft+pad+left+Const.SLOTS_WIDTH, 
					this.guiTop+InventoryRenderer.topspace);
		//lower left is 
		if(prop.hasStorage(3))
			drawSlotSectionAt(
					this.guiLeft+left, 
					this.guiTop+InventoryRenderer.topspace+pad+Const.SLOTS_HEIGHT);
		//lower right is 
		if(prop.hasStorage(4))
			drawSlotSectionAt(
					this.guiLeft+pad+left+Const.SLOTS_WIDTH, 
					this.guiTop+InventoryRenderer.topspace+pad+Const.SLOTS_HEIGHT);

		if(prop.hasStorage(5))
			drawSlotSectionAt( 
					this.guiLeft+left, 
					this.guiTop+InventoryRenderer.topspace+2*(pad+Const.SLOTS_HEIGHT));
		
		if(prop.hasStorage(6))
			drawSlotSectionAt(
					this.guiLeft+pad+left+Const.SLOTS_WIDTH, 
					this.guiTop+InventoryRenderer.topspace+2*(pad+Const.SLOTS_HEIGHT));
		
        if(container.echestSlotEnabled){drawSlotAt(SlotEnderChest.posX, SlotEnderChest.posY);}
    	if(container.epearlSlotEnabled){drawSlotAt(SlotEnderPearl.posX, SlotEnderPearl.posY);}
	}
	
	private void drawSlotSectionAt(int x, int y){
		UtilTextureRender.drawTextureSimple(bkg_3x9, x, y, Const.SLOTS_WIDTH, Const.SLOTS_HEIGHT);
	}
	
	private void drawSlotAt(int x, int y)
	{
        UtilTextureRender.drawTextureSimple(slot,this.guiLeft + x - 1, this.guiTop + y - 1, Const.SQ, Const.SQ);
	}
}
