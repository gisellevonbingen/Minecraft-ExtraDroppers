package com.github.gisellevonbingen.extradroppers.util;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class NBTUtils
{
	public static <T extends INBT> void removeTag(ItemStack itemStack, String key)
	{
		if (itemStack != null)
		{
			CompoundNBT compound = itemStack.getTag();

			if (compound != null)
			{
				compound.remove(key);

				if (compound.size() == 0)
				{
					itemStack.setTag(null);
				}

			}

		}

	}

	public static <T extends INBT> void putTag(CompoundNBT compound, String key, T tag)
	{
		if (compound != null)
		{
			if (tag != null)
			{
				compound.put(key, tag);
			}
			else
			{
				compound.remove(key);
			}

		}

	}

	public static <T extends INBT> void putTag(ItemStack itemStack, String key, T tag)
	{
		if (tag == null)
		{
			removeTag(itemStack, key);
			return;
		}

		if (itemStack != null)
		{
			CompoundNBT compound = itemStack.getTag();

			if (compound == null)
			{
				compound = new CompoundNBT();
				itemStack.setTag(compound);
			}

			compound.put(key, tag);
		}

	}

	public static <T extends INBT> T getTag(CompoundNBT compound, String key)
	{
		return getTag(compound, key, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends INBT> T getTag(CompoundNBT compound, String key, Supplier<T> supplier)
	{
		if (compound != null)
		{
			INBT tag = compound.get(key);

			if (tag != null)
			{
				try
				{
					return (T) tag;
				}
				catch (Exception e)
				{

				}

			}

		}

		return supplier != null ? supplier.get() : null;
	}

	public static <T extends INBT> T getTag(ItemStack itemStack, String key)
	{
		return getTag(itemStack, key, null);
	}

	public static <T extends INBT> T getTag(ItemStack itemStack, String key, Supplier<T> supplier)
	{
		if (itemStack != null)
		{
			return getTag(itemStack.getTag(), key, supplier);
		}
		else
		{
			return supplier != null ? supplier.get() : null;
		}

	}

	public static CompoundNBT getCompound(ItemStack itemStack, String key)
	{
		return getTag(itemStack, key, () -> new CompoundNBT());
	}

	public static CompoundNBT getCompound(ItemStack itemStack, String key, Supplier<CompoundNBT> supplier)
	{
		return getTag(itemStack, key, supplier);
	}

	public static void putCompound(CompoundNBT compound, String key, CompoundNBT value)
	{
		if (compound != null)
		{
			compound.put(key, value);
		}

	}

	public static CompoundNBT getCompound(CompoundNBT compound, String key)
	{
		return getCompound(compound, key, null);
	}

	public static CompoundNBT getCompound(CompoundNBT compound, String key, Supplier<CompoundNBT> supplier)
	{
		if (compound != null)
		{
			return compound.getCompound(key);
		}
		else
		{
			return supplier != null ? supplier.get() : new CompoundNBT();
		}

	}

	public static void putInt(CompoundNBT compound, String key, int value)
	{
		if (compound != null)
		{
			compound.putInt(key, value);
		}

	}

	public static int getInt(CompoundNBT compound, String key)
	{
		return getInt(compound, key, null);
	}

	public static int getInt(CompoundNBT compound, String key, Supplier<Integer> supplier)
	{
		if (compound != null)
		{
			return compound.getInt(key);
		}
		else
		{
			return supplier != null ? supplier.get() : 0;
		}

	}

	public static void putLong(CompoundNBT compound, String key, long value)
	{
		if (compound != null)
		{
			compound.putLong(key, value);
		}

	}

	public static long getLong(CompoundNBT compound, String key)
	{
		return getLong(compound, key, null);
	}

	public static long getLong(CompoundNBT compound, String key, Supplier<Long> supplier)
	{
		if (compound != null)
		{
			return compound.getLong(key);
		}
		else
		{
			return supplier != null ? supplier.get() : 0L;
		}

	}

	private NBTUtils()
	{

	}

}
