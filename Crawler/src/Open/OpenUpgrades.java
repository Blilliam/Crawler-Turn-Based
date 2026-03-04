package Open;

import enums.Rarity;

public class OpenUpgrades {
	Rarity[] rarities;
	
	public OpenUpgrades() {
		rarities = new Rarity[4];
		rarities[0] = Rarity.BRONZE;
		rarities[1] = Rarity.SILVER;
		rarities[2] = Rarity.GOLD;
		rarities[3] = Rarity.DIAMOND;
	}
}
