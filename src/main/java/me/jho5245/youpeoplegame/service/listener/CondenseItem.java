package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

import static com.jho5245.cucumbery.util.storage.data.CustomMaterial.*;

public class CondenseItem
{
	private final Map<Object, CondenseMap> CONDENSE_MAP;

	private static final CondenseItem instance = new CondenseItem();

	private CondenseItem()
	{
		CONDENSE_MAP = new HashMap<>();
		CONDENSE_MAP.put(YOUPEOPLEGAME_DAMP_COOKIE_PILE, new CondenseMap(YOUPEOPLEGAME_DAMP_COOKIE));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_STONE, new CondenseMap(Material.COBBLESTONE));
		CONDENSE_MAP.put(YOUPEOPLEGAME_BURNING_FIRE, new CondenseMap(Material.COAL));
		CONDENSE_MAP.put(YOUPEOPLEGAME_COPPER_PILE, new CondenseMap(Material.RAW_COPPER));
		CONDENSE_MAP.put(YOUPEOPLEGAME_IRON_PILE, new CondenseMap(Material.RAW_IRON));
		CONDENSE_MAP.put(YOUPEOPLEGAME_GOLD_PILE, new CondenseMap(Material.RAW_GOLD));
		CONDENSE_MAP.put(YOUPEOPLEGAME_GENERATOR_MATERIAL, new CondenseMap(Material.REDSTONE));
		CONDENSE_MAP.put(YOUPEOPLEGAME_MAGIC_MATTER, new CondenseMap(Material.LAPIS_LAZULI));
		CONDENSE_MAP.put(YOUPEOPLEGAME_REFINED_QUARTZ, new CondenseMap(Material.QUARTZ));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_OAK_WOOD, new CondenseMap(Material.OAK_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_DARK_OAK_WOOD, new CondenseMap(Material.DARK_OAK_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HIGH_QUALITY_STICK, new CondenseMap(Material.SPRUCE_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_ACACIA_WOOD, new CondenseMap(Material.ACACIA_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_JUNGLE_WOOD, new CondenseMap(Material.JUNGLE_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_BIRCH_WOOD, new CondenseMap(Material.BIRCH_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_PALE_OAK_WOOD, new CondenseMap(Material.PALE_OAK_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_CHERRY_WOOD, new CondenseMap(Material.CHERRY_LOG));
		CONDENSE_MAP.put(YOUPEOPLEGAME_HARD_MANGROVE_WOOD, new CondenseMap(Material.MANGROVE_LOG));
		CONDENSE_MAP.put(Material.GLASS_BOTTLE, new CondenseMap(List.of(
				Pair.of(Material.SAND, 64),
				Pair.of(YOUPEOPLEGAME_BURNING_FIRE, 1)
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
