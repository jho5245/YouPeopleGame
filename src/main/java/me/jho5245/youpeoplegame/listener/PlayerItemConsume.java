package me.jho5245.youpeoplegame.listener;

import com.google.errorprone.annotations.Var;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.data.Variable;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.nisovin.shopkeepers.api.user.User;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.YouPeopleGame.YouPeopleGameUserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemConsume implements Listener
{
	@EventHandler
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		if (customMaterial == CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_POTION)
		{
			if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USED.toString()))
			{
				event.setCancelled(true);
				MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
				return;
			}
			UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USED.toString());
			player.showTitle(
					Title.title(
							Component.empty(),
							ComponentUtil.translate("&e눅눅한 쿠키 포션의 버프가 해금되었습니다!"),
							60, 60, 60
			));
		}
	}
}
