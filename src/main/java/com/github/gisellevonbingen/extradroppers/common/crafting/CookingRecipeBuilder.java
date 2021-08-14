package com.github.gisellevonbingen.extradroppers.common.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CookingRecipeBuilder extends SingleOutputRecipeBuilder
{
	private Ingredient ingredient;
	private float experience;
	private int smeltingTime;
	private int blastingTime;

	public CookingRecipeBuilder(ResourceLocation id)
	{
		super(id);

		this.setCookingTime(200);
	}

	public Ingredient getIngredient()
	{
		return this.ingredient;
	}

	public void setIngredient(Ingredient ingredient)
	{
		this.ingredient = ingredient;
	}

	public float getExperience()
	{
		return this.experience;
	}

	public void setExperience(float experience)
	{
		this.experience = experience;
	}

	public int getSmeltingTime()
	{
		return this.smeltingTime;
	}

	public CookingRecipeBuilder setSmeltingTime(int smeltingTime)
	{
		this.smeltingTime = smeltingTime;
		return this;
	}

	public int getBlastingTime()
	{
		return this.blastingTime;
	}

	public CookingRecipeBuilder setBlastingTime(int blastingTime)
	{
		this.blastingTime = blastingTime;
		return this;
	}

	public CookingRecipeBuilder setCookingTime(int cookingTime)
	{
		this.setSmeltingTime(cookingTime);
		this.setBlastingTime(cookingTime / 2);
		return this;
	}

	public Result getSmelting()
	{
		return new Result(this, "smelting", this.smeltingTime, IRecipeSerializer.SMELTING_RECIPE);
	}

	public Result getBlasting()
	{
		return new Result(this, "blasting", this.blastingTime, IRecipeSerializer.BLASTING_RECIPE);
	}

	public static class Result extends SingleOutputRecipeResult
	{
		private final Ingredient ingredient;
		private final float experience;
		private final int cookingTime;

		private IRecipeSerializer<?> type;

		public Result(CookingRecipeBuilder builder, String name, int cookingTime, IRecipeSerializer<?> type)
		{
			super(builder, new ResourceLocation(builder.getId().getNamespace(), builder.getId().getPath() + "_" + name));

			this.ingredient = builder.ingredient;
			this.experience = builder.experience;
			this.cookingTime = cookingTime;
			this.type = type;
		}

		@Override
		public void serializeRecipeData(JsonObject json)
		{
			super.serializeRecipeData(json);

			json.add("ingredient", this.ingredient.toJson());
			json.addProperty("experience", this.experience);
			json.addProperty("cookingtime", this.cookingTime);
		}

		public Ingredient getIngredient()
		{
			return this.ingredient;
		}

		public float getExperience()
		{
			return this.experience;
		}

		public int getCookingTime()
		{
			return this.cookingTime;
		}

		@Override
		public IRecipeSerializer<?> getType()
		{
			return this.type;
		}

	}

}
