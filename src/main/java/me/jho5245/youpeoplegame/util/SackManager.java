package me.jho5245.youpeoplegame.util;

import com.jho5245.cucumbery.util.additemmanager.AddItemUtil;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.component.util.ItemNameUtil;
import com.jho5245.cucumbery.custom.custommaterial.CustomMaterial;
import com.jho5245.cucumbery.util.storage.data.EnumHideable;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.custommaterial.CustomMaterialYouPeopleGame;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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
		COAL(Material.COAL, Category.MINING),
		RAW_COPPER(Material.RAW_COPPER, Category.MINING),
		RAW_IRON(Material.RAW_IRON, Category.MINING),
		RAW_GOLD(Material.RAW_GOLD, Category.MINING),
		REDSTONE(Material.REDSTONE, Category.MINING),
		LAPIS_LAZULI(Material.LAPIS_LAZULI, Category.MINING),
		QUARTZ(Material.QUARTZ, Category.MINING),
		DIAMOND(Material.DIAMOND, Category.MINING),
		EMERALD(Material.EMERALD, Category.MINING),
		AMEYTHST_SHARD(Material.AMETHYST_SHARD, Category.MINING),
		ANCIENT_DEBRIS(Material.ANCIENT_DEBRIS, Category.MINING),

		DAMP_COOKIE(CustomMaterialYouPeopleGame.DAMP_COOKIE, Category.COOKIE),
		DAMP_COOKIE_PILE(CustomMaterialYouPeopleGame.DAMP_COOKIE_PILE, Category.COOKIE),
		DAMP_COOKIE_EVEN(CustomMaterialYouPeopleGame.DAMP_COOKIE_EVEN, Category.COOKIE),
		CRISPY_COOKIE(CustomMaterialYouPeopleGame.CRISPY_COOKIE, Category.COOKIE),
		CRISPY_COOKIE_BOX(CustomMaterialYouPeopleGame.CRISPY_COOKIE_BOX, Category.COOKIE),
		MOIST_COOKIE(CustomMaterialYouPeopleGame.MOIST_COOKIE, Category.COOKIE),

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
		MOSS_BLOCK(Material.MOSS_BLOCK, Category.DIRT),
		PALE_MOSS_BLOCK(Material.PALE_MOSS_BLOCK, Category.DIRT),
		SNOW_BLOCK(Material.SNOW_BLOCK, Category.DIRT),

		MEDAL_OF_PARKOUR(CustomMaterialYouPeopleGame.MEDAL_OF_PARKOUR, Category.CURRENCY),
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
			FORCE("--force"),

			COMBAT("전투"),
			MINING("채광"),
			COOKIE("쿠키"),
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
				return this.name().toLowerCase();
			}

			public String getName()
			{
				return this.name;
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
				if (customMaterial == CustomMaterialYouPeopleGame.SACK_EXPANDER_MINING)
					return Category.MINING;
				if (customMaterial == CustomMaterialYouPeopleGame.SACK_EXPANDER_COOKIE)
					return Category.COOKIE;
				if (customMaterial == CustomMaterialYouPeopleGame.SACK_EXPANDER_WOOD)
					return Category.WOOD;
				if (customMaterial == CustomMaterialYouPeopleGame.SACK_EXPANDER_DIRT)
					return Category.DIRT;
				if (customMaterial == CustomMaterialYouPeopleGame.SACK_EXPANDER_CURRENCY)
					return Category.CURRENCY;
				return null;
			}

			@Override
			public boolean isHiddenEnum()
			{
				return this == FORCE;
			}

			public ItemStack getDisplayItemStack()
			{
				Material material = switch (this)
				{
					case COMBAT -> Material.ROTTEN_FLESH;
					case MINING -> Material.DIAMOND;
					case COOKIE -> Material.COOKIE;
					case WOOD -> Material.OAK_WOOD;
					case DIRT -> Material.DIRT;
					case CURRENCY -> Material.MUSIC_DISC_13;
					default -> Material.STONE;
				};
				ItemStack itemStack = new ItemStack(Material.DEBUG_STICK);
				ItemMeta itemMeta = itemStack.getItemMeta();
				var dataComponent = itemMeta.getCustomModelDataComponent();
				dataComponent.setStrings(List.of("youpeoplegame_gui_sack_category_" + this.name().toLowerCase()));
				itemMeta.setCustomModelDataComponent(dataComponent);
				itemMeta.setEnchantmentGlintOverride(false);
				itemMeta.displayName(ComponentUtil.translate("&e%s 재료 가방", this.getName()));
				itemMeta.setItemModel(material.getKey());
				itemStack.setItemMeta(itemMeta);
				return itemStack;
			}
		}
	}

	private String getPath(Category category)
	{
		return YouPeopleGameUserData.MATERIAL_STORAGE_CONTENTS + "." + category;
	}

	private String getPath(SackElement sackElement)
	{
		return getPath(sackElement.category) + ".아이템-목록." + sackElement.name().toLowerCase();
	}

	public int getCategoryUnlockCount(Player player, Category category)
	{
		return UserData.getInt(player, getPath(category) + ".확장-횟수");
	}

	public void setCategoryUnlockCount(Player player, Category category, int unlockCount)
	{
		UserData.set(player, getPath(category) + ".확장-횟수", unlockCount);
	}

	public int getAmount(Player player, SackElement element)
	{
		return UserData.getInt(player, getPath(element) + ".amount");
	}

	public void setAmount(Player player, SackElement element, int amount)
	{
		UserData.set(player, getPath(element) + ".amount", amount);
	}

	public int getMaxAmount(Player player, SackElement element)
	{
		return UserData.getInt(player, getPath(element) + ".max-amount");
	}

	public void setMaxAmount(Player player, SackElement element, int amount)
	{
		UserData.set(player, getPath(element) + ".max-amount", amount);
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
		MessageUtil.info(player, "재료 가방에 %s을(를) %s개 보관했습니다. (%s / %s)", itemStack, itemStackAmount, getAmount(player, element), getMaxAmount(player, element));
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
			MessageUtil.sendError(player, "재료 가방에 아이템이 부족합니다. (%s / %s)", amount, getMaxAmount(player, element));
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
		MessageUtil.info(player, "재료 가방에서 %s을(를) %s개 꺼냈습니다. (%s / %s)", itemStack, itemStackAmount, getAmount(player, element), getMaxAmount(player, element));
		return true;
	}

	// 주머니 누적 확장에 따른 다음 최대 보관 가능 수치. -1일 경우 최대 업그레이드
	public int getNextMaxAmount(Player player, SackElement element)
	{
		int categoryUnlockCount = getCategoryUnlockCount(player, element.getCategory());
		switch (element)
		{
			case DAMP_COOKIE ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 640;
					case 1 -> 1280;
					case 2 -> 2000;
					case 3 -> 3200;
					default -> -1;
				};
			}
			case DAMP_COOKIE_PILE ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 64;
					case 1 -> 128;
					case 2 -> 200;
					case 3 -> 320;
					default -> -1;
				};
			}
			case DAMP_COOKIE_EVEN, CRISPY_COOKIE_BOX, MOIST_COOKIE ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 12;
					case 1 -> 20;
					case 2 -> 32;
					case 3 -> 64;
					default -> -1;
				};
			}
			case CRISPY_COOKIE ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 320;
					case 1 -> 640;
					case 2 -> 1000;
					case 3 -> 1600;
					default -> -1;
				};
			}
			case COBBLESTONE, OAK_LOG, DIRT ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 640;
					case 1 -> 1280;
					case 2 -> 2000;
					case 3 -> 5000;
					case 4 -> 20000;
					case 5 -> 50000;
					default -> -1;
				};
			}
			case COAL, SPRUCE_LOG, SAND ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 480;
					case 1 -> 960;
					case 2 -> 2000;
					case 3 -> 5000;
					case 4 -> 20000;
					case 5 -> 50000;
					default -> -1;
				};
			}
			case RAW_COPPER, REDSTONE, LAPIS_LAZULI ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 320;
					case 1 -> 640;
					case 2 -> 1280;
					case 3 -> 2000;
					case 4 -> 5000;
					case 5 -> 20000;
					default -> -1;
				};
			}
			case RAW_IRON, DARK_OAK_LOG, RED_SAND ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 240;
					case 1 -> 480;
					case 2 -> 960;
					case 3 -> 2000;
					case 4 -> 5000;
					case 5 -> 20000;
					default -> -1;
				};
			}
			case RAW_GOLD, QUARTZ, ACACIA_LOG, JUNGLE_LOG, BIRCH_LOG, PALE_OAK_LOG, MUD, GRAVEL, CLAY ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 160;
					case 1 -> 320;
					case 2 -> 640;
					case 3 -> 1280;
					case 4 -> 2000;
					case 5 -> 5000;
					default -> -1;
				};
			}
			case DIAMOND ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 32;
					case 1 -> 64;
					case 2 -> 128;
					case 3 -> 320;
					case 4 -> 640;
					case 5 -> 1280;
					default -> -1;
				};
			}
			case EMERALD, CRIMSON_STEM, WARPED_STEM, SNOW_BLOCK ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 16;
					case 1 -> 32;
					case 2 -> 64;
					case 3 -> 128;
					case 4 -> 320;
					case 5 -> 640;
					default -> -1;
				};
			}
			case AMEYTHST_SHARD, CHERRY_LOG, MANGROVE_LOG, MOSS_BLOCK, PALE_MOSS_BLOCK ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 64;
					case 1 -> 128;
					case 2 -> 320;
					case 3 -> 640;
					case 4 -> 1280;
					case 5 -> 2000;
					default -> -1;
				};
			}
			case ANCIENT_DEBRIS, BAMBOO_BLOCK ->
			{
				return switch (categoryUnlockCount)
				{
					case 0 -> 2;
					case 1 -> 4;
					case 2 -> 10;
					case 3 -> 32;
					case 4 -> 64;
					case 5 -> 128;
					default -> -1;
				};
			}
		}
		return -1;
	}
}
