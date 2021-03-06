package com.lothrazar.powerinventory.inventory.button;

import com.lothrazar.powerinventory.ModInv;
import com.lothrazar.powerinventory.net.SortPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class GuiButtonSort extends GuiButton {

	public GuiButtonSort(int buttonId, int x, int y, int w) {
		super(buttonId, x, y, w, 20, StatCollector.translateToLocal("button.sort"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		boolean pressed = super.mousePressed(mc, mouseX, mouseY);

		if (pressed) {
			NBTTagCompound tags = new NBTTagCompound();

			// tags.setInteger(SortButtonPacket.NBT_SORT, sortType);

			ModInv.instance.network.sendToServer(new SortPacket(tags));// does

		}

		return pressed;
	}
}
