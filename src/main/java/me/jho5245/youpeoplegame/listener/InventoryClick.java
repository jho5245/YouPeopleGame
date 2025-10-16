package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.gui.GUIManager;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.YouPeopleGame.YouPeopleGameUserData;
import me.jho5245.youpeoplegame.util.GUICommand;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener
{
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
			case GUICommand.COOKIE_GUI_KEY -> {
				boolean updated = false;
				switch (slot)
				{
					case 0 -> {
						if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE);
							updated = true;
						}
					}
					case 1 -> {
						if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
					case 2 -> {
						if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
				}
				if (updated) GUICommand.openGUI(player);
			}
		}
	}
}
