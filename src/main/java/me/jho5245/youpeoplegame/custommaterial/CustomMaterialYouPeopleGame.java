package me.jho5245.youpeoplegame.custommaterial;

import com.jho5245.cucumbery.custom.custommaterial.CustomMaterial;
import com.jho5245.cucumbery.util.storage.component.util.ComponentUtil;
import com.jho5245.cucumbery.util.storage.no_groups.ItemCategory.Rarity;
import me.jho5245.youpeoplegame.YouPeopleGame;
import net.kyori.adventure.text.format.ShadowColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class CustomMaterialYouPeopleGame
{
	private static final CustomMaterialYouPeopleGame instance = new CustomMaterialYouPeopleGame();

	public static CustomMaterialYouPeopleGame getInstance()
	{
		return instance;
	}

	private CustomMaterialYouPeopleGame()
	{
		
	}
	
	private static NamespacedKey of(String key)
	{
		return new NamespacedKey(YouPeopleGame.getPlugin(), key);
	}

	public static final CustomMaterial
	BURNING_FIRE = new CustomMaterial(of("burning_fire"), Material.CAMPFIRE, "&6key:item.youpeoplegame.burning_fire|타오르는 불", Material.FIRE.translationKey()),
	CHAOS_MATTER = new CustomMaterial(of("chaos_matter"), Material.WARPED_HYPHAE, "&1key:item.youpeoplegame.chaos_matter|혼돈물질", "혼돈"),
	CLAY_PILE = new CustomMaterial(of("clay_pile"), Material.TERRACOTTA, "&7key:item.youpeoplegame.clay_pile|뭉친 점토", "점토"),
	COPPER_PILE = new CustomMaterial(of("copper_pile"), Material.RAW_COPPER_BLOCK, "#9b563e;key:item.youpeoplegame.copper_pile|구리뭉치", "구리"),
	CORRUPTED_MATTER = new CustomMaterial(of("corrupted_matter"), Material.CRIMSON_HYPHAE, "&4key:item.youpeoplegame.corrupted_matter|오염물질", "오염"),
	CRISPY_COOKIE = new CustomMaterial(of("crispy_cookie"), Material.COOKIE, "&6key:item.youpeoplegame.crispy_cookie|바삭한 쿠키", "key:itemGroup.crispyCookie|바삭한 쿠키"),
	CRISPY_COOKIE_AXE = new CustomMaterial(of("crispy_cookie_axe"), Material.DIAMOND_AXE, null, ComponentUtil.translate("&6key:item.youpeoplegame.crispy_cookie_axe|바삭한 쿠키도끼").shadowColor(ShadowColor.shadowColor(-256)), Rarity.RARE, "key:itemGroup.crispyCookie|바삭한 쿠키"),
	CRISPY_COOKIE_BOX = new CustomMaterial(of("crispy_cookie_box"), Material.ORANGE_SHULKER_BOX, "&6key:item.youpeoplegame.crispy_cookie_box|바삭한 쿠키박스", "key:itemGroup.crispyCookie|바삭한 쿠키"),
	CRISPY_COOKIE_PICKAXE = new CustomMaterial(of("crispy_cookie_pickaxe"), Material.DIAMOND_PICKAXE, null, ComponentUtil.translate("&6key:item.youpeoplegame.crispy_cookie_pickaxe|바삭한 쿠키곡괭이").shadowColor(ShadowColor.shadowColor(-256)), Rarity.RARE, "key:itemGroup.crispyCookie|바삭한 쿠키"),
	CRISPY_COOKIE_SHOVEL = new CustomMaterial(of("crispy_cookie_shovel"), Material.DIAMOND_SHOVEL, null, ComponentUtil.translate("&6key:item.youpeoplegame.crispy_cookie_shovel|바삭한 쿠키삽").shadowColor(ShadowColor.shadowColor(-256)), Rarity.RARE, "key:itemGroup.crispyCookie|바삭한 쿠키"),
	CRISPY_COOKIE_SWORD = new CustomMaterial(of("crispy_cookie_sword"), Material.DIAMOND_SWORD, null, ComponentUtil.translate("&6key:item.youpeoplegame.crispy_cookie_sword|바삭한 쿠키검").shadowColor(ShadowColor.shadowColor(-256)), Rarity.RARE, "key:itemGroup.crispyCookie|바삭한 쿠키"),
	DAMP_COOKIE = new CustomMaterial(of("damp_cookie"), Material.COOKIE, "&6key:item.youpeoplegame.damp_cookie|눅눅한 쿠키", Rarity.JUNK, "key:itemGroup.dampCookie|눅눅한 쿠키"),
	DAMP_COOKIE_EVEN = new CustomMaterial(of("damp_cookie_even"), Material.COOKIE, "&6&lkey:item.youpeoplegame.damp_cookie_even|더욱 눅눅한 쿠키", "key:itemGroup.dampCookie|눅눅한 쿠키"),
	DAMP_COOKIE_PILE = new CustomMaterial(of("damp_cookie_pile"), Material.COOKIE, "&6&lkey:item.youpeoplegame.damp_cookie_pile|뭉친 눅눅한 쿠키", "key:itemGroup.dampCookie|눅눅한 쿠키"),
	DAMP_COOKIE_POTION = new CustomMaterial(of("damp_cookie_potion"), Material.HONEY_BOTTLE, "&6key:item.youpeoplegame.damp_cookie_potion|눅눅한 쿠키 포션", "key:itemGroup.potion|포션"),
	DIRT_PILE = new CustomMaterial(of("dirt_pile"), Material.COARSE_DIRT, "#C3712C;key:item.youpeoplegame.dirt_pile|뭉친 흙", "흙"),
	GENERATOR_MATERIAL = new CustomMaterial(of("generator_material"), Material.REDSTONE_TORCH, "&4key:item.youpeoplegame.generator_material|발전물체", "전기"),
	GOLD_PILE = new CustomMaterial(of("gold_pile"), Material.RAW_GOLD_BLOCK, "&ekey:item.youpeoplegame.gold_pile|금뭉치", "key:itemGroup.gold|금"),
	GRAVEL_PILE = new CustomMaterial(of("gravel_pile"), Material.DRIPSTONE_BLOCK, "&8key:item.youpeoplegame.gravel_pile|뭉친 자갈", "자갈"),
	HARD_ACACIA_WOOD = new CustomMaterial(of("hard_acacia_wood"), Material.ACACIA_WOOD, "#FF7F00;key:item.youpeoplegame.hard_acacia_wood|단단한 아카시아나무", "key:itemGroup.wood|나무"),
	HARD_BIRCH_WOOD = new CustomMaterial(of("hard_birch_wood"), Material.BIRCH_WOOD, "&7key:item.youpeoplegame.hard_birch_wood|단단한 자작나무", "key:itemGroup.wood|나무"),
	HARD_CHERRY_WOOD = new CustomMaterial(of("hard_cherry_wood"), Material.CHERRY_WOOD, "&dkey:item.youpeoplegame.hard_cherry_wood|단단한 벚나무", "key:itemGroup.wood|나무"),
	HARD_DARK_OAK_WOOD = new CustomMaterial(of("hard_dark_oak_wood"), Material.DARK_OAK_WOOD, "#553830;key:item.youpeoplegame.hard_dark_oak_wood|단단한 짙은 참나무", "key:itemGroup.wood|나무"),
	HARD_JUNGLE_WOOD = new CustomMaterial(of("hard_jungle_wood"), Material.JUNGLE_WOOD, "#E0B88A;key:item.youpeoplegame.hard_jungle_wood|단단한 정글나무", "key:itemGroup.wood|나무"),
	HARD_MANGROVE_WOOD = new CustomMaterial(of("hard_mangrove_wood"), Material.MANGROVE_WOOD, "&ckey:item.youpeoplegame.hard_mangrove_wood|단단한 맹그로브나무", "key:itemGroup.wood|나무"),
	HARD_OAK_WOOD = new CustomMaterial(of("hard_oak_wood"), Material.OAK_WOOD, "#553830;key:item.youpeoplegame.hard_oak_wood|단단한 참나무", "key:itemGroup.wood|나무"),
	HARD_PALE_OAK_WOOD = new CustomMaterial(of("hard_pale_oak_wood"), Material.PALE_OAK_WOOD, "&fkey:item.youpeoplegame.hard_jungle_wood|단단한 창백한 참나무", "key:itemGroup.wood|나무"),
	HARD_STONE = new CustomMaterial(of("hard_stone"), Material.STONE, "&7key:item.youpeoplegame.hard_stone|단단한 돌", "key:itemGroup.stone|돌"),
	HIGH_QUALITY_STICK = new CustomMaterial(of("high_quality_stick"), Material.STICK, "#553830;key:item.youpeoplegame.high_quality_stick|품질좋은 막대기", "key:itemGroup.wood|나무"),
	IRON_PILE = new CustomMaterial(of("iron_pile"), Material.RAW_IRON_BLOCK, "&fkey:item.youpeoplegame.iron_pile|철뭉치", "key:itemGroup.iron|철"),
	MAGIC_MATTER = new CustomMaterial(of("magic_matter"), Material.LAPIS_LAZULI, "&1key:item.youpeoplegame.magic_matter|마력물질", "마나"),
	MEDAL_OF_PARKOUR = new CustomMaterial(of("medal_of_parkour"), Material.MUSIC_DISC_13, "&ekey:item.youpeoplegame.medal_of_parkour|점프맵의 메달", "key:itemGroup.medal|메달"),
	MOIST_COOKIE = new CustomMaterial(of("moist_cookie"), Material.COOKIE, "&6key:item.youpeoplegame.moist_cookie|촉촉한 초코칩", "촉촉한 쿠키"),
	MOIST_COOKIE_BOOSTER = new CustomMaterial(of("moist_cookie_booster"), Material.HONEY_BOTTLE, "&6key:item.youpeoplegame.moist_cookie_booster|촉촉한 쿠키 촉진제", "포션"),
	MOSS_PILE = new CustomMaterial(of("moss_pile"), Material.GREEN_GLAZED_TERRACOTTA, "&akey:item.youpeoplegame.moss_pile|뭉친 이끼", "이끼"),
	MUD_PILE = new CustomMaterial(of("mud_pile"), Material.BLACKSTONE, "&0key:item.youpeoplegame.mud_pile|뭉친 진흙", "진흙"),
	PALE_MOSS_PILE = new CustomMaterial(of("pale_moss_pile"), Material.GRAY_GLAZED_TERRACOTTA, "&akey:item.youpeoplegame.pale_moss_pile|뭉친 창백한 이끼", "이끼"),
	RED_SAND_PILE = new CustomMaterial(of("red_sand_pile"), Material.SMOOTH_RED_SANDSTONE, "&6key:item.youpeoplegame.red_sand_pile|뭉친 붉은모래", "모래"),
	REFINED_AMETHYST = new CustomMaterial(of("refined_amethyst"), Material.AMETHYST_CLUSTER, "&5key:item.youpeoplegame.refined_amethyst|가공된 자수정", "key:itemGroup.amethyst|자수정"),
	REFINED_ANCIENT_ORE = new CustomMaterial(of("refined_ancient_ore"), Material.NETHERITE_BLOCK, ComponentUtil.translate("&8key:item.youpeoplegame.refined_ancient_ore|가공된 고대광물").shadowColor(ShadowColor.shadowColor(197, 199, 196, 255)), Rarity.RARE, "key:itemGraoup.ancient_ore|고대광물"),
	REFINED_COPPER = new CustomMaterial(of("refined_copper"), Material.COPPER_BLOCK, "#9b563e;key:item.youpeoplegame.refined_copper|가공된 구리", "key:itemGroup.copper|구리"),
	REFINED_DIAMOND = new CustomMaterial(of("refined_diamond"), Material.DIAMOND_BLOCK, "&bkey:item.youpeoplegame.refined_diamond|가공된 다이아몬드", "key:itemGroup.diamond|다이아몬드"),
	REFINED_EMERALD = new CustomMaterial(of("refined_emerald"), Material.EMERALD_BLOCK, "&akey:item.youpeoplegame.refined_emerald|가공된 에메랄드", "key:itemGroup.emerald|에메랄드"),
	REFINED_GOLD = new CustomMaterial(of("refined_gold"), Material.GOLD_BLOCK, "&ekey:item.youpeoplegame.refined_gold|가공된 금", "key:itemGroup.gold|금"),
	REFINED_HARD_STONE = new CustomMaterial(of("refined_hard_stone"), Material.SMOOTH_STONE, "&7key:item.youpeoplegame.refined_hard_stone|가공된 단단한 돌", "key:itemGroup.stone|돌"),
	REFINED_IRON = new CustomMaterial(of("refined_iron"), Material.IRON_BLOCK, "&fkey:item.youpeoplegame.refined_iron|가공된 철", "key:itemGroup.iron|철"),
	REFINED_QUARTZ = new CustomMaterial(of("refined_quartz"), Material.WHITE_STAINED_GLASS, "&fkey:item.youpeoplegame.refined_quartz|가공된 석영", "key:itemGraoup.quartz|석영"),
	SACK_EXPANDER_COOKIE = new CustomMaterial(of("sack_expander_cookie"), Material.ORANGE_BUNDLE, "&6key:item.youpeoplegame.sack_expander_cookie|쿠키 주머니 확장", Rarity.RARE, "주머니 확장"),
	SACK_EXPANDER_CURRENCY = new CustomMaterial(of("sack_expander_currency"), Material.PINK_BUNDLE, "&6key:item.youpeoplegame.sack_expander_currency|재화 주머니 확장", Rarity.RARE, "주머니 확장"),
	SACK_EXPANDER_DIRT = new CustomMaterial(of("sack_expander_dirt"), Material.BUNDLE, "&6key:item.youpeoplegame.sack_expander_dirt|흙 주머니 확장", Rarity.RARE, "주머니 확장"),
	SACK_EXPANDER_MINING = new CustomMaterial(of("sack_expander_mining"), Material.GRAY_BUNDLE, "&6key:item.youpeoplegame.sack_expander_mining|광물 주머니 확장", Rarity.RARE, "주머니 확장"),
	SACK_EXPANDER_WOOD = new CustomMaterial(of("sack_expander_wood"), Material.BROWN_BUNDLE, "&6key:item.youpeoplegame.sack_expander_wood|나무 주머니 확장", Rarity.RARE, "주머니 확장"),
	SNOW_PILE = new CustomMaterial(of("snow_pile"), Material.CALCITE, "&fkey:item.youpeoplegame.snow_pile|뭉친 눈", "눈"),
	SPIRIT_MATTER = new CustomMaterial(of("spirit_matter"), Material.BAMBOO_MOSAIC, ComponentUtil.translate("&ekey:item.youpeoplegame.spirit_matter|정령물질").shadowColor(ShadowColor.fromHexString("#4BF010FF")), Rarity.RARE, "정령"),
	STARTER_AXE = new CustomMaterial(of("starter_axe"), Material.IRON_AXE, null, Material.IRON_AXE.translationKey(), "스타터"),
	STARTER_PICKAXE = new CustomMaterial(of("starter_pickaxe"), Material.IRON_PICKAXE, null, Material.IRON_PICKAXE.translationKey(), "스타터"),
	STARTER_SHOVEL = new CustomMaterial(of("starter_shovel"), Material.IRON_SHOVEL, null, Material.IRON_SHOVEL.translationKey(), "스타터"),
	STARTER_SWORD = new CustomMaterial(of("starter_sword"), Material.IRON_SWORD, null, Material.IRON_SWORD.translationKey(), "스타터"),
	SUPER_MOIST_COOKIE_BOOSTER = new CustomMaterial(of("super_moist_cookie_booster"), Material.HONEY_BOTTLE, "&6key:item.youpeoplegame.super_moist_cookie_booster|촉촉한 쿠키 포션", "포션"),
	SPECIAL_TOOL = new CustomMaterial(of("special_tool"), Material.CLOCK, "key:item.youpeoplegame.special_tool|특별 도구(로딩중)", "key:itemGroup.youpeoplegame.special_tool|특별 도구"),
	TEST_ITEM = new CustomMaterial(of("test_item"), Material.APPLE, "key:item.youpeoplegame.test_item|테스트 아이템 2", Rarity.RARE, "key:itemGroup.youpeoplegame_item_group|테스트 그룹");

	public void registerCustomMaterial()
	{
		CustomMaterial.register(
				BURNING_FIRE, 
				CHAOS_MATTER, 
				CLAY_PILE, 
				COPPER_PILE, 
				CORRUPTED_MATTER, 
				CRISPY_COOKIE, 
				CRISPY_COOKIE_AXE,
				CRISPY_COOKIE_BOX, 
				CRISPY_COOKIE_PICKAXE,
				CRISPY_COOKIE_SHOVEL,
				CRISPY_COOKIE_SWORD,
				DAMP_COOKIE, 
				DAMP_COOKIE_EVEN, 
				DAMP_COOKIE_PILE, 
				DAMP_COOKIE_POTION, 
				DIRT_PILE, 
				GENERATOR_MATERIAL, 
				GOLD_PILE, 
				GRAVEL_PILE, 
				HARD_ACACIA_WOOD, 
				HARD_BIRCH_WOOD, 
				HARD_CHERRY_WOOD, 
				HARD_DARK_OAK_WOOD, 
				HARD_JUNGLE_WOOD, 
				HARD_MANGROVE_WOOD, 
				HARD_OAK_WOOD, 
				HARD_PALE_OAK_WOOD, 
				HARD_STONE, 
				HIGH_QUALITY_STICK, 
				IRON_PILE, 
				MAGIC_MATTER, 
				MEDAL_OF_PARKOUR, 
				MOIST_COOKIE, 
				MOIST_COOKIE_BOOSTER, 
				MOSS_PILE, 
				MUD_PILE, 
				PALE_MOSS_PILE, 
				RED_SAND_PILE, 
				REFINED_AMETHYST, 
				REFINED_ANCIENT_ORE,
				REFINED_COPPER, 
				REFINED_DIAMOND, 
				REFINED_EMERALD, 
				REFINED_GOLD, 
				REFINED_HARD_STONE, 
				REFINED_IRON, 
				REFINED_QUARTZ, 
				SACK_EXPANDER_COOKIE, 
				SACK_EXPANDER_CURRENCY, 
				SACK_EXPANDER_DIRT, 
				SACK_EXPANDER_MINING, 
				SACK_EXPANDER_WOOD, 
				SNOW_PILE, 
				SPIRIT_MATTER,
				STARTER_AXE, 
				STARTER_PICKAXE, 
				STARTER_SHOVEL, 
				STARTER_SWORD, 
				SUPER_MOIST_COOKIE_BOOSTER,
						SPECIAL_TOOL,
						TEST_ITEM
		);
	}

	public void unregisterCustomMaterial()
	{
		CustomMaterial.unregister(YouPeopleGame.getPlugin());
	}
}
