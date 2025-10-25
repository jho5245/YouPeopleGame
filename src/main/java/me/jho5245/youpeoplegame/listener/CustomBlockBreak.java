package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.custom.customeffect.custom_mining.MiningManager;
import com.jho5245.cucumbery.custom.customeffect.custom_mining.MiningResult;
import com.jho5245.cucumbery.custom.customeffect.custom_mining.MiningScheduler;
import com.jho5245.cucumbery.events.block.CustomBlockBreakEvent;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.data.Prefix;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CustomBlockBreak implements Listener
{
	private final List<Pair<Material, Double>> ORE_CHANCE = new ArrayList<>(), WOOD_CHANCE = new ArrayList<>(), DIRT_CHANCE = new ArrayList<>();

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
		convertWeight(oreChance, this.ORE_CHANCE);

		List<Pair<Material, Double>> woodChance = new ArrayList<>();
		woodChance.add(Pair.of(Material.OAK_LOG, 100d));
		woodChance.add(Pair.of(Material.SPRUCE_LOG, 38d));
		woodChance.add(Pair.of(Material.DARK_OAK_LOG, 20d));
		woodChance.add(Pair.of(Material.ACACIA_LOG, 15d));
		woodChance.add(Pair.of(Material.JUNGLE_LOG, 13d));
		woodChance.add(Pair.of(Material.BIRCH_LOG, 11d));
		woodChance.add(Pair.of(Material.PALE_OAK_LOG, 9d));
		woodChance.add(Pair.of(Material.CHERRY_LOG, 7d));
		woodChance.add(Pair.of(Material.MANGROVE_LOG, 5d));
		woodChance.add(Pair.of(Material.CRIMSON_STEM, 3d));
		woodChance.add(Pair.of(Material.WARPED_STEM, 1d));
		woodChance.add(Pair.of(Material.BAMBOO_BLOCK, 0.1d));

		convertWeight(woodChance, this.WOOD_CHANCE);

		List<Pair<Material, Double>> dirtChance = new ArrayList<>();
		dirtChance.add(Pair.of(Material.DIRT, 100d));
		dirtChance.add(Pair.of(Material.SAND, 38d));
		dirtChance.add(Pair.of(Material.RED_SAND, 20d));
		dirtChance.add(Pair.of(Material.MUD, 15d));
		dirtChance.add(Pair.of(Material.GRAVEL, 10d));
		dirtChance.add(Pair.of(Material.CLAY, 8d));
		dirtChance.add(Pair.of(Material.MOSS_BLOCK, 6d));
		dirtChance.add(Pair.of(Material.PALE_MOSS_BLOCK, 4d));
		dirtChance.add(Pair.of(Material.SNOW_BLOCK, 1d));
		convertWeight(dirtChance, this.DIRT_CHANCE);
	}

	private void convertWeight(List<Pair<Material, Double>> origin, List<Pair<Material, Double>> target)
	{
		double sum = origin.stream().mapToDouble(Pair::getRight).sum();
		origin.forEach(pair -> target.add(Pair.of(pair.getLeft(), pair.getRight() / sum)));
	}

	@EventHandler
	public void onBlockBreak(CustomBlockBreakEvent event)
	{
		event.setApplyPhysics(false);
		Player player = event.getPlayer();
		Location blockLocation = event.getBlock().getLocation();
		Material blockType = event.getBlock().getType();
		if (blockType == Material.ANCIENT_DEBRIS || blockType == Material.BAMBOO_BLOCK)
		{
			MessageUtil.broadcastPlayer(Prefix.INFO, "%s이(가) %s(을)를 획득하였습니다.", player, blockType);
		}
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regionManager = container.get(localPlayer.getWorld());
		com.sk89q.worldedit.util.Location worldEditLocation = BukkitAdapter.adapt(blockLocation);
		List<String> regionIds = new ArrayList<>();
		if (regionManager != null)
		{
			regionManager.getApplicableRegions(worldEditLocation.toVector().toBlockPoint()).forEach((region) -> regionIds.add(region.getId()));
		}
		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () ->
		{
			if (regionIds.contains("mine"))
				event.getBlock().setType(getMaterial(ORE_CHANCE), false);
			else if (regionIds.contains("mine2"))
				event.getBlock().setType(getMaterial(WOOD_CHANCE), false);
			else if (regionIds.contains("mine3"))
				event.getBlock().setType(getMaterial(DIRT_CHANCE), false);
		}, 0L);

		if (!event.isChain())
		{
			List<Location> locations = new ArrayList<>();
			double miningSpread = UserData.getDouble(player, YouPeopleGameUserData.MINING_SPREAD, 0);
			int max = (int) (miningSpread / 100) + 1;
			if (Math.random() * 100 < miningSpread % 100) max++;
			addLocation(locations, player, blockLocation, blockLocation, max);
			locations.remove(blockLocation);
			locations.forEach(location ->
			{
				MiningScheduler.breakingBlock(player, true, location);
			});
		}
	}

	public void addLocation(List<Location> locations, Player player, Location origin, Location nextLocation, int max)
	{
		if (locations.size() >= max)
			return;
		if (locations.contains(nextLocation))
			return;
		MiningResult result = MiningManager.getMiningInfo(player, nextLocation);
		if (result == null || result.blockHardness() == -1)
			return;
		locations.add(nextLocation);
		List<Location> newLocations = new ArrayList<>(
				Arrays.asList(new Location(nextLocation.getWorld(), nextLocation.getBlockX() - 1, nextLocation.getBlockY(), nextLocation.getBlockZ()),
						new Location(nextLocation.getWorld(), nextLocation.getBlockX() + 1, nextLocation.getBlockY(), nextLocation.getBlockZ()),
						new Location(nextLocation.getWorld(), nextLocation.getBlockX(), nextLocation.getBlockY() + 1, nextLocation.getBlockZ()),
						new Location(nextLocation.getWorld(), nextLocation.getBlockX(), nextLocation.getBlockY() - 1, nextLocation.getBlockZ()),
						new Location(nextLocation.getWorld(), nextLocation.getBlockX(), nextLocation.getBlockY(), nextLocation.getBlockZ() + 1),
						new Location(nextLocation.getWorld(), nextLocation.getBlockX(), nextLocation.getBlockY(), nextLocation.getBlockZ() - 1)));
		newLocations.sort(Comparator.comparingDouble(l -> l.distance(origin) * Math.random()));
		for (Location newLocation : newLocations)
		{
			addLocation(locations, player, origin, newLocation, max);
		}
	}

	private Material getMaterial(List<Pair<Material, Double>> oreChance)
	{
		final double pivot = Math.random();
		double acc = 0;
		for (Pair<Material, Double> pair : oreChance)
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
