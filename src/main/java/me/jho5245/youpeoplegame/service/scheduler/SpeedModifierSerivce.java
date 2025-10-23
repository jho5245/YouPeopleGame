package me.jho5245.youpeoplegame.service.scheduler;

import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;

public class SpeedModifierSerivce extends SchedulerService
{
	public static final NamespacedKey SPEED_MODIFIER_KEY = new NamespacedKey(YouPeopleGame.getPlugin(), "speed_modifier");

	private static final SpeedModifierSerivce instance = new SpeedModifierSerivce();

	private SpeedModifierSerivce() {}

	public static SpeedModifierSerivce get()
	{
		return instance;
	}

	public double getSpeedModifier(Player player)
	{
		if (player.getScoreboardTags().contains("playing_parkour"))
		{
			return 0d;
		}
		return UserData.getDouble(player, YouPeopleGameUserData.STAT_SPEED);
	}

	@Override
	public void run()
	{
		Bukkit.getScheduler().runTaskTimer(YouPeopleGame.getPlugin(), () -> {
			for (Player player : Bukkit.getOnlinePlayers())
			{
				AttributeInstance attributeInstance = player.getAttribute(Attribute.MOVEMENT_SPEED);
				if (attributeInstance == null)
					continue;
				double speed = getSpeedModifier(player);
				AttributeModifier modifier = attributeInstance.getModifier(SPEED_MODIFIER_KEY);
				if (modifier == null)
				{
					if (speed > 0)
					{
						attributeInstance.addModifier(new AttributeModifier(SPEED_MODIFIER_KEY, speed, Operation.ADD_SCALAR));
					}
				}
				else
				{
					if (speed == 0)
					{
						attributeInstance.removeModifier(SPEED_MODIFIER_KEY);
					}
					else if (modifier.getAmount() != speed)
					{
						attributeInstance.removeModifier(SPEED_MODIFIER_KEY);
						attributeInstance.addModifier(new AttributeModifier(SPEED_MODIFIER_KEY, speed, Operation.ADD_SCALAR));
					}
				}
			}
		}, 0L, 1L);
	}
}
