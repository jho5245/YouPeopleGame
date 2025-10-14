package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItem implements Listener
{
	@EventHandler
	public void onCraftItem(final CraftItemEvent event)
	{
		if (event.isCancelled())
			return;
		Player player = (Player) event.getView().getPlayer();
		event.setCancelled(true);
		if (player.hasPermission("youpeoplegame.admin"))
		{
			MessageUtil.sendDebug(player, "아이템 제작 불가");
		}
	}
}
