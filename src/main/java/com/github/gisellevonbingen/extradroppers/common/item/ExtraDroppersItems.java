package com.github.gisellevonbingen.extradroppers.common.item;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtraDroppersItems
{
	public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraDroppers.MODID);

	public static void register(IEventBus eventBus)
	{
		register(eventBus, REGISTER);
	}

	public static void register(IEventBus eventBus, DeferredRegister<Item> register)
	{
		register.register(eventBus);
	}

}
