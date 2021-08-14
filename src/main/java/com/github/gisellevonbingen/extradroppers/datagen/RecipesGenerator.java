package com.github.gisellevonbingen.extradroppers.datagen;

import java.util.function.Consumer;

import com.github.gisellevonbingen.extradroppers.common.crafting.ShapelessRecipeBuilder;
import com.github.gisellevonbingen.extradroppers.common.item.ExtraDroppersItems;
import com.github.gisellevonbingen.extradroppers.common.item.ItemTinyGaugeDropper;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class RecipesGenerator extends RecipeProvider
{
	public RecipesGenerator(DataGenerator generator)
	{
		super(generator);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
	{
		RegistryObject<ItemTinyGaugeDropper> holder = ExtraDroppersItems.tinyGaugeDropper;
		ItemTinyGaugeDropper item = holder.get();
		ResourceLocation baseName = holder.getId();

		for (int i = 0; i < 9; i++)
		{
			ShapelessRecipeBuilder builder = new ShapelessRecipeBuilder(this.suffix(baseName, String.valueOf(i)));
			builder.setOutput(item.getItemStack((int) Math.pow(2, i)));
			builder.add(Ingredient.of(Items.STICK), i + 1);
			consumer.accept(builder.getResult());
		}

	}

	public ResourceLocation suffix(ResourceLocation original, String suffix)
	{
		return new ResourceLocation(original.getNamespace(), original.getPath() + suffix);
	}

}
