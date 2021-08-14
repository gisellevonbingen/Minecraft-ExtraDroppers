package com.github.gisellevonbingen.extradroppers.common.capabilities.merged;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.gisellevonbingen.extradroppers.common.item.ItemTinyGaugeDropper;

import mekanism.api.DataHandlerUtils;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.fluid.IMekanismFluidHandler;
import mekanism.common.capabilities.CapabilityCache;
import mekanism.common.capabilities.DynamicHandler.InteractPredicate;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler.DynamicGasHandler;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler.DynamicInfusionHandler;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler.DynamicPigmentHandler;
import mekanism.common.capabilities.chemical.dynamic.DynamicChemicalHandler.DynamicSlurryHandler;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank.RateLimitGasTank;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank.RateLimitInfusionTank;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank.RateLimitPigmentTank;
import mekanism.common.capabilities.chemical.variable.RateLimitChemicalTank.RateLimitSlurryTank;
import mekanism.common.capabilities.fluid.item.RateLimitFluidHandler.RateLimitFluidTank;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.capabilities.merged.MergedTankContentsHandler;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import mekanism.common.util.ItemDataUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TinyGaugeDropperContentsHandler extends MergedTankContentsHandler<MergedTank> implements IMekanismFluidHandler, IFluidHandlerItem
{
	private final ItemStack itemStack;
	protected List<IExtendedFluidTank> fluidTanks;

	private int transferRate = 256;

	public TinyGaugeDropperContentsHandler(ItemStack itemStack)
	{
		InteractPredicate alwaysTrue = InteractPredicate.ALWAYS_TRUE;

		RateLimitFluidTank fluidTank = new RateLimitFluidTank(this.getTransferRate(), this::getCapacity, this);
		RateLimitGasTank gasTank = new RateLimitGasTank(this::getTransferRate, this::getCapacity, ChemicalTankBuilder.GAS.alwaysTrueBi, ChemicalTankBuilder.GAS.alwaysTrueBi, ChemicalTankBuilder.GAS.alwaysTrue, (ChemicalAttributeValidator) null, this.gasHandler = new DynamicGasHandler(this::getGasTanks, alwaysTrue, alwaysTrue, this::onGasTanksContentsChanged));
		RateLimitInfusionTank infusionTank = new RateLimitInfusionTank(this::getTransferRate, this::getCapacity, ChemicalTankBuilder.INFUSION.alwaysTrueBi, ChemicalTankBuilder.INFUSION.alwaysTrueBi, ChemicalTankBuilder.INFUSION.alwaysTrue, this.infusionHandler = new DynamicInfusionHandler(this::getInfusionTanks, alwaysTrue, alwaysTrue, this::onInfustionTanksContentsChanged));
		RateLimitPigmentTank pigmentTank = new RateLimitPigmentTank(this::getTransferRate, this::getCapacity, ChemicalTankBuilder.PIGMENT.alwaysTrueBi, ChemicalTankBuilder.PIGMENT.alwaysTrueBi, ChemicalTankBuilder.PIGMENT.alwaysTrue, this.pigmentHandler = new DynamicPigmentHandler(this::getPigmentTanks, alwaysTrue, alwaysTrue, this::onPigmentTanksContentsChanged));
		RateLimitSlurryTank slurryTank = new RateLimitSlurryTank(this::getTransferRate, this::getCapacity, ChemicalTankBuilder.SLURRY.alwaysTrueBi, ChemicalTankBuilder.SLURRY.alwaysTrueBi, ChemicalTankBuilder.SLURRY.alwaysTrue, this.slurryHandler = new DynamicSlurryHandler(this::getSlurryTanks, alwaysTrue, alwaysTrue, this::onSlurryTanksContentsChanged));

		this.itemStack = itemStack;
		this.mergedTank = MergedTank.create(fluidTank, gasTank, infusionTank, pigmentTank, slurryTank);
	}

	protected void onAllTanksContentsChanged()
	{
		this.onFluidTanksContentsChanged();
		this.onGasTanksContentsChanged();
		this.onInfustionTanksContentsChanged();
		this.onPigmentTanksContentsChanged();
		this.onSlurryTanksContentsChanged();
	}

	protected void onSlurryTanksContentsChanged()
	{
		this.onContentsChanged("SlurryTanks", this.slurryTanks);
	}

	protected void onPigmentTanksContentsChanged()
	{
		this.onContentsChanged("PigmentTanks", this.pigmentTanks);
	}

	protected void onInfustionTanksContentsChanged()
	{
		this.onContentsChanged("InfusionTanks", this.infusionTanks);
	}

	protected void onGasTanksContentsChanged()
	{
		this.onContentsChanged("GasTanks", this.gasTanks);
	}

	protected void onFluidTanksContentsChanged()
	{
		this.onContentsChanged("FluidTanks", this.fluidTanks);
	}

	public int getTransferRate()
	{
		return this.transferRate;
	}

	public int getCapacity()
	{
		return ItemTinyGaugeDropper.getCapacity(this.getStack());
	}

	protected void init()
	{
		super.init();

		this.fluidTanks = Collections.singletonList(((MergedTank) this.mergedTank).getFluidTank());
	}

	protected void load()
	{
		super.load();

		ItemStack stack = this.getStack();

		if (!stack.isEmpty())
		{
			DataHandlerUtils.readContainers(this.getFluidTanks((Direction) null), ItemDataUtils.getList(stack, "FluidTanks"));
		}

		this.onAllTanksContentsChanged();
	}

	@Nonnull
	public List<IExtendedFluidTank> getFluidTanks(@Nullable Direction side)
	{
		return this.fluidTanks;
	}

	@Nonnull
	public List<IGasTank> getGasTanks(@Nullable Direction side)
	{
		return this.gasTanks;
	}

	@Nonnull
	public List<IInfusionTank> getInfusionTanks(@Nullable Direction side)
	{
		return this.infusionTanks;
	}

	@Nonnull
	public List<IPigmentTank> getPigmentTanks(@Nullable Direction side)
	{
		return this.pigmentTanks;
	}

	@Nonnull
	public List<ISlurryTank> getSlurryTanks(@Nullable Direction side)
	{
		return this.slurryTanks;
	}

	public void onContentsChanged()
	{
		this.onFluidTanksContentsChanged();
	}

	@Override
	public ItemStack getStack()
	{
		return this.itemStack;
	}

	@Nonnull
	public ItemStack getContainer()
	{
		return this.getStack();
	}

	protected void addCapabilityResolvers(CapabilityCache capabilityCache)
	{
		super.addCapabilityResolvers(capabilityCache);
		capabilityCache.addCapabilityResolver(BasicCapabilityResolver.constant(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, this));
	}

}
