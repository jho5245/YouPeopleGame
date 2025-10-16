package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.YouPeopleGame.YouPeopleGameUserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
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
		switch (customMaterial)
		{
			case YOUPEOPLEGAME_DAMP_COOKIE_POTION ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e눅눅한 쿠키 포션의 버프가 해금되었습니다!"), 60, 60, 60));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e촉촉한 쿠키 촉진제의 버프가 해금되었습니다!"), 60, 60, 60));
			}			case YOUPEOPLEGAME_SUPER_MOIST_COOKIE_BOOSTER ->
			{
				if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
				{
					event.setCancelled(true);
					MessageUtil.sendWarn(player, "이미 사용한 아이템입니다.");
					return;
				}
				UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED);
				UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE);
				player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 1);
				player.showTitle(Title.title(Component.empty(), ComponentUtil.translate("&e촉촉한 쿠키 포션의 버프가 해금되었습니다!"), 60, 60, 60));
			}
			case null, default ->
			{
			}
		}
	}
}
