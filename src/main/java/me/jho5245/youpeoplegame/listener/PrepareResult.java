package me.jho5245.youpeoplegame.listener;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PrepareResult implements Listener
{
	@EventHandler
	public void onPrepareResult(PrepareResultEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		event.setResult(null);
		if (player.hasPermission("youpeoplegame.admin"))
		{
			MessageUtil.sendDebug(player, "아이템 제작 불가");
		}
	}
}
