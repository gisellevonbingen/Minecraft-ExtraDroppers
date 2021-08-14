package com.github.gisellevonbingen.extradroppers.datagen;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguagesGenerator extends LanguageProvider
{
	public LanguagesGenerator(DataGenerator generator)
	{
		super(generator, ExtraDroppers.MODID, "en_us");
	}

	@Override
	protected void addTranslations()
	{

	}

}
