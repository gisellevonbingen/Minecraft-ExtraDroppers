package com.github.gisellevonbingen.extradroppers.common.crafting.conditions;

import net.minecraftforge.common.crafting.CraftingHelper;

public class ExtraDroppersConditions
{
	public static void register()
	{
		CraftingHelper.register(new TagNotEmptyCondition.Serializer());
	}

}
