package me.jho5245.youpeoplegame.service;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Predicate;

public class CookieGivewayEvery5Seconds extends Service
{
	ItemStack cookie;

	Predicate<Player> filter;

	public CookieGivewayEvery5Seconds()
	{
		cookie = CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE.create();
		filter = player -> {
			Location location = player.getLocation();
			int blockX = location.getBlockX(), blockY = location.getBlockY(), blockZ = location.getBlockZ();
			return blockX <= 1 && blockX >= -1 && blockY == 64 && blockZ <= 1 && blockZ >= -1;
		};
	}

	public void run()
	{
		// 스폰에 있을 경우 5초마다 눅눅한 쿠키 1개 지급
		Bukkit.getScheduler().runTaskTimer(YouPeopleGame.getPlugin(), () -> {
			for (Player player : Bukkit.getOnlinePlayers().stream().filter(filter).toList())
			{
				PlayerInventory inventory = player.getInventory();
				if (ItemStackUtil.countSpace(inventory, cookie) > 0)
				{
					AddItemUtil.addItem(player, cookie);
				}
				// 인벤토리 꽉 참
				else
				{
					MessageUtil.sendMessage(player, "&c&l더 이상 가질 수 없습니다!");
				}
			}
		}, 0L, 100L);
	}
}
