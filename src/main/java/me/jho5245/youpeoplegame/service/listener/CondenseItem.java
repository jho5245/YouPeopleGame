package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.jho5245.cucumbery.util.storage.data.CustomMaterial.*;

public class CondenseItem
{
	public final Map<Object, CondenseMap> CONDENSE_MAP;

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
	}

	public static CondenseItem get()
	{
		return instance;
	}

	public void listen(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(item);
		Action action = event.getAction();
		EquipmentSlot hand = event.getHand();
		if (action.isRightClick() && customMaterial != null && CONDENSE_MAP.containsKey(customMaterial))
		{
			CondenseMap condenseMap = CONDENSE_MAP.get(customMaterial);
			condense(player, condenseMap.from, condenseMap.fromAmount, customMaterial, condenseMap.toAmount);
			if (hand != null)
				player.swingHand(hand);
		}
	}

	/**
	 *
	 * @param player
	 * 		플레이어
	 * @param from
	 * 		바꿀 아이템
	 * @param fromAmount
	 * 		바꿀 아이템 개수
	 * @param to
	 * 		결과 아이템(+ 손에 들고 있는 아이템)
	 * @param toAmount
	 * 		결과 아이템 개수
	 */
	private void condense(Player player, Object from, int fromAmount, Object to, int toAmount)
	{
		PlayerInventory inventory = player.getInventory();
		ItemStack fromItemStack = convert(from, fromAmount), toItemStack = convert(to, toAmount);
		if (ItemStackUtil.countItem(inventory, fromItemStack) >= fromAmount)
		{
			inventory.removeItem(fromItemStack);
			AddItemUtil.addItem(player, toItemStack);
		}
	}

	public ItemStack convert(Object o, int amount)
	{
		return switch (o)
		{
			case ItemStack itemStack -> itemStack;
			case Material material -> new ItemStack(material, amount);
			case CustomMaterial custom_material -> custom_material.create(amount, false);
			default ->
			{
				throw new IllegalArgumentException("Invalid from");
			}
		};
	}

	public static class CondenseMap implements Comparable<CondenseMap>
	{
		public Object from;

		public int fromAmount, toAmount;

		public CondenseMap(Object from)
		{
			this(from, 64, 1);
		}

		public CondenseMap(Object from, int fromAmount, int toAmount)
		{
			this.from = from;
			this.fromAmount = fromAmount;
			this.toAmount = toAmount;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(from, fromAmount, toAmount);
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			CondenseMap that = (CondenseMap) o;
			return this.from == that.from && this.fromAmount == that.fromAmount && this.toAmount == that.toAmount;
		}

		@Override
		public int compareTo(@NotNull CondenseMap o)
		{
			return from.toString().compareTo(o.from.toString());
		}
	}
}
