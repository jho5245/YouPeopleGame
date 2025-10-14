package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import me.jho5245.youpeoplegame.YouPeopleGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore())
		{
			AddItemUtil.addItem(player, CustomMaterial.YOUPEOPLEGAME_STARTER_SWORD.create());
			AddItemUtil.addItem(player, CustomMaterial.YOUPEOPLEGAME_STARTER_PICKAXE.create());
			AddItemUtil.addItem(player, CustomMaterial.YOUPEOPLEGAME_STARTER_AXE.create());
			AddItemUtil.addItem(player, CustomMaterial.YOUPEOPLEGAME_STARTER_SHOVEL.create());
		}
		if (!player.hasPermission("youpeoplegame.admin"))
		{
			player.teleport(YouPeopleGame.SPAWN_LOCATION);
		}
	}
}
