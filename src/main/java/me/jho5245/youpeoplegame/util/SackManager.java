package me.jho5245.youpeoplegame.util;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.component.util.ItemNameUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.data.EnumHideable;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class SackManager
{
	private static final SackManager instance = new SackManager();

	private SackManager()
	{

	}

	public static SackManager get()
	{
		return instance;
	}

	public enum SackElement
	{
		COBBLESTONE(Material.COBBLESTONE, Category.MINING),
		STONE(Material.STONE, Category.MINING),
		COAL(Material.COAL, Category.MINING),
		RAW_COPPER(Material.RAW_COPPER, Category.MINING),

		COPPER_INGOT(Material.COPPER_INGOT, Category.MINING),
		RAW_IRON(Material.RAW_IRON, Category.MINING),
		IRON_INGOT(Material.IRON_INGOT, Category.MINING),
		RAW_GOLD(Material.RAW_GOLD, Category.MINING),

		GOLD_INGOT(Material.GOLD_INGOT, Category.MINING),
		REDSTONE(Material.REDSTONE, Category.MINING),
		LAPIS_LAZULI(Material.LAPIS_LAZULI, Category.MINING),
		QUARTZ(Material.QUARTZ, Category.MINING),

		DIAMOND(Material.DIAMOND, Category.MINING),
		EMERALD(Material.EMERALD, Category.MINING),
		AMEYTHST_SHARD(Material.AMETHYST_SHARD, Category.MINING),
		ANCIENT_DEBRIS(Material.ANCIENT_DEBRIS, Category.MINING),

		DAMP_COOKIE(CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE, Category.COOKIE),
		DAMP_COOKIE_PILE(CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_PILE, Category.COOKIE),
		DAMP_COOKIE_EVEN(CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_EVEN, Category.COOKIE),
		CRISPY_COOKIE(CustomMaterial.YOUPEOPLEGAME_CRISPY_COOKIE, Category.CRISPY_COOKIE),
		CRISPY_COOKIE_BOX(CustomMaterial.YOUPEOPLEGAME_CRISPY_COOKIE_BOX, Category.CRISPY_COOKIE),

		OAK_LOG(Material.OAK_LOG, Category.WOOD),
		SPRUCE_LOG(Material.SPRUCE_LOG, Category.WOOD),
		BIRCH_LOG(Material.BIRCH_LOG, Category.WOOD),
		DARK_OAK_LOG(Material.DARK_OAK_LOG, Category.WOOD),

		ACACIA_LOG(Material.ACACIA_LOG, Category.WOOD),
		JUNGLE_LOG(Material.JUNGLE_LOG, Category.WOOD),
		PALE_OAK_LOG(Material.PALE_OAK_LOG, Category.WOOD),
		CHERRY_LOG(Material.CHERRY_LOG, Category.WOOD),

		MANGROVE_LOG(Material.MANGROVE_LOG, Category.WOOD),
		CRIMSON_STEM(Material.CRIMSON_STEM, Category.WOOD),
		WARPED_STEM(Material.WARPED_STEM, Category.WOOD),
		BAMBOO_BLOCK(Material.BAMBOO_BLOCK, Category.WOOD),

		DIRT(Material.DIRT, Category.DIRT),
		SAND(Material.SAND, Category.DIRT),
		RED_SAND(Material.RED_SAND, Category.DIRT),
		MUD(Material.MUD, Category.DIRT),

		GRAVEL(Material.GRAVEL, Category.DIRT),
		CLAY(Material.CLAY, Category.DIRT),
		CLAY_BALL(Material.CLAY_BALL, Category.DIRT),
		MOSS_BLOCK(Material.MOSS_BLOCK, Category.DIRT),

		PALE_MOSS_BLOCK(Material.PALE_MOSS_BLOCK, Category.DIRT),
		SNOW_BLOCK(Material.SNOW_BLOCK, Category.DIRT),
		SNOWBALL(Material.SNOWBALL, Category.DIRT),

		MEDAL_OF_PARKOUR(CustomMaterial.YOUPEOPLEGAME_MEDAL_OF_PARKOUR, Category.CURRENCY),
		;

		private final ItemStack element;

		private final Category category;

		SackElement(Material material, Category category)
		{
			this(new ItemStack(material), category);
		}

		SackElement(CustomMaterial customMaterial, Category category)
		{
			this(customMaterial.create(), category);
		}

		SackElement(ItemStack element, Category category)
		{
			this.element = element;
			this.category = category;
		}

		public ItemStack getItemStack()
		{
			return getItemStack(1);
		}

		public ItemStack getItemStack(int amount)
		{
			ItemStack itemStack = element.clone();
			itemStack.setAmount(amount);
			return itemStack;
		}

		public String toString()
		{
			return this.category.name().toLowerCase() + "." + this.name().toLowerCase();
		}

		public Component getItemStackNameComponent()
		{
			return ItemNameUtil.itemName(element);
		}

		public String getItemStackName()
		{
			return MessageUtil.stripColor(ComponentUtil.serialize(getItemStackNameComponent()));
		}

		public String getItemStackNameJson()
		{
			return ComponentUtil.serializeAsJson(getItemStackNameComponent());
		}

		public Category getCategory()
		{
			return category;
		}

		public static SackElement getByItemStack(ItemStack itemStack)
		{
			for (SackElement sackElement : SackElement.values())
			{
				if (ItemStackUtil.itemEquals(itemStack, sackElement.getItemStack()))
				{
					return sackElement;
				}
			}
			return null;
		}

		public static SackElement getByItemStackName(String name)
		{
			for (SackElement sackElement : SackElement.values())
			{
				if (sackElement.getItemStackName().equals(name))
				{
					return sackElement;
				}
			}
			return null;
		}

		public static List<SackElement> getElementsByCategory(Category category)
		{
			List<SackElement> elements = new ArrayList<>();
			for (SackElement sackElement : SackElement.values())
			{
				if (sackElement.getCategory().equals(category))
				{
					elements.add(sackElement);
				}
			}
			return elements;
		}

		public enum Category implements EnumHideable
		{
			/**
			 * internal use only
			 */
			FORCE("모두 보기"),

			COMBAT("전투"),
			MINING("채광"),
			COOKIE("쿠키"),
			CRISPY_COOKIE("바삭한 쿠키"),
			WOOD("나무"),
			DIRT("흙"),

			CURRENCY("재화");

			private final String name;

			Category(String name)
			{
				this.name = name;
			}

			@Override
			public String toString()
			{
				return name;
			}

			public static Category getByName(String name)
			{
				for (Category category : Category.values())
				{
					if (category.name.equals(name))
						return category;
				}
				return null;
			}

			public static Category getByCustomMaterial(CustomMaterial customMaterial)
			{
				return switch (customMaterial)
				{
					case YOUPEOPLEGAME_SACK_EXPANDER_MINING -> Category.MINING;
					case YOUPEOPLEGAME_SACK_EXPANDER_COOKIE -> Category.COOKIE;
					case YOUPEOPLEGAME_SACK_EXPANDER_WOOD -> Category.WOOD;
					case YOUPEOPLEGAME_SACK_EXPANDER_DIRT -> Category.DIRT;
					case YOUPEOPLEGAME_SACK_EXPANDER_CURRENCY -> Category.CURRENCY;
					case null, default -> null;
				};
			}

			@Override
			public boolean isHiddenEnum()
			{
				return this == FORCE;
			}
		}
	}

	public int getAmount(Player player, SackElement element)
	{
		return UserData.getInt(player, YouPeopleGameUserData.SACK_CONTENTS + "." + element + ".amount");
	}

	public void setAmount(Player player, SackElement element, int amount)
	{
		UserData.set(player, YouPeopleGameUserData.SACK_CONTENTS + "." + element + ".amount", amount);
	}

	public int getMaxAmount(Player player, SackElement element)
	{
		return UserData.getInt(player, YouPeopleGameUserData.SACK_CONTENTS + "." + element + ".max-amount");
	}

	public void setMaxAmount(Player player, SackElement element, int amount)
	{
		UserData.set(player, YouPeopleGameUserData.SACK_CONTENTS + "." + element + ".max-amount", amount);
	}

	public boolean addItem(Player player, SackElement element, int itemStackAmount)
	{
		PlayerInventory playerInventory = player.getInventory();
		ItemStack itemStack = element.getItemStack();
		itemStack.setAmount(itemStackAmount);
		int countItem = ItemStackUtil.countItem(playerInventory, itemStack);
		if (itemStackAmount <= 0)
		{
			MessageUtil.sendError(player, "보관할 아이템의 수량은 0보다 커야합니다.");
			return false;
		}
		if (countItem < itemStackAmount)
		{
			MessageUtil.sendError(player, "아이템 개수가 부족합니다. (소지 개수 : %s개)", countItem);
			return false;
		}
		int amount = getAmount(player, element);
		int maxAmount = getMaxAmount(player, element);
		if (amount + itemStack.getAmount() > maxAmount)
		{
			MessageUtil.sendError(player, "최대 용량 초과! - 추가로 %s개까지 보관 가능합니다. (%s / %s)", maxAmount - amount, amount, maxAmount);
			return false;
		}
		playerInventory.removeItem(itemStack);
		setAmount(player, element, amount + itemStackAmount);
		MessageUtil.info(player, "보관함에 %s을(를) %s개 보관했습니다. (%s / %s)", itemStack, itemStackAmount, getAmount(player, element), getMaxAmount(player, element));
		return true;
	}

	public boolean takeItem(Player player, SackElement element, int itemStackAmount)
	{
		int amount = getAmount(player, element);
		if (itemStackAmount <= 0)
		{
			MessageUtil.sendError(player, "꺼낼 아이템의 수량은 0보다 커야합니다.");
			return false;
		}
		if (amount < itemStackAmount)
		{
			MessageUtil.sendError(player, "보관함에 아이템이 부족합니다. (%s / %s)", amount, getMaxAmount(player, element));
			return false;
		}
		PlayerInventory playerInventory = player.getInventory();
		ItemStack itemStack = element.getItemStack(itemStackAmount);
		int countSpace = ItemStackUtil.countSpace(playerInventory, itemStack);
		if (countSpace < itemStackAmount)
		{
			MessageUtil.sendError(player, "인벤토리에 공간이 부족합니다. 최대 %s개만 꺼낼 수 있습니다.", countSpace);
			return false;
		}
		setAmount(player, element, amount - itemStackAmount);
		AddItemUtil.addItem(player, itemStack);
		MessageUtil.info(player, "보관함에서 %s을(를) %s개 꺼냈습니다.", itemStack, itemStackAmount);
		return true;
	}
}
