package me.jho5245.youpeoplegame.service.scheduler;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.function.Predicate;

public class CookieGiveawayEveryNSeconds extends SchedulerService
{
	ItemStack cookie;

	Predicate<Player> filter;

	private final HashMap<Player, Integer> cookieWaitTimeFlown;

	private final Object[] cookieMessages = {
			"&a쿠키 얻었다",
			"&a쿠키 하나 더 얻었다 개이득",
			"&a와 쿠키가 3개 이건 정말 신의 축복임이 틀림없어 놀라운!"
	};

	public CookieGiveawayEveryNSeconds()
	{
		cookie = CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE.create();
		filter = player ->
		{
			Location location = player.getLocation();
			int blockX = location.getBlockX(), blockY = location.getBlockY(), blockZ = location.getBlockZ();
			return blockX <= 1 && blockX >= -1 && blockY == 64 && blockZ <= 1 && blockZ >= -1;
		};
		cookieWaitTimeFlown = new HashMap<>();
	}

	public void run()
	{
		// 스폰에 있을 경우 5초마다 눅눅한 쿠키 1개 지급
		Bukkit.getScheduler().runTaskTimer(YouPeopleGame.getPlugin(), () ->
		{
			for (Player player : Bukkit.getOnlinePlayers().stream().filter(filter).toList())
			{
				int waitTick = getWaitTick(player);
				int currentTick = cookieWaitTimeFlown.getOrDefault(player, 0);
				if (currentTick > waitTick)
				{
					cookieWaitTimeFlown.put(player, 0);
					giveCookie(player);
				}
				else
				{
					cookieWaitTimeFlown.put(player, ++currentTick);
				}
			}
		}, 0L, 1L);
	}

	private void giveCookie(Player player)
	{
		PlayerInventory inventory = player.getInventory();
		int amount = getCookieAmount(player);
		if (ItemStackUtil.countSpace(inventory, cookie) >= amount)
		{
			cookie.setAmount(amount);
			MessageUtil.sendActionBar(player, cookieMessages[amount - 1]);
			AddItemUtil.addItem(player, cookie);
		}
		// 인벤토리 꽉 참
		else
		{
			MessageUtil.sendMessage(player, "&c&l더 이상 가질 수 없습니다!");
		}
	}

	public static int getWaitTick(Player player)
	{
		int waitTick = 100;
		if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED) && UserData.getBoolean(player,
				YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE))
		{
			waitTick -= 20;
		}
		if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED) && UserData.getBoolean(player,
				YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE))
		{
			waitTick -= 25;
		}
		return waitTick;
	}

	public static int getCookieAmount(Player player)
	{
		int amount = 1;
		// 25% 확률로 쿠키를 하나 더 얻는 기능
		if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED) && UserData.getBoolean(player,
				YouPeopleGameUserData.DAMP_COOKIE_POTION_USE) && Math.random() >= 0.75)
		{
			amount += 1;
		}
		// 50% 확률로 쿠키를 하나 더 얻는 기능
		if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED) && UserData.getBoolean(player,
				YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE) && Math.random() >= 0.5)
		{
			amount += 1;
		}
		return amount;
	}
}
