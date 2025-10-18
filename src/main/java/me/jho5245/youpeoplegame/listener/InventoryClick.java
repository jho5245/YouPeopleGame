package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.gui.GUIManager;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.YouPeopleGame.YouPeopleGameUserData;
import me.jho5245.youpeoplegame.util.GUICommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;
import java.util.Set;

public class InventoryClick implements Listener
{
	private final Set<Player> cooldowns = new HashSet<>();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getView().getPlayer();
		Component title = event.getView().title();
		if (!GUIManager.isGUITitle(title))
			return;
		String key = GUIManager.getGUIKey(event.getView().title());
		switch (key)
		{
			case GUICommand.COOKIE_GUI_KEY ->
			{
				boolean updated = false;
				switch (slot)
				{
					case 0 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE);
							updated = true;
						}
					}
					case 1 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
					case 2 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
				}
				if (updated)
					GUICommand.openGUI(player);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void toggleItemLore(InventoryClickEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() == 8 && ItemLore3.TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType()))
		{
			event.setCancelled(true);
			if (cooldowns.contains(player))
				return;
			cooldowns.add(player);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> cooldowns.remove(player), 10L);
			UserData.setToggle(player, YouPeopleGameUserData.ITEM_LORE_3);
			Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 1L);
		}
	}
}
