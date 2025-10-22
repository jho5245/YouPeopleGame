package me.jho5245.youpeoplegame.util;

public enum YouPeopleGameUserData
{
	DAMP_COOKIE_POTION_UNLOCKED("기능.눅눅한-쿠키-포션.해금됨"),
	DAMP_COOKIE_POTION_USE("기능.눅눅한-쿠키-포션.사용-여부"),
	MOIST_COOKIE_BOOSTER_UNLOCKED("기능.촉촉한-쿠키-촉진제.해금됨"),
	MOIST_COOKIE_BOOSTER_USE("기능.촉촉한-쿠키-촉진제.사용-여부"),
	SUPER_MOIST_COOKIE_BOOSTER_UNLOCKED("기능.촉촉한-쿠키-포션.해금됨"),
	SUPER_MOIST_COOKIE_BOOSTER_USE("기능.촉촉한-쿠키-포션.사용-여부"),
	SACK_USED("보관함.최초-사용-여부"),
	SACK_CONTENTS("보관함.내용"),
	SACK_AUTO_FILL("보관함.자동-보관-여부"),
	SACK_SHOW_CAPACITY_INFO("보관함.용량-정보-표시-여부"),
	BACKBACK_UNLOCKED_INDEX("가방.정보.최대-해금-번호"),
	MINING_SPREAD("추가-채굴-블록-배율"),
	HIDE_ITEM_LORE_3("추가-설명-숨김-여부");

	final String key;

	YouPeopleGameUserData(String key)
	{
		this.key = key;
	}

	public String toString()
	{
		return "YouPeopleGame." + key;
	}
}
