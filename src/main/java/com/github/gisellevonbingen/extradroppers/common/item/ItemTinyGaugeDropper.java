package com.github.gisellevonbingen.extradroppers.common.item;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Triple;

import com.github.gisellevonbingen.extradroppers.common.capabilities.merged.TinyGaugeDropperContentsHandler;
import com.github.gisellevonbingen.extradroppers.util.NBTUtils;

import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.capabilities.ItemCapabilityWrapper.ItemCapability;
import mekanism.common.item.ItemGaugeDropper;
import mekanism.common.util.StorageUtils;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;

public class ItemTinyGaugeDropper extends ItemGaugeDropper
{
	public static final String TagKeyMain = "tiny_gauge_dropper";
	public static final String TagKeyCapacity = "capacity";

	public ItemTinyGaugeDropper(Properties properties)
	{
		super(properties);
	}

	@Override
	public ITextComponent getName(ItemStack p_200295_1_)
	{
		return new StringTextComponent("Tiny Gauge Dropper (" + getCapacity(p_200295_1_) + ")");
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@Nonnull ItemStack itemStack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag)
	{
		Triple<MekanismLang, Object, Long> triple = getContentsAmout(itemStack);
		MekanismLang type = triple.getLeft();
		Object stack = triple.getMiddle();
		String status = TextUtils.format(triple.getRight()) + " / " + getCapacity(itemStack);

		IFormattableTextComponent textComponent = type.translateColored(EnumColor.YELLOW, new Object[]{EnumColor.ORANGE, MekanismLang.GENERIC_STORED_MB.translate(new Object[]{stack, EnumColor.GRAY, status})});
		tooltip.add(textComponent);
	}

	public ItemStack getItemStack(int capacity)
	{
		ItemStack itemStack = new ItemStack(this);
		setCapacity(itemStack, capacity);
		return itemStack;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list)
	{
		if (this.allowdedIn(group) == true)
		{
			list.add(this.getItemStack(1));
			list.add(this.getItemStack(2));
			list.add(this.getItemStack(4));
			list.add(this.getItemStack(8));
			list.add(this.getItemStack(16));
			list.add(this.getItemStack(32));
			list.add(this.getItemStack(64));
			list.add(this.getItemStack(128));
			list.add(this.getItemStack(256));
		}

	}

	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new ItemCapabilityWrapper(stack, new ItemCapability[]{new TinyGaugeDropperContentsHandler(stack)});
	}

	public static int getCapacity(ItemStack itemStack)
	{
		CompoundNBT compound = NBTUtils.getCompound(itemStack, TagKeyMain);
		return NBTUtils.getInt(compound, TagKeyCapacity);
	}

	public static void setCapacity(ItemStack itemStack, int capacity)
	{
		CompoundNBT compound = NBTUtils.getCompound(itemStack, TagKeyMain);
		NBTUtils.putInt(compound, TagKeyCapacity, capacity);
		NBTUtils.putTag(itemStack, TagKeyMain, compound);
	}

	public static Triple<MekanismLang, Object, Long> getContentsAmout(@Nonnull ItemStack stack)
	{
		FluidStack fluidStack = StorageUtils.getStoredFluidFromNBT(stack);

		if (fluidStack.isEmpty() == false)
		{
			return Triple.of(MekanismLang.LIQUID, fluidStack, (long) fluidStack.getAmount());
		}

		GasStack gasStack = StorageUtils.getStoredGasFromNBT(stack);

		if (gasStack.isEmpty() == false)
		{
			return Triple.of(MekanismLang.GAS, gasStack, (long) gasStack.getAmount());
		}

		InfusionStack infusionStack = StorageUtils.getStoredInfusionFromNBT(stack);

		if (infusionStack.isEmpty() == false)
		{
			return Triple.of(MekanismLang.INFUSE_TYPE, infusionStack, (long) infusionStack.getAmount());
		}

		PigmentStack pigmentStack = StorageUtils.getStoredPigmentFromNBT(stack);

		if (pigmentStack.isEmpty() == false)
		{
			return Triple.of(MekanismLang.PIGMENT, pigmentStack, (long) pigmentStack.getAmount());
		}

		SlurryStack slurryStack = StorageUtils.getStoredSlurryFromNBT(stack);

		if (slurryStack.isEmpty() == false)
		{
			return Triple.of(MekanismLang.SLURRY, slurryStack, (long) slurryStack.getAmount());
		}

		return Triple.of(MekanismLang.LIQUID, new FluidStack(Fluids.EMPTY, 0), 0L);
	}

}
