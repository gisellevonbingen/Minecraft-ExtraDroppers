package com.github.gisellevonbingen.extradroppers.datagen;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagsGenerator extends ItemTagsProvider
{
	public ItemTagsGenerator(DataGenerator p_i244817_1_, BlockTagsProvider p_i244817_2_, ExistingFileHelper p_i244817_4_)
	{
		super(p_i244817_1_, p_i244817_2_, ExtraDroppers.MODID, p_i244817_4_);
	}

	@Override
	protected void addTags()
	{
		this.buildStatedMaterials();
	}

	protected void buildStatedMaterials()
	{

	}

}
