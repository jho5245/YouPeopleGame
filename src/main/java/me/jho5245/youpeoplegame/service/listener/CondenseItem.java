package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.custom.custommaterial.CustomMaterial;
import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class CondenseItem
{
	private final Map<Object, CondenseMap> CONDENSE_MAP;

	private static final CondenseItem instance = new CondenseItem();

	private CondenseItem()
	{
		CONDENSE_MAP = new HashMap<>();
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.DAMP_COOKIE_PILE, new CondenseMap(CustomMaterialYouPeopleGame.DAMP_COOKIE));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_STONE, new CondenseMap(Material.COBBLESTONE));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.BURNING_FIRE, new CondenseMap(Material.COAL));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.COPPER_PILE, new CondenseMap(Material.RAW_COPPER));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.IRON_PILE, new CondenseMap(Material.RAW_IRON));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.GOLD_PILE, new CondenseMap(Material.RAW_GOLD));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.GENERATOR_MATERIAL, new CondenseMap(Material.REDSTONE));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.MAGIC_MATTER, new CondenseMap(Material.LAPIS_LAZULI));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.REFINED_QUARTZ, new CondenseMap(Material.QUARTZ));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_OAK_WOOD, new CondenseMap(Material.OAK_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_DARK_OAK_WOOD, new CondenseMap(Material.DARK_OAK_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HIGH_QUALITY_STICK, new CondenseMap(Material.SPRUCE_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_ACACIA_WOOD, new CondenseMap(Material.ACACIA_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_JUNGLE_WOOD, new CondenseMap(Material.JUNGLE_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_BIRCH_WOOD, new CondenseMap(Material.BIRCH_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_PALE_OAK_WOOD, new CondenseMap(Material.PALE_OAK_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_CHERRY_WOOD, new CondenseMap(Material.CHERRY_LOG));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.HARD_MANGROVE_WOOD, new CondenseMap(Material.MANGROVE_LOG));

		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.DIRT_PILE, new CondenseMap(Material.DIRT));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.RED_SAND_PILE, new CondenseMap(Material.RED_SAND));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.MUD_PILE, new CondenseMap(Material.MUD));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.GRAVEL_PILE, new CondenseMap(Material.GRAVEL));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.CLAY_PILE, new CondenseMap(Material.CLAY));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.MOSS_PILE, new CondenseMap(Material.MOSS_BLOCK));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.PALE_MOSS_PILE, new CondenseMap(Material.PALE_MOSS_BLOCK));
		CONDENSE_MAP.put(CustomMaterialYouPeopleGame.SNOW_PILE, new CondenseMap(Material.SNOW_BLOCK));
		CONDENSE_MAP.put(Material.GLASS_BOTTLE, new CondenseMap(List.of(
				Pair.of(Material.SAND, 64),
				Pair.of(CustomMaterialYouPeopleGame.BURNING_FIRE, 1)
		)));
	}

	public static CondenseItem get()
	{
		return instance;
	}

	public static Map<Object, CondenseMap> getCondenseMap()
	{
		return get().CONDENSE_MAP;
	}

	public void listen(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		Action action = event.getAction();
		EquipmentSlot hand = event.getHand();
		Object material = customMaterial != null ? customMaterial : item.getType();
		if (action.isRightClick() && CONDENSE_MAP.containsKey(material))
		{
			CondenseMap condenseMap = CONDENSE_MAP.get(material);
			condense(player, condenseMap.from, material, condenseMap.toAmount);
			if (hand != null)
				player.swingHand(hand);
		}
	}

	/**
	 *
	 * @param player
	 * 		플레이어
	 * @param from
	 * 		바꿀 아이템 map
	 * @param to
	 * 		결과 아이템(+ 손에 들고 있는 아이템)
	 * @param toAmount
	 * 		결과 아이템 개수
	 */
	private void condense(Player player, List<Pair<Object, Integer>> from, Object to, int toAmount)
	{
		PlayerInventory inventory = player.getInventory();
		List<ItemStack> fromItemStack = convert(from);
		ItemStack toItemStack = _convert(to, toAmount);
		boolean hasEnough = true;
		for (ItemStack itemStack : fromItemStack)
		{
			if (ItemStackUtil.countItem(inventory, itemStack) < itemStack.getAmount())
			{
				hasEnough = false;
				break;
			}
		}
		if (hasEnough)
		{
			fromItemStack.forEach(inventory::removeItem);
			AddItemUtil.addItem(player, toItemStack);
		}
	}

	public List<ItemStack> convert(Object o)
	{
		return switch (o)
		{
			case Pair<?, ?> pair ->
			{
				try
				{
					Object from = pair.getLeft();
					int left = (int) pair.getRight();
					yield Collections.singletonList(_convert(from, left));
				}
				catch (Exception e)
				{
					throw new IllegalArgumentException("pair is not pair<object, integer>", e);
				}
			}
			case List<?> list ->
			{
				List<ItemStack> result = new ArrayList<>();
				list.forEach(element -> result.add(convert(element).getFirst()));
				yield result;
			}
			default -> throw new IllegalArgumentException("only pair or list allowed");
		};
	}

	private ItemStack _convert(Object o, int amount)
	{
		return switch (o)
		{
			case ItemStack itemStack -> itemStack;
			case Material material -> new ItemStack(material, amount);
			case CustomMaterial customMaterial -> customMaterial.create(amount, false);
			default ->
			{
				throw new IllegalArgumentException("Invalid from");
			}
		};
	}

	public static class CondenseMap
	{
		public List<Pair<Object, Integer>> from;

		public int toAmount;

		public CondenseMap(Object from)
		{
			this(from, 64, 1);
		}

		public CondenseMap(Object from, int fromAmount, int toAmount)
		{
			this(List.of(Pair.of(from, fromAmount)), toAmount);
		}

		public CondenseMap(List<Pair<Object, Integer>> from)
		{
			this(from, 1);
		}

		public CondenseMap(List<Pair<Object, Integer>> from, int toAmount)
		{
			this.from = from;
			this.toAmount = toAmount;
		}
	}
}
