package me.jho5245.youpeoplegame.service;

import com.jho5245.cucumbery.custom.customeffect.CustomEffectManager;
import com.jho5245.cucumbery.custom.customeffect.type.CustomEffectType;
import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.no_groups.Method;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.Constant;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.service.scheduler.SchedulerService;
import me.jho5245.youpeoplegame.util.Cooldown;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

public class SheepWool extends SchedulerService
{
	private static final SheepWool instance = new SheepWool();

	private SheepWool()
	{
	}

	public static SheepWool get()
	{
		return instance;
	}

	private final HashMap<Player, Cooldown> cooldowns = new HashMap<>();

	private final HashMap<Player, ItemStack> results = new HashMap<>();

	private final DecimalFormat df = new DecimalFormat("00");

	private boolean pre(PlayerShearEntityEvent event)
	{
		Entity entity = event.getEntity();
		if (!(entity instanceof Sheep sheep))
		{
			return false;
		}
		Component component = sheep.customName();
		if (!(component instanceof TextComponent textComponent && textComponent.content().equals("jeb_")))
		{
			return false;
		}
		if (!sheep.getScoreboardTags().contains("jeb_"))
		{
			return false;
		}
		ItemStack item = event.getItem();
		if (!ItemStackUtil.itemExists(item))
		{
			return false;
		}
		return item.getType() == Material.SHEARS;
	}

	public void listen(PlayerShearEntityEvent event)
	{
		if (!pre(event))
		{
			event.setCancelled(true);
			return;
		}
		Player player = event.getPlayer();
		Entity entity = event.getEntity();
		event.setCancelled(true);
		if (cooldowns.containsKey(player) && cooldowns.get(player).getCurrent() > 0)
		{
			return;
		}
		player.swingHand(event.getHand());
		Sheep sheep = (Sheep) entity;
		cooldowns.put(player, new Cooldown(getCooldown(event)));
		ItemStack result = getResult(player, sheep);
		results.put(player, result);
		player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1, 1);
	}

	private long getCooldown(PlayerShearEntityEvent event)
	{
		int cooldown = Method.random(80, 100);
		int skillLevel = UserData.getInt(event.getPlayer(), "YouPeopleGame.skillLevel");
		cooldown -= skillLevel * 5;
		return Math.max(1, cooldown);
	}

	private ItemStack getResult(Player player, Sheep sheep)
	{
		int minAmount = 1, maxAmount = 2;
		ItemStack itemStack;
		itemStack = new ItemStack(Material.WHITE_WOOL);
		if (CustomEffectManager.hasEffect(player, CustomEffectType.NOTHING))
		{
			maxAmount += 2;
		}
		Set<String> tags = sheep.getScoreboardTags();
		if (tags.contains("tier_2"))
		{
			minAmount += 1;
			maxAmount += 1;
		}
		else if (tags.contains("tier_3"))
		{
			minAmount += 2;
			maxAmount += 3;
		}

		int amount = Method.random(minAmount, maxAmount);
		itemStack.setAmount(amount);
		return itemStack;
	}

	@Override
	public void run()
	{
		Bukkit.getScheduler().runTaskTimer(YouPeopleGame.getPlugin(), () ->
		{
			for (Player player : cooldowns.keySet())
			{
				if (!cooldowns.containsKey(player))
				{
					continue;
				}
				Cooldown cooldown = cooldowns.get(player);
				long current = cooldown.getCurrent();
				if (current == 0)
				{
					player.sendActionBar(Component.empty());
					if (results.containsKey(player))
					{
						AddItemUtil.addItem(player, results.get(player));
						cooldowns.remove(player);
					}
					else
					{
						MessageUtil.sendWarn(player, "오류 발생");
					}
				}
				else
				{
					long max = cooldown.getMax();
					player.sendActionBar(ComponentUtil.translate("양털 깎는 중 (%s%%)",
							df.format(100 - 100d * current / max)).color(TextColor.color(HSVLike.hsvLike(0.4f - 0.3f * current / max, 1f, 1f))));
				}
				cooldown.tick();
			}
		}, 0L, 1L);
	}
}
