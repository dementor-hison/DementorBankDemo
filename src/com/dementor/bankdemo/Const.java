package com.dementor.bankdemo;

import android.graphics.Color;

public class Const {
	
	public static final int BANK_INDEX_CARD_SS = 0;
	public static final int BANK_INDEX_HN = BANK_INDEX_CARD_SS + 1;
	public static final int BANK_INDEX_IBK = BANK_INDEX_HN + 1;
	public static final int BANK_INDEX_JB = BANK_INDEX_IBK + 1;
	public static final int BANK_INDEX_KB = BANK_INDEX_JB + 1;
	public static final int BANK_INDEX_KEB = BANK_INDEX_KB + 1;
	public static final int BANK_INDEX_KJ = BANK_INDEX_KEB + 1;
	public static final int BANK_INDEX_KN = BANK_INDEX_KJ + 1;
	public static final int BANK_INDEX_NH = BANK_INDEX_KN + 1;
	public static final int BANK_INDEX_SH = BANK_INDEX_NH + 1;
	public static final int BANK_INDEX_SUH = BANK_INDEX_SH + 1;
	public static final int BANK_INDEX_WR = BANK_INDEX_SUH + 1;
	public static final int BANK_INDEX_E = BANK_INDEX_WR + 1;
	public static final int BANK_INDEX_CHINA = BANK_INDEX_E + 1;
	public static final int BANK_INDEX_PAYNOW = BANK_INDEX_CHINA + 1;
	public static final int BANK_INDEX_ETEX = BANK_INDEX_CHINA + 1;
	
	public static final int CI_ICON_COUNT[] = {5, 6, 7, 0, 8, 7, 5, 0, 3, 6, 0, 5, 0, 25, 0, 9};
	//test
	public static final int COLORS_HIGHLIGHT[] = {
		Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), 
		Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151),
		Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(0, 145, 228), 
		Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151), Color.rgb(109, 195, 151)};
	
	public static final int BANK_ICON[] = {R.drawable.icon_sscard, R.drawable.icon_hn, R.drawable.icon_ibk, R.drawable.icon_jb, R.drawable.icon_kb,
		R.drawable.icon_keb, R.drawable.icon_kj, R.drawable.icon_kn, R.drawable.icon_nh, R.drawable.icon_sh, 
		R.drawable.icon_suh, R.drawable.icon_wr, R.drawable.icon_e, R.drawable.icon_china, R.drawable.icon_paynow, R.drawable.icon_etex};
	
	public static final int BANK_INTRO[] = {R.drawable.bg_card_ss1, R.drawable.bg_hn1, R.drawable.bg_ibk1, R.drawable.bg_jb1, R.drawable.bg_kb1,
		R.drawable.bg_keb1, R.drawable.bg_kj1, R.drawable.bg_kn1, R.drawable.bg_nh1, R.drawable.bg_sh1, 0, R.drawable.bg_wr1,
		R.drawable.bg_wr1, R.drawable.bg_alipay1, R.drawable.bg_paynow1, R.drawable.bg_etex1};
	
	public static final int BANK_AUTH[] = {R.drawable.bg_card_ss2, R.drawable.bg_hn2, R.drawable.bg_ibk2, R.drawable.bg_jb2, R.drawable.bg_kb2,
		R.drawable.bg_keb2, R.drawable.bg_kj2, R.drawable.bg_kn2, R.drawable.bg_nh2, R.drawable.bg_sh2, 0, R.drawable.bg_wr2,
		R.drawable.bg_wr2, R.drawable.bg_alipay2, R.drawable.bg_paynow2, R.drawable.bg_etex2};
	
	public static final int BANK_MAIN[] = {R.drawable.bg_card_ss3, R.drawable.bg_hn3, R.drawable.bg_ibk3, R.drawable.bg_jb3, R.drawable.bg_kb3,
		R.drawable.bg_keb3, R.drawable.bg_kj3, R.drawable.bg_kn3, R.drawable.bg_nh3, R.drawable.bg_sh3, 0, R.drawable.bg_wr3,
		R.drawable.bg_wr3, R.drawable.bg_alipay3, R.drawable.bg_paynow3, R.drawable.bg_etex3};
	
	public static final int BANK_SUB[] = {R.drawable.bg_card_ss4, R.drawable.bg_hn4, R.drawable.bg_ibk4, R.drawable.bg_jb4, R.drawable.bg_kb4,
		R.drawable.bg_keb4, R.drawable.bg_kj4, R.drawable.bg_kn4, R.drawable.bg_nh4, R.drawable.bg_sh4, 0, R.drawable.bg_wr4,
		R.drawable.bg_wr3, 0, 0, 0};
}
