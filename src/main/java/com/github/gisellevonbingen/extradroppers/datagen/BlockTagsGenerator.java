package com.github.gisellevonbingen.extradroppers.datagen;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider
{
	public BlockTagsGenerator(DataGenerator p_i244820_1_, ExistingFileHelper p_i244820_3_)
	{
		super(p_i244820_1_, ExtraDroppers.MODID, p_i244820_3_);
	}

	@Override
	protected void addTags()
	{

	}

}
