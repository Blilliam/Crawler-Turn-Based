package Open.Upgrades;

import main.enums.Rarity;

public class Upgrades {
	Rarity[] rarities;

	public Upgrades() {
		rarities = new Rarity[4];
		rarities[0] = Rarity.BRONZE;
		rarities[1] = Rarity.SILVER;
		rarities[2] = Rarity.GOLD;
		rarities[3] = Rarity.DIAMOND;
	}
}
