package com.seancheey;

import java.awt.Color;

public class RCConstants {
	public static final int COMMON_SELL_PRICE = 1, UNCOMMON_SELL_PRICE = 8, RARE_SELL_PRICE = 25, EPIC_SELL_PRICE = 100,
			LEGENDARY_SELL_PRICE = 125;
	public static final int MAX_CPU = 1750;
	public static final int COMMON = 0, UNCOMMON = 1, RARE = 2, EPIC = 3, LEGENDARY = 4;
	public static final Color RC_ORANGE = new Color(255, 133, 17), RC_BLACK = new Color(23, 23, 23);

	public static final int getSellPrice(int rarity) {
		switch (rarity) {
		case COMMON:
			return COMMON_SELL_PRICE;
		case UNCOMMON:
			return UNCOMMON_SELL_PRICE;
		case RARE:
			return RARE_SELL_PRICE;
		case EPIC:
			return EPIC_SELL_PRICE;
		case LEGENDARY:
			return LEGENDARY_SELL_PRICE;
		}
		return 0;
	}
}