package com.github.gisellevonbingen.extradroppers.common.item;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;

import mekanism.common.Mekanism;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtraDroppersItems
{
	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraDroppers.MODID);
	public static final RegistryObject<ItemTinyGaugeDropper> tinyGaugeDropper;

	static
	{
		tinyGaugeDropper = REGISTER.register("tiny_gauge_dropper", () -> new ItemTinyGaugeDropper(new Properties().tab(Mekanism.tabMekanism)));
	}

	public static void register(IEventBus eventBus)
	{
		REGISTER.register(eventBus);
	}

}
