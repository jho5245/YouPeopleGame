package me.jho5245.youpeoplegame.listener.player;

import me.jho5245.youpeoplegame.command.ParkourGiveUp;
import me.jho5245.youpeoplegame.command.GUICommand;
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
			// 파쿠르 도중인 플레이어는 포기 메시지를 띄움
			if (player.getScoreboardTags().contains("playing_parkour"))
			{
				ParkourGiveUp.get().listen(player);
				return;
			}
			GUICommand.openGUI(player);
		}
	}
}
