package me.jho5245.youpeoplegame.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemLoreModifier
{
	public static ItemLoreModifier instance = new ItemLoreModifier();

	private ItemLoreModifier()
	{
		instance = this;
	}

	public static ItemLoreModifier getInstance()
	{
		return instance;
	}


	public ItemStack perform(ItemStack item)
	{
		return perform(Collections.singletonList(item)).getFirst();
	}

	public List<ItemStack> perform(List<ItemStack> items)
	{
		items.forEach(item ->
		{
			if (item.hasItemMeta())
			{
				ItemMeta meta = item.getItemMeta();
				List<Component> lore = meta.lore();
				if (lore != null)
				{
					List<Component> lore2 = new ArrayList<>(lore);
					for (int i = 0; i < lore.size(); i++)
					{
						Component component = lore.get(i);
						if (i > 0 && component instanceof TranslatableComponent translatableComponent && translatableComponent.key().equals("내구도 : %s"))
						{
							lore2.remove(i - 1);
							lore2.remove(i - 1);
						}
					}
					meta.lore(lore2);
					item.setItemMeta(meta);
				}
			}
		});
		return items;
	}
}
