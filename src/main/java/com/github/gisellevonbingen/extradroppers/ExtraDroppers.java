package com.github.gisellevonbingen.extradroppers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.gisellevonbingen.extradroppers.client.ClientHandler;
import com.github.gisellevonbingen.extradroppers.common.block.ExtraDroppersBlocks;
import com.github.gisellevonbingen.extradroppers.common.crafting.conditions.ExtraDroppersConditions;
import com.github.gisellevonbingen.extradroppers.common.item.ExtraDroppersItems;
import com.github.gisellevonbingen.extradroppers.config.ExtraDroppersConfig;
import com.github.gisellevonbingen.extradroppers.datagen.DataGenerators;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtraDroppers.MODID)
public class ExtraDroppers
{
	public static final String MODID = "extradroppers";
	public static final String MODANME = "More Mekanism Processing";
	public static final Logger LOGGER = LogManager.getLogger();

	public ExtraDroppers()
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientHandler::new);

		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ExtraDroppersConfig.CommonSpec);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(new DataGenerators());

		ExtraDroppersBlocks.register(modEventBus);
		ExtraDroppersItems.register(modEventBus);
		ExtraDroppersConditions.register();
	}

}
