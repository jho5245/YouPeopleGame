package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.listener.ItemLore3;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;
import java.util.Set;

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

	private final Set<Player> cooldowns = new HashSet<>();

	public void listen(InventoryClickEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() == 8 && ItemLore3.TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType()))
		{
			event.setCancelled(true);
			if (cooldowns.contains(player))
				return;
			cooldowns.add(player);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> cooldowns.remove(player), 10L);
			UserData.setToggle(player, YouPeopleGameUserData.HIDE_ITEM_LORE_3);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 1L);
		}
	}
}
