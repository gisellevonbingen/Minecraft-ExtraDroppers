package com.github.gisellevonbingen.extradroppers.datagen;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;
import com.github.gisellevonbingen.extradroppers.common.item.ExtraDroppersItems;

import mekanism.common.Mekanism;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider
{
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, ExtraDroppers.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		this.withParent(ExtraDroppersItems.tinyGaugeDropper.get(), "item/gauge_dropper");
	}

	public void withParent(IItemProvider item, String modelName)
	{
		ResourceLocation parentName = new ResourceLocation(Mekanism.MODID, modelName);
		ModelFile parent = this.getExistingFile(parentName);
		this.getBuilder(item.asItem().getRegistryName().toString()).parent(parent);
	}

}
