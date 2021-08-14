package com.github.gisellevonbingen.extradroppers.datagen;

import java.lang.reflect.Field;

import com.github.gisellevonbingen.extradroppers.ExtraDroppers;
import com.github.gisellevonbingen.extradroppers.util.UnsafeHelper;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
		boolean enable = this.existingFileHelper.isEnabled();
		Field enableField = null;

		try
		{
			try
			{
				enableField = ExistingFileHelper.class.getDeclaredField("enable");
				enableField.setAccessible(true);
			}
			catch (NoSuchFieldException | SecurityException e)
			{
				e.printStackTrace();
			}

			if (enableField != null)
			{
				UnsafeHelper.putBoolean(this.existingFileHelper, enableField, false);
			}

			this.onRegisterModels();
		}
		finally
		{
			if (enableField != null)
			{
				UnsafeHelper.putBoolean(this.existingFileHelper, enableField, enable);
			}

		}

	}

	protected void onRegisterModels()
	{

	}

	protected ResourceLocation child(ResourceLocation parent)
	{
		return new ResourceLocation(parent.getNamespace(), "item/" + parent.getPath());
	}

}
