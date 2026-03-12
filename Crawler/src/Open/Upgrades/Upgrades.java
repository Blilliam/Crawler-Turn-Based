package Open.Upgrades;

import main.enums.WeaponRarity;

public class Upgrades {
	WeaponRarity[] rarities;

	public Upgrades() {
		rarities = new WeaponRarity[4];
		rarities[0] = WeaponRarity.BRONZE;
		rarities[1] = WeaponRarity.SILVER;
		rarities[2] = WeaponRarity.GOLD;
		rarities[3] = WeaponRarity.DIAMOND;
	}
}
