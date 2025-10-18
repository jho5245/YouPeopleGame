package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.itemlore.ItemLore3Event;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.Constant;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import com.jho5245.cucumbery.util.storage.no_groups.CustomConfig.UserData;
import me.jho5245.youpeoplegame.YouPeopleGame.YouPeopleGameUserData;
import me.jho5245.youpeoplegame.service.CookieGivewayEveryNSeconds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

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
				int waitTick = player != null ? CookieGivewayEveryNSeconds.getWaitTick(player) : 100;
				lore.add(ComponentUtil.translate("&7%s초를 기다려 얻은 쿠키다.", Constant.Sosu2.format(waitTick / 20d)));
				lore.add(ComponentUtil.translate("&7어딘가 좀 눅눅하다."));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_PILE ->
			{
				lore.add(ComponentUtil.translate("&7뭉쳐서 이름이 굵어졌다."));
				lore.add(Component.empty());
				lore.add(ComponentUtil.translate("&f손에 들고 %s 키를 누르면 %s 64개를", Component.keybind("key.use", NamedTextColor.YELLOW),
						CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE.getDisplayName()));
				lore.add(ComponentUtil.translate("&f즉시 %s 1개로 바꿀 수 있다.", CustomMaterial.YOUPEOPLEGAME_DAMP_COOKIE_PILE.getDisplayName()));
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
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s이다", customMaterial.translationKey()));
			}
			case YOUPEOPLEGAME_STARTER_AXE, YOUPEOPLEGAME_STARTER_PICKAXE ->
			{
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s다", customMaterial.translationKey()));
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
			}
			case YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER ->
			{
				lore.add(ComponentUtil.translate("&7촉촉한 쿠키를 액화하여 만든 이상한 촉진제다."));
				lore.add(ComponentUtil.translate("&7사용 시 잠수자리에서 영구적으로 1초를 줄여 더 빠르게 쿠키를 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
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
			case YOUPEOPLEGAME_REFINED_DIAMOND ->
			{
				lore.add(ComponentUtil.translate("&7다이아몬드 구워 만들어 강력해진 다이아몬드이다."));
			}
			case YOUPEOPLEGAME_REFINED_EMERALD ->
			{
				lore.add(ComponentUtil.translate("&7에메랄드 구워 만들어 강력해진 에메랄드이다."));
			}
			case YOUPEOPLEGAME_REFINED_AMETHYST ->
			{
				lore.add(ComponentUtil.translate("&7자수정 구워 만들어 강력해진 자수정이다."));
			}
			case YOUPEOPLEGAME_HARD_OAK_WOOD -> {
				lore.add(ComponentUtil.translate("&7참나무를 압축하여 단단하게 만들었다."));
			}
			case YOUPEOPLEGAME_HARD_DARK_OAK_WOOD -> {
				lore.add(ComponentUtil.translate("&7짙은 참나무를 압축하여 단단하게 만들었다."));
			}
			case YOUPEOPLEGAME_HIGH_QUALITY_STICK -> {
				lore.add(ComponentUtil.translate("&7엄선된 나무로만 가공되어 만들어진 막대기이다."));
				lore.add(ComponentUtil.translate("&7주로 도구의 손잡이로 사용된다."));
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
		if (!lore.isEmpty() && player != null)
		{
			boolean condition = TOGGLE_ITEM_LORE_ALLOWED_INVENTORY.contains(player.getOpenInventory().getType());
			if (!UserData.getBoolean(player, YouPeopleGameUserData.ITEM_LORE_3))
			{
				if (condition)
					event.setItemLore(List.of(ComponentUtil.translate("&89번 키로 설명 켜기")));
				return;
			}
			else if (condition)
			{
				lore.addFirst(ComponentUtil.translate("&89번 키로 설명 끄기"));
			}
		}
		event.setItemLore(lore);
	}
}
