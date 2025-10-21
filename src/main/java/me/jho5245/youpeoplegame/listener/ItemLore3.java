package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.itemlore.ItemLore3Event;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.component.util.ItemNameUtil;
import com.jho5245.cucumbery.util.storage.data.Constant;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.service.listener.CondenseItem;
import me.jho5245.youpeoplegame.service.listener.CondenseItem.CondenseMap;
import me.jho5245.youpeoplegame.service.scheduler.CookieGiveawayEveryNSeconds;
import me.jho5245.youpeoplegame.util.SackManager;
import me.jho5245.youpeoplegame.util.SackManager.SackElement;
import me.jho5245.youpeoplegame.util.SackManager.SackElement.Category;
import me.jho5245.youpeoplegame.util.YouPeopleGameUserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemLore3 implements Listener
{
	public static final Set<InventoryType> TOGGLE_ITEM_LORE_ALLOWED_INVENTORY = new HashSet<>(List.of(InventoryType.CRAFTING, InventoryType.CHEST));

	@EventHandler
	public void onItemLore3(ItemLore3Event event)
	{
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItemStack();
		List<Component> lore = event.getItemLore();

		CustomMaterial customMaterial = CustomMaterial.itemStackOf(itemStack);
		switch (customMaterial)
		{
			case YOUPEOPLEGAME_DAMP_COOKIE ->
			{
				int waitTick = player != null ? CookieGiveawayEveryNSeconds.getWaitTick(player) : 100;
				lore.add(ComponentUtil.translate("&7%s초를 기다려 얻은 쿠키다.", Constant.Sosu2.format(waitTick / 20d)));
				lore.add(ComponentUtil.translate("&7어딘가 좀 눅눅하다."));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_PILE ->
			{
				lore.add(ComponentUtil.translate("&7뭉쳐서 이름이 굵어졌다."));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_EVEN ->
			{
				lore.add(ComponentUtil.translate("&7이젠 문쳐서 보기만 해도 목이 막힐 지경이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE ->
			{
				lore.add(ComponentUtil.translate("&7눅눅한 쿠키를 익혀 바삭하게 만든 쿠키다."));
			}
			case YOUPEOPLEGAME_STARTER_SWORD, YOUPEOPLEGAME_STARTER_SHOVEL ->
			{
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s이다.", customMaterial.translationKey()));
			}
			case YOUPEOPLEGAME_STARTER_AXE, YOUPEOPLEGAME_STARTER_PICKAXE ->
			{
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s다.", customMaterial.translationKey()));
			}
			case YOUPEOPLEGAME_BURNING_FIRE ->
			{
				lore.add(ComponentUtil.translate("&7다용도로 필요해질 것 같은 불이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_PICKAXE ->
			{
				lore.add(ComponentUtil.translate("&7key:item.youpeoplegame.crispy_cookie_pickaxe.description|바삭한 쿠키로 만든 곡괭이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_AXE ->
			{
				lore.add(ComponentUtil.translate("&7바삭한 쿠키로 만든 도끼다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_SHOVEL ->
			{
				lore.add(ComponentUtil.translate("&7바삭한 쿠키로 만든 삽이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_BOX ->
			{
				lore.add(ComponentUtil.translate("&7바삭한 쿠키가 1셋트 들어있는 박스다."));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE ->
			{
				lore.add(ComponentUtil.translate("&7눅눅함과 바삭함이 만나 촉촉한 쿠키가 되었다!"));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_POTION ->
			{
				lore.add(ComponentUtil.translate("&7더욱 눅눅한 쿠키를 액화하여 만든 이상한 포션이다."));
				lore.add(ComponentUtil.translate("&7사용시 잠수자리에서 영구적으로 25%확률로 쿠키를 하나 더 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
				lore.add(ComponentUtil.translate("&7한 번 사용하면 사라진다."));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER ->
			{
				lore.add(ComponentUtil.translate("&7촉촉한 쿠키를 액화하여 만든 이상한 촉진제다."));
				lore.add(ComponentUtil.translate("&7사용 시 잠수자리에서 영구적으로 1초를 줄여 더 빠르게 쿠키를 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
				lore.add(ComponentUtil.translate("&7한 번 사용하면 사라진다."));
			}
			case YOUPEOPLEGAME_HARD_STONE ->
			{
				lore.add(ComponentUtil.translate("&7조약돌을 압축시켜 단단하게 만든 돌이다."));
			}
			case YOUPEOPLEGAME_IRON_PILE ->
			{
				lore.add(ComponentUtil.translate("&7철 원석을 뭉친 철뭉치다."));
			}
			case YOUPEOPLEGAME_GOLD_PILE ->
			{
				lore.add(ComponentUtil.translate("&7금 원석을 뭉친 금뭉치다."));
			}
			case YOUPEOPLEGAME_COPPER_PILE ->
			{
				lore.add(ComponentUtil.translate("&7구리 원석을 뭉친 구리뭉치다."));
			}
			case YOUPEOPLEGAME_GENERATOR_MATERIAL ->
			{
				lore.add(ComponentUtil.translate("&7기계에 사용되는 발전물체이다."));
			}
			case YOUPEOPLEGAME_MAGIC_MATTER ->
			{
				lore.add(ComponentUtil.translate("&7신비한 마력을 지닌 마력물질이다."));
			}
			case YOUPEOPLEGAME_REFINED_QUARTZ ->
			{
				lore.add(ComponentUtil.translate("&7석영을 가공하여 만들었다."));
			}
			case YOUPEOPLEGAME_SUPER_MOIST_COOKIE_BOOSTER ->
			{
				lore.add(ComponentUtil.translate("&7눅눅한 쿠키 포션을 촉촉한 쿠키로 고농축화하여 만들어진 퓨전 포션이다."));
				lore.add(ComponentUtil.translate("&7사용 시 잠수자리에서 영구적으로 1.25초를 줄여 더 빠르게 쿠키를 얻을 수 있으며,"));
				lore.add(ComponentUtil.translate("&750% 확률로 쿠키를 하나 더 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
				lore.add(ComponentUtil.translate("&7한 번 사용하면 사라진다."));
			}
			case YOUPEOPLEGAME_REFINED_HARD_STONE ->
			{
				lore.add(ComponentUtil.translate("&7단단한 돌을 구워 만들어진 돌이다."));
			}
			case YOUPEOPLEGAME_REFINED_IRON ->
			{
				lore.add(ComponentUtil.translate("&7철뭉치 구워 만들어진 철이다."));
			}
			case YOUPEOPLEGAME_REFINED_GOLD ->
			{
				lore.add(ComponentUtil.translate("&7금뭉치 구워 만들어진 금이다."));
			}
			case YOUPEOPLEGAME_REFINED_COPPER ->
			{
				lore.add(ComponentUtil.translate("&7구리뭉치 구워 만들어진 구리다."));
			}
			case YOUPEOPLEGAME_REFINED_DIAMOND, YOUPEOPLEGAME_REFINED_EMERALD, YOUPEOPLEGAME_REFINED_AMETHYST ->
			{
				lore.add(ComponentUtil.translate("&7%1$s을(를) 구워 만들어 강력해진 %1$s이다.", Component.translatable(customMaterial.translationKey())));
			}
			case YOUPEOPLEGAME_HARD_OAK_WOOD, YOUPEOPLEGAME_HARD_DARK_OAK_WOOD, YOUPEOPLEGAME_HARD_ACACIA_WOOD, YOUPEOPLEGAME_HARD_JUNGLE_WOOD,
					 YOUPEOPLEGAME_HARD_BIRCH_WOOD, YOUPEOPLEGAME_HARD_PALE_OAK_WOOD, YOUPEOPLEGAME_HARD_CHERRY_WOOD, YOUPEOPLEGAME_HARD_MANGROVE_WOOD ->
			{
				lore.add(ComponentUtil.translate("&7%s을(를) 압축하여 단단하게 만들었다.", Component.translatable(customMaterial.getDisplayMaterial().translationKey())));
			}
			case YOUPEOPLEGAME_HIGH_QUALITY_STICK ->
			{
				lore.add(ComponentUtil.translate("&7엄선된 나무로만 가공되어 만들어진 막대기이다."));
				lore.add(ComponentUtil.translate("&7주로 도구의 손잡이로 사용된다."));
			}
			case YOUPEOPLEGAME_MEDAL_OF_PARKOUR ->
			{
				lore.add(ComponentUtil.translate("&7점프맵을 클리어 하여 얻은 메달이다."));
				lore.add(ComponentUtil.translate("&7다양한 아이템들과 교환할 수 있다."));
			}
			case YOUPEOPLEGAME_REFINED_ANCIENT_ORE ->
			{
				lore.add(ComponentUtil.translate("&7정체불명의 광물을 오랫동안 구워서 가공하였다."));
			}
			case null, default ->
			{
			}
		}
		switch (itemStack.getType())
		{
			case GLASS_BOTTLE ->
			{
				lore.add(ComponentUtil.translate("&7모래를 구워만든 유리로 장인의 손을 거쳐서 유리병이 되었다."));
			}
		}

		// 아이템 압축 기능 설명
		Object itemMaterial = customMaterial != null ? customMaterial : itemStack.getType();
		if (CondenseItem.getCondenseMap().containsKey(itemMaterial))
		{
			CondenseMap condenseMap = CondenseItem.getCondenseMap().get(itemMaterial);
			List<Pair<Object, Integer>> fromObject = condenseMap.from;
			int toAmount = condenseMap.toAmount;

			lore.add(Component.empty());
			StringBuilder s = new StringBuilder("&f손에 들고 %s 키를 누르면 ");
			List<Component> arguments = new ArrayList<>(List.of(Component.keybind("key.use", NamedTextColor.YELLOW)));
			for (Pair<Object, Integer> pair : fromObject)
			{
				int amount = pair.getRight();
				s = s.append("%s %s개, ");
				Component fromDisplay = switch (pair.getLeft())
				{
					case Material material -> ItemNameUtil.itemName(material);
					case CustomMaterial custom_material -> custom_material.getDisplayName();
					default -> throw new IllegalArgumentException("");
				};
				arguments.add(fromDisplay);
				arguments.add(ComponentUtil.create(player, amount));
			}
			lore.add(ComponentUtil.translate(s.substring(0, s.length() - 2) + "를", Component.keybind("key.use", NamedTextColor.YELLOW)).arguments(arguments));
			// CustomMaterial이 아닌 경우 표시 이름을 Material로
			Object displayName = customMaterial != null ? customMaterial.getDisplayName() : ItemNameUtil.itemName(itemStack.getType());
			lore.add(ComponentUtil.translate("&f즉시 %s %s개로 바꿀 수 있다.", displayName, toAmount));
		}

		// Sack
		{
			Category category = Category.getByCustomMaterial(customMaterial);
			if (category != null)
			{
				var sackElements = SackElement.getElementsByCategory(category);
				lore.add(ComponentUtil.translate("&7신비로운 힘이 느껴지는 주머니다."));
				lore.add(ComponentUtil.translate("&7무언가 보관할 수 있을것 같은 힘(?)이 느껴진다."));
				lore.add(Component.empty());
				lore.add(ComponentUtil.translate("&f손에 들고 %s 키를 눌러 사용하면", Component.keybind("key.use", NamedTextColor.YELLOW)));
				lore.add(ComponentUtil.translate("&f다음 목록에 있는 아이템의 최대 보관 용량이 100 증가한다.", Component.keybind("key.use", NamedTextColor.YELLOW)));
				lore.add(ComponentUtil.translate("&f한 번 사용하면 사라진다.", Component.keybind("key.use", NamedTextColor.YELLOW)));
				lore.add(Component.empty());
				for (int i = 0; i < sackElements.size(); i += 4)
				{
					StringBuilder builder = new StringBuilder();
					List<Component> arguments = new ArrayList<>();
					for (int j = i; j < Math.min(i + 4, sackElements.size()); j++)
					{
						builder.append("%s, ");
						Component itemStackNameComponent = sackElements.get(j).getItemStackNameComponent();
						if (itemStackNameComponent.color() == null)
							itemStackNameComponent = itemStackNameComponent.color(NamedTextColor.GREEN);
						arguments.add(itemStackNameComponent);
					}
					String key = builder.substring(0, builder.length() - 2);
					lore.add(ComponentUtil.translate("&7" + key).arguments(arguments));
				}

				lore.add(Component.empty());
				boolean hasUsedSack = player != null && UserData.getBoolean(player, YouPeopleGameUserData.SACK_USED);
				if (hasUsedSack)
				{
					boolean condition = TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType());
					if (condition)
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.SACK_AUTO_FILL))
						{
							lore.add(ComponentUtil.translate("&f자동 보관 : %s %s", "&a켜짐", "&8[8번 키로 끄기]"));
						}
						else
						{
							lore.add(ComponentUtil.translate("&f자동 보관 : %s %s", "&c꺼짐", "&8[8번 키로 켜기]"));
						}

					}
					else
					{
						if (UserData.getBoolean(player, YouPeopleGameUserData.SACK_AUTO_FILL))
						{
							lore.add(ComponentUtil.translate("&f자동 보관 : %s", "&a켜짐"));
						}
						else
						{
							lore.add(ComponentUtil.translate("&f자동 보관 : %s", "&c꺼짐"));
						}
					}
					lore.add(ComponentUtil.translate("&7자동 보관 기능을 사용하면"));
					lore.add(ComponentUtil.translate("&7블록을 부수거나 몹을 잡을 때 얻는 아이템이"));
					lore.add(ComponentUtil.translate("&7자동으로 보관함에 보관됩니다."));
					lore.add(ComponentUtil.translate("&7단, 보관함이 가득 차면 보관되지 않고"));
					lore.add(ComponentUtil.translate("&7기존 획득 경로(인벤토리)에 보관됩니다."));
					lore.add(Component.empty());
					lore.add(ComponentUtil.translate("&e[현재 보관 용량 정보]"));
					for (SackElement sackElement : sackElements)
					{
						int amount = SackManager.get().getAmount(player, sackElement), maxAmount = SackManager.get().getMaxAmount(player, sackElement);
						lore.add(ComponentUtil.translate("&f%s : %s / %s", sackElement.getItemStackNameComponent(), amount, maxAmount));
					}
				}
				else
				{
					lore.add(ComponentUtil.translate("&7사용 후 %s 명령어를 통해 아이템을 보관하거나 꺼낼 수 있다.", "&e/gfs 또는 /보관함"));
					lore.add(ComponentUtil.translate("&7보관함에 넣을 수 없는 아이템이거나,"));
					lore.add(ComponentUtil.translate("&7보관함이 가득찼을 경우 더 이상 아이템을 보관할 수 없다."));
					lore.add(Component.empty());
					lore.add(ComponentUtil.translate("&b[명령어 모음]"));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs", "&f1개 이상 보관 중인 모든 아이템의 보관 상태를 나열한다."));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs show [카테고리]", "&f해당하는 카테고리의 모든 아이템의 보관 상태를 나열한다."));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs store", "&f손에 들고 있는 아이템을 하나 보관한다."));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs store <아이템 이름> [개수]", "&f해당하는 아이템을 개수만큼 혹은 1개 보관한다."));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs get", "&f손에 들고 있는 아이템과 동일한 아이템을 하나 꺼낸다."));
					lore.add(ComponentUtil.translate("&7%s - %s", "&e/gfs get <아이템 이름> [개수]", "&f해당하는 아이템을 개수만큼 혹은 1개 꺼낸다."));
				}
			}
		}

		if (!lore.isEmpty() && player != null)
		{
			boolean condition = TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType());
			if (UserData.getBoolean(player, YouPeopleGameUserData.HIDE_ITEM_LORE_3))
			{
				if (condition)
				{
					lore.clear();
					lore.add(ComponentUtil.translate("&8[추가 설명] (9번 키로 펼치기) ▶"));
				}
			}
			else if (condition)
			{
				lore.addFirst(ComponentUtil.translate("&8[추가 설명] (9번 키로 줄이기) ▼"));
			}
		}
		addDebugLore(player, customMaterial, lore);
		event.setItemLore(lore);
	}

	private void addDebugLore(Player player, CustomMaterial customMaterial, List<Component> lore)
	{
		if (player != null && player.getName().equals("jho5245") && customMaterial != null)
		{
			lore.add(ComponentUtil.translate("&8custom_material:" + customMaterial.toString().toLowerCase()));
		}
	}
}
