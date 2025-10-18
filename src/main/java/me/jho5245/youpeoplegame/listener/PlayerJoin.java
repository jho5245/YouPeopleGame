package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import me.jho5245.youpeoplegame.YouPeopleGame;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
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
//		player.setResourcePack("https://github.com/jho5245/YouPeopleGame-Resourcepack/archive/refs/heads/main.zip", null, Component.text("다운받아라"), true);

		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> {
			player.showTitle(Title.title(ComponentUtil.translate("&e주의사항"), ComponentUtil.translate("&c제발 아이템 설명을 꼭 읽으시길 바랍니다."), 5, 100, 15));
		}, 100L);
	}
}
