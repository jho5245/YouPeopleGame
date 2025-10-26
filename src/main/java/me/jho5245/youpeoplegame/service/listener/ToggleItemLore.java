package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.listener.ItemLore3;
import me.jho5245.youpeoplegame.util.Cooldowns;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ToggleItemLore
{
	private static final ToggleItemLore instance = new ToggleItemLore();

	private ToggleItemLore()
	{
	}

	public static ToggleItemLore get()
	{
		return instance;
	}

	public void listen(InventoryClickEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		if (event.getClick() == ClickType.NUMBER_KEY && ItemLore3.TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType()))
		{
			int hotbarButton = event.getHotbarButton();
			if (hotbarButton >= 6)
			{
				if (Cooldowns.TOGGLE_ITEM_LORE_COOLDOWN.contains(player))
					return;
				ItemStack currentItem = event.getCurrentItem();
				if (!ItemStackUtil.itemExists(event.getCurrentItem()))
					return;
				boolean success = false;
				boolean isNotSack = Category.getByCustomMaterial(CustomMaterial.itemStackOf(currentItem)) == null;
				switch (hotbarButton)
				{
					case 7 ->
					{
						// item이 sack일 경우에만 기능 전환
						if (isNotSack)
						{
							return;
						}
						UserData.setToggle(player, YouPeopleGameUserData.MATERIAL_STORAGE_SHOW_CAPACITY_INFO);
						success = true;
					}
					case 8 ->
					{
						success = true;
						UserData.setToggle(player, YouPeopleGameUserData.HIDE_ITEM_LORE_3);
					}
				}
				if (success)
				{
					event.setCancelled(true);
					Cooldowns.TOGGLE_ITEM_LORE_COOLDOWN.add(player);
					Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> Cooldowns.TOGGLE_ITEM_LORE_COOLDOWN.remove(player), 10L);
					Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 1L);
				}
			}
		}
	}
}
