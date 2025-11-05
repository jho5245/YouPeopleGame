package me.jho5245.youpeoplegame.util;

import com.jho5245.cucumbery.custom.custommaterial.CustomMaterial;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

	public ItemStack perform(Player player, ItemStack item)
	{
		return perform(player, Collections.singletonList(item)).getFirst();
	}

	public List<ItemStack> perform(Player player, List<ItemStack> items)
	{
		if (player.getGameMode() == GameMode.CREATIVE)
			return items;
		List<ItemStack> result = new ArrayList<>();
		items.forEach(itemStack ->
		{
			CustomMaterial customMaterial = CustomMaterial.itemStackOf(itemStack);
			// nbt
			{

			}

			// itemMeta
			{
				if (itemStack.hasItemMeta())
				{
					ItemMeta itemMeta = itemStack.getItemMeta();
					// YouPeopleGame은 아이템 내구도가 없으므로 내구도 삭제
					{
						List<Component> lore = itemMeta.lore();
						if (lore != null)
						{
							List<Component> newLore = new ArrayList<>(lore);
							for (int i = 0; i < lore.size(); i++)
							{
								Component component = lore.get(i);
								if (i > 0 && component instanceof TranslatableComponent translatableComponent && translatableComponent.key().equals("내구도 : %s"))
								{
									newLore.remove(i - 1);
									newLore.remove(i - 1);
								}
							}
							itemMeta.lore(newLore);
						}
					}
					// 특별 도구
					{
						if (customMaterial == CustomMaterialYouPeopleGame.SPECIAL_TOOL)
						{
							String specialToolString = UserData.getString(player, YouPeopleGameUserData.SPECIAL_TOOL);
							List<Component> lore = new ArrayList<>();
							switch (specialToolString)
							{
								case "1" -> {
									itemMeta.setItemModel(Material.EMERALD.getKey());
									itemMeta.itemName(ComponentUtil.translate("&7빛바랜 에메랄등"));
									lore.add(ComponentUtil.translate("&7에메랄드인데 빛이 나지 않는다."));
									lore.add(ComponentUtil.translate("&7그래도 꽤나 날카로워 보인다."));
								}
								case null -> {
									itemMeta.setItemModel(Material.STICK.getKey());
									itemMeta.itemName(ComponentUtil.translate("&7낡은 막대기"));
									lore.add(ComponentUtil.translate("&7평범한 낡은 막대기다. 캐릭터의 성장 방향에 따라"));
									lore.add(ComponentUtil.translate("&7강력한 도구로 바뀔 수도 있을 것 같다."));
								}
								default -> {}
							}
							if (!lore.isEmpty())
							{
								lore.addFirst(Component.empty());
								addLore(itemMeta, lore);
							}
						}
					}
					itemStack.setItemMeta(itemMeta);
				}
			}
			result.add(itemStack);
		});
		return result;
	}

	private void addLore(ItemMeta itemMeta, List<Component> lore)
	{
		List<Component> newLore = itemMeta.lore();
		if (newLore == null) newLore = new ArrayList<>();
		newLore.addAll(lore);
		itemMeta.lore(newLore);
	}
}
