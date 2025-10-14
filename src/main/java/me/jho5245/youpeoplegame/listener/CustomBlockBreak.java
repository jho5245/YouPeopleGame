package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.block.CustomBlockBreakEvent;
import me.jho5245.youpeoplegame.YouPeopleGame;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockBreak implements Listener
{
	private final List<Pair<Material, Double>> ORE_CHANCE = new ArrayList<>();

	public CustomBlockBreak()
	{
		List<Pair<Material, Double>> oreChance = new ArrayList<>();
		oreChance.add(Pair.of(Material.STONE, 100d));
		oreChance.add(Pair.of(Material.COAL_ORE, 25d));
		oreChance.add(Pair.of(Material.COPPER_ORE, 16d));
		oreChance.add(Pair.of(Material.IRON_ORE, 10d));
		oreChance.add(Pair.of(Material.GOLD_ORE, 8d));
		oreChance.add(Pair.of(Material.REDSTONE_ORE, 5d));
		oreChance.add(Pair.of(Material.LAPIS_ORE, 5d));
		oreChance.add(Pair.of(Material.NETHER_QUARTZ_ORE, 5d));
		oreChance.add(Pair.of(Material.DIAMOND_ORE, 1d));
		oreChance.add(Pair.of(Material.EMERALD_ORE, 0.5d));
		oreChance.add(Pair.of(Material.AMETHYST_CLUSTER, 0.1d));
		oreChance.add(Pair.of(Material.ANCIENT_DEBRIS, 0.01d));

		// 가중치를 백분율로 치환
		double sum = oreChance.stream().mapToDouble(Pair::getRight).sum();
		oreChance.forEach(pair -> this.ORE_CHANCE.add(Pair.of(pair.getLeft(), pair.getRight() / sum)));
	}

	@EventHandler
	public void onBlockBreak(CustomBlockBreakEvent event)
	{
		event.setApplyPhysics(false);
		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () ->
		{
			event.getBlock().setType(getMaterial(), false);
		}, 0L);
	}

	private Material getMaterial()
	{
		final double pivot = Math.random();
		double acc = 0;
		for (Pair<Material, Double> pair : ORE_CHANCE)
		{
			acc += pair.getRight();
			if (pivot <= acc)
			{
				return pair.getLeft();
			}
		}
		throw new RuntimeException("No material found!");
	}
}
