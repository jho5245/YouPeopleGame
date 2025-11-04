package me.jho5245.youpeoplegame.listener.player;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
	Location SPAWN_LOCATION = new Location(Bukkit.getWorld("youpeople_world"), 0, 64, 0);

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore())
		{
			AddItemUtil.addItem(player, CustomMaterialYouPeopleGame.STARTER_SWORD.create());
			AddItemUtil.addItem(player, CustomMaterialYouPeopleGame.STARTER_PICKAXE.create());
			AddItemUtil.addItem(player, CustomMaterialYouPeopleGame.STARTER_AXE.create());
			AddItemUtil.addItem(player, CustomMaterialYouPeopleGame.STARTER_SHOVEL.create());
		}
		if (!player.hasPermission("youpeoplegame.admin"))
		{
			player.teleport(SPAWN_LOCATION);
		}
		//		player.setResourcePack("https://github.com/jho5245/YouPeopleGame-Resourcepack/archive/refs/heads/main.zip", null, Component.text("다운받아라"), true);

		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () ->
		{
			player.showTitle(Title.title(ComponentUtil.translate("&e주의사항"), ComponentUtil.translate("&c제발 아이템 설명을 꼭 읽으시길 바랍니다."), 5, 100, 15));
		}, 100L);
	}
}
