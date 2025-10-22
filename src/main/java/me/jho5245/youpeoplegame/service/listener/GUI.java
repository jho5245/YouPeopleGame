package me.jho5245.youpeoplegame.service.listener;

import com.jho5245.cucumbery.util.gui.GUIManager;
import com.jho5245.cucumbery.util.itemlore.ItemLore;
import com.jho5245.cucumbery.util.itemlore.ItemLoreView;
import com.jho5245.cucumbery.util.no_groups.MessageUtil;
import com.jho5245.cucumbery.util.no_groups.Method2;
import com.jho5245.cucumbery.util.shading.NBT;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import com.jho5245.cucumbery.util.storage.no_groups.ItemStackUtil;
import me.jho5245.youpeoplegame.YouPeopleGame;
import me.jho5245.youpeoplegame.command.GUICommand;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class GUI
{
	private static final GUI instance = new GUI();

	private GUI()
	{

	}

	public static GUI get()
	{
		return instance;
	}

	public void listen(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getView().getPlayer();
		Component title = event.getView().title();
		if (!GUIManager.isGUITitle(title))
			return;
		Inventory clickedInventory = event.getClickedInventory();
		if (clickedInventory == null || clickedInventory.getType() == InventoryType.PLAYER)
			return;
		String key = GUIManager.getGUIKey(event.getView().title());
		switch (key)
		{
			case GUICommand.COOKIE_GUI_KEY ->
			{
				boolean updated = false;
				switch (slot)
				{
					case 0 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.DAMP_COOKIE_POTION_USE);
							updated = true;
						}
					}
					case 1 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
					case 2 ->
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED))
						{
							UserData.setToggle(player, YouPeopleGameUserData.SUPER_MOIST_COOKIE_BOOSTER_USE);
							updated = true;
						}
					}
				}
				if (updated)
					GUICommand.openGUI(player);
			}
		}
		if (key.startsWith(SACK_GUI_KEY))
		{
			Category category = Method2.valueOf(key.substring(SACK_GUI_KEY.length()), Category.class);
			ItemStack clickedItem = event.getCurrentItem();
			if (!ItemStackUtil.itemExists(clickedItem))
				return;
			// 자동 보관 기능 토글
			if (slot == clickedInventory.getSize() - 8)
			{
				UserData.setToggle(player, YouPeopleGameUserData.SACK_AUTO_FILL);
			}
			// 보관한 용량 정보 표시 토글
			if (slot == clickedInventory.getSize() - 9)
			{
				UserData.setToggle(player, YouPeopleGameUserData.SACK_SHOW_CAPACITY_INFO);
			}
			if (category == null)
			{
				String clickedCategoryString = NBT.get(clickedItem, nbt ->
				{
					return nbt.getString("sack_category");
				});
				Category clickedCategory = Method2.valueOf(clickedCategoryString, Category.class);
				if (clickedCategory != null)
				{
					openSackGUI(player, clickedCategory);
					return;
				}
				if (slot == clickedInventory.getSize() - 6)
				{
					for (SackElement element : SackElement.values())
					{
						addAllItem(player, element);
					}
				}
				if (slot == clickedInventory.getSize() - 4)
				{
					for (SackElement element : SackElement.values())
					{
						takeAllItem(player, element);
					}
				}
				openSackGUI(player);
			}
			else
			{
				if (slot == clickedInventory.getSize() - 5)
				{
					openSackGUI(player);
					return;
				}
				String clickedElementString = NBT.get(clickedItem, nbt ->
				{
					return nbt.getString("sack_element");
				});
				SackElement clickedElement = Method2.valueOf(clickedElementString, SackElement.class);
				if (clickedElement != null)
				{
					ClickType clickType = event.getClick();
					ItemStack itemStack = clickedElement.getItemStack();
					switch (clickType)
					{
						// 1개 꺼내기
						case LEFT ->
						{
							SackManager.get().takeItem(player, clickedElement, 1);
						}
						// 64개 꺼내기
						case SHIFT_LEFT ->
						{
							int playerSpace = ItemStackUtil.countSpace(player.getInventory(), itemStack);
							int count = SackManager.get().getAmount(player, clickedElement);
							int takeAmount = Math.max(1, Math.min(64, Math.min(playerSpace, count)));
							SackManager.get().takeItem(player, clickedElement, takeAmount);
						}
						// 1개 보관
						case RIGHT ->
						{
							SackManager.get().addItem(player, clickedElement, 1);
						}
						// 64개 보관
						case SHIFT_RIGHT ->
						{
							int playerCount = ItemStackUtil.countItem(player.getInventory(), itemStack);
							int count = SackManager.get().getAmount(player, clickedElement);
							int maxCount = SackManager.get().getMaxAmount(player, clickedElement);
							int storeCount = Math.max(1, Math.min(64, Math.min(playerCount, maxCount - count)));
							SackManager.get().addItem(player, clickedElement, storeCount);
						}
					}
				}
				if (slot == clickedInventory.getSize() - 6)
				{
					for (SackElement element : SackElement.getElementsByCategory(category))
					{
						addAllItem(player, element);
					}
				}
				if (slot == clickedInventory.getSize() - 4)
				{
					for (SackElement element : SackElement.getElementsByCategory(category))
					{
						takeAllItem(player, element);
					}
				}
				openSackGUI(player, category);
			}
		}
	}

	private void takeAllItem(Player player, SackElement element)
	{
		ItemStack elementItem = element.getItemStack();
		int playerSpace = ItemStackUtil.countSpace(player.getInventory(), elementItem);
		int current = SackManager.get().getAmount(player, element);
		int amountToTake = Math.min(playerSpace, current);
		if (amountToTake <= 0)
			return;
		SackManager.get().takeItem(player, element, amountToTake);
	}

	private void addAllItem(Player player, SackElement element)
	{
		ItemStack elementItem = element.getItemStack();
		int playerCount = ItemStackUtil.countItem(player.getInventory(), elementItem);
		int current = SackManager.get().getAmount(player, element);
		int max = SackManager.get().getMaxAmount(player, element);
		int amountToStore = Math.min(playerCount, max - current);
		if (amountToStore <= 0)
			return;
		SackManager.get().addItem(player, element, amountToStore);
	}

	public static final String SACK_GUI_KEY = "SACK_GUI";

	private final ItemStack deco, getAll, putAll, backToMenu;

	{
		deco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta decoMeta = deco.getItemMeta();
		decoMeta.setHideTooltip(true);
		deco.setItemMeta(decoMeta);
	}

	{
		getAll = new ItemStack(Material.DEBUG_STICK);
		ItemMeta getAllMeta = getAll.getItemMeta();
		getAllMeta.setEnchantmentGlintOverride(false);
		getAllMeta.setItemModel(Material.HOPPER.getKey());
		CustomModelDataComponent getAllData = getAllMeta.getCustomModelDataComponent();
		getAllData.setStrings(List.of("youpeoplegame_gui_sack_get_all"));
		getAllMeta.setCustomModelDataComponent(getAllData);
		getAllMeta.displayName(ComponentUtil.translate("&e전부 꺼내기"));
		getAllMeta.lore(List.of(ComponentUtil.translate("&7아이템을 전부 꺼냅니다."), ComponentUtil.translate("&7인벤토리가 가득 찬 경우 꺼낼 수 없습니다.")));
		getAll.setItemMeta(getAllMeta);
	}

	{
		putAll = new ItemStack(Material.DEBUG_STICK);
		ItemMeta putAllMeta = putAll.getItemMeta();
		putAllMeta.setEnchantmentGlintOverride(false);
		putAllMeta.setItemModel(Material.CHEST.getKey());
		CustomModelDataComponent putAllData = putAllMeta.getCustomModelDataComponent();
		putAllData.setStrings(List.of("youpeoplegame_gui_sack_put_all"));
		putAllMeta.setCustomModelDataComponent(putAllData);
		putAllMeta.displayName(ComponentUtil.translate("&e전부 넣기"));
		putAllMeta.lore(List.of(ComponentUtil.translate("&7가지고 있는 아이템을"), ComponentUtil.translate("&7전부 보관함에 넣습니다.")));
		putAll.setItemMeta(putAllMeta);
	}

	{
		backToMenu = new ItemStack(Material.DEBUG_STICK);
		ItemMeta itemMeta = backToMenu.getItemMeta();
		itemMeta.setEnchantmentGlintOverride(false);
		itemMeta.setItemModel(Material.ARROW.getKey());
		CustomModelDataComponent dataComponent = itemMeta.getCustomModelDataComponent();
		dataComponent.setStrings(List.of("youpeoplegame_gui_sack_back_to_menu"));
		itemMeta.setCustomModelDataComponent(dataComponent);
		itemMeta.displayName(ComponentUtil.translate("&b처음 화면으로"));
		itemMeta.lore(List.of(ComponentUtil.translate("&7보관함 목록으로 돌아갑니다.")));
		backToMenu.setItemMeta(itemMeta);
	}

	public void openSackGUI(Player player)
	{
		Inventory inventory = GUIManager.create(3, ComponentUtil.translate("보관함"), SACK_GUI_KEY);
		for (Category category : Category.values())
		{
			if (category.isHiddenEnum())
				continue;
			int unlocked = SackManager.get().getCategoryUnlockCount(player, category);
			if (unlocked == 0)
				continue;
			ItemStack displayItem = category.getDisplayItemStack();
			ItemMeta meta = displayItem.getItemMeta();
			List<Component> lore = new ArrayList<>();
			lore.add(Component.empty());
			for (SackElement element : SackElement.getElementsByCategory(category))
			{
				int maxAmount = SackManager.get().getMaxAmount(player, element);
				if (maxAmount == 0)
					continue;
				int currentAmount = SackManager.get().getAmount(player, element);
				lore.add(ComponentUtil.translate("&7%s - %s / %s", element.getItemStackNameComponent(), currentAmount, maxAmount));
			}
			lore.add(Component.empty());
			lore.add(ComponentUtil.translate("&e클릭하여 %s 보관함 열기", category.getName()));
			meta.lore(lore);
			displayItem.setItemMeta(meta);

			NBT.modify(displayItem, nbt ->
			{
				nbt.setString("sack_category", category.name());
			});

			inventory.addItem(displayItem);
		}
		setFooter(player, inventory);
		if (inventory.getItem(0) == null)
		{
			MessageUtil.sendWarn(player, "아직 해금한 보관함이 없습니다.");
			return;
		}
		player.openInventory(inventory);
		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 0L);
	}

	private void setFooter(Player player, Inventory inventory)
	{
		for (int i = inventory.getSize() - 9; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, deco);
		}
		inventory.setItem(inventory.getSize() - 6, putAll);
		inventory.setItem(inventory.getSize() - 4, getAll);

		ItemStack toggleAutoFill = new ItemStack(Material.HOPPER);
		ItemMeta meta = toggleAutoFill.getItemMeta();
		meta.displayName(ComponentUtil.translate("&e자동 보관 기능"));
		List<Component> lore = new ArrayList<>();
		boolean using = UserData.getBoolean(player, YouPeopleGameUserData.SACK_AUTO_FILL);
		lore.add(Component.empty());
		lore.add(ComponentUtil.translate("&7자동 보관 기능을 사용하면"));
		lore.add(ComponentUtil.translate("&7블록을 부수거나 몹을 잡을 때 얻는 아이템이"));
		lore.add(ComponentUtil.translate("&7자동으로 보관함에 보관됩니다."));
		lore.add(ComponentUtil.translate("&7단, 보관함이 가득 차면 보관되지 않고"));
		lore.add(ComponentUtil.translate("&7기존 획득 경로(인벤토리)에 보관됩니다."));
		lore.add(Component.empty());
		lore.add(ComponentUtil.translate(using ? "&a클릭하여 기능 끄기" : "&c클릭하여 기능 켜기"));
		meta.lore(lore);
		toggleAutoFill.setItemMeta(meta);
		inventory.setItem(inventory.getSize() - 8, toggleAutoFill);

		ItemStack toggleTool = new ItemStack(Material.BIRCH_SIGN);
		ItemMeta toolItemMeta = toggleTool.getItemMeta();
		toolItemMeta.displayName(ComponentUtil.translate("&e주머니 확장 아이템의 보관 용량 정보 표시"));
		List<Component> toolLore = new ArrayList<>();
		boolean toolUsing = UserData.getBoolean(player, YouPeopleGameUserData.SACK_SHOW_CAPACITY_INFO);
		toolLore.add(Component.empty());
		toolLore.add(ComponentUtil.translate("&7주머니 확장 아이템을 구매할 때 현재 해금된 주머니의"));
		toolLore.add(ComponentUtil.translate("&7최대 보관 용량과 보관 중인 아이템의 개수를 구매할"));
		toolLore.add(ComponentUtil.translate("&7주머니 확장 아이템의 설명에서 확인할 수 있습니다."));
		toolLore.add(Component.empty());
		toolLore.add(ComponentUtil.translate(toolUsing ? "&a클릭하여 기능 끄기" : "&c클릭하여 기능 켜기"));
		toolItemMeta.lore(toolLore);
		toggleTool.setItemMeta(toolItemMeta);
		inventory.setItem(inventory.getSize() - 9, toggleTool);
	}

	public void openSackGUI(Player player, Category category)
	{
		Inventory inventory = GUIManager.create(6, ComponentUtil.translate("%s 보관함", category.getName()), SACK_GUI_KEY + category);
		List<SackElement> sackElements = SackElement.getElementsByCategory(category);
		for (SackElement sackElement : sackElements)
		{
			ItemStack itemStack = sackElement.getItemStack();
			ItemLore.setItemLore(itemStack, false, ItemLoreView.of(player));
			ItemMeta itemMeta = itemStack.getItemMeta();
			List<Component> lore = itemMeta.lore();
			if (lore == null)
				lore = new ArrayList<>();
			int currentAmount = SackManager.get().getAmount(player, sackElement);
			int maxAmount = SackManager.get().getMaxAmount(player, sackElement);
			lore.add(Component.empty());
			lore.add(ComponentUtil.translate("&7보관 개수 : %s / %s", currentAmount, maxAmount));
			lore.add(Component.empty());
			lore.add(ComponentUtil.translate("&e좌클릭 : 1개 꺼내기"));
			lore.add(ComponentUtil.translate("&e시프트 + 좌클릭 : 64개 꺼내기"));
			lore.add(ComponentUtil.translate("&e우클릭 : 1개 넣기"));
			lore.add(ComponentUtil.translate("&e시프트 우클릭 : 64개 넣기"));
			// make not to update lore
			lore.set(0, Component.text(" "));
			itemMeta.lore(lore);
			itemStack.setItemMeta(itemMeta);
			NBT.modify(itemStack, nbt ->
			{
				nbt.setString("sack_element", sackElement.name());
			});
			inventory.addItem(itemStack);
		}
		setFooter(player, inventory);
		inventory.setItem(inventory.getSize() - 5, backToMenu);
		player.openInventory(inventory);
		Bukkit.getScheduler().runTaskLater(YouPeopleGame.getPlugin(), () -> ItemStackUtil.updateInventory(player), 0L);
	}
}
