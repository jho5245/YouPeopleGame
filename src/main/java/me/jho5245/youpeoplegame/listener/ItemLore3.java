package me.jho5245.youpeoplegame.listener;

import com.jho5245.cucumbery.events.itemlore.ItemLore3Event;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.data.CustomMaterial;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemLore3 implements Listener
{
	@EventHandler
	public void onItemLore3(ItemLore3Event event)
	{
		ItemStack itemStack = event.getItemStack();
		List<Component> lore = event.getItemLore();
		CustomMaterial customMaterial = CustomMaterial.itemStackOf(itemStack);
		switch (customMaterial)
		{
			case YOUPEOPLEGAME_DAMP_COOKIE -> {
				lore.add(ComponentUtil.translate("&75초를 기다려 얻은 쿠키다."));
				lore.add(ComponentUtil.translate("&7어딘가 좀 눅눅하다."));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_PILE -> {
				lore.add(ComponentUtil.translate("&7뭉쳐서 이름이 굵어졌다."));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_EVEN -> {
				lore.add(ComponentUtil.translate("&7이젠 문쳐서 보기만 해도 목이 막힐 지경이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE -> {
				lore.add(ComponentUtil.translate("&7눅눅한 쿠키를 익혀 바삭하게 만든 쿠키다."));
			}
			case YOUPEOPLEGAME_STARTER_SWORD, YOUPEOPLEGAME_STARTER_SHOVEL -> {
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s이다", customMaterial.translationKey()));
			}
			case YOUPEOPLEGAME_STARTER_AXE, YOUPEOPLEGAME_STARTER_PICKAXE -> {
				lore.add(ComponentUtil.translate("&7뉴비에게 지급하는 %s다", customMaterial.translationKey()));
			}
			case YOUPEOPLEGAME_BURNING_FIRE -> {
				lore.add(ComponentUtil.translate("&7다용도로 필요해질 것 같은 불이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_PICKAXE -> {
				lore.add(ComponentUtil.translate("&7바삭한 쿠키로 만든 곡괭이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_AXE -> {
				lore.add(ComponentUtil.translate("&7바삭한 쿠키로 만든 도끼다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_SHOVEL -> {
				lore.add(ComponentUtil.translate("&7바삭한 쿠키로 만든 삽이다."));
			}
			case YOUPEOPLEGAME_CRISPY_COOKIE_BOX -> {
				lore.add(ComponentUtil.translate("&7바삭한 쿠키가 1셋트 들어있는 박스다."));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE -> {
				lore.add(ComponentUtil.translate("&7눅눅함과 바삭함이 만나 촉촉한 쿠키가 되었다!"));
			}
			case YOUPEOPLEGAME_DAMP_COOKIE_POTION -> {
				lore.add(ComponentUtil.translate("&7더욱 눅눅한 쿠키를 액화하여 만든 이상한 포션이다."));
				lore.add(ComponentUtil.translate("&7사용시 잠수자리에서 영구적으로 25%확률로 쿠키를 하나 더 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
			}
			case YOUPEOPLEGAME_MOIST_COOKIE_BOOSTER -> {
				lore.add(ComponentUtil.translate("&7촉촉한 쿠키를 액화하여 만든 이상한 촉진제다."));
				lore.add(ComponentUtil.translate("&7사용 시 잠수자리에서 영구적으로 1초를 줄여 더 빠르게 쿠키를 얻을 수 있다."));
				lore.add(ComponentUtil.translate("&7단, 딱 한 번만 사용할 수 있다."));
			}
			case YOUPEOPLEGAME_HARD_STONE -> {
				lore.add(ComponentUtil.translate("&7조약돌을 압축시켜 단단하게 만든 돌이다."));
			}
			case YOUPEOPLEGAME_IRON_PILE -> {
				lore.add(ComponentUtil.translate("&7철 원석을 뭉친 철뭉치다."));
			}
			case YOUPEOPLEGAME_GOLD_PILE -> {
				lore.add(ComponentUtil.translate("&7금 원석을 뭉친 금뭉치다."));
			}
			case YOUPEOPLEGAME_COPPER_PILE -> {
				lore.add(ComponentUtil.translate("&7구리 원석을 뭉친 구리뭉치다."));
			}
			case YOUPEOPLEGAME_GENERATOR_MATERIAL -> {
				lore.add(ComponentUtil.translate("&7기계에 사용되는 발전물체이다."));
			}
			case YOUPEOPLEGAME_MAGIC_MATTER -> {
				lore.add(ComponentUtil.translate("&7신비한 마력을 지닌 마력물질이다."));
			}
			case YOUPEOPLEGAME_REFINED_QUARTZ -> {
				lore.add(ComponentUtil.translate("&7석영을 가공하여 만들었다."));
			}
			case null, default -> {}
		}
		switch (itemStack.getType())
		{
			case GLASS_BOTTLE -> {
				lore.add(ComponentUtil.translate("&7모래를 구워만든 유리로 장인의 손을 거쳐서 유리병이 되었다."));
			}
		}
		event.setItemLore(lore);
	}
}
