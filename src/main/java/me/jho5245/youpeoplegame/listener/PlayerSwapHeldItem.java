package me.jho5245.youpeoplegame.listener;

import me.jho5245.youpeoplegame.util.GUICommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHeldItem implements Listener
{
	@EventHandler
	public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event)
	{
		Player player = event.getPlayer();
		if (player.isSneaking())
		{
			event.setCancelled(true);
			GUICommand.openGUI(player);
		}
	}
}
