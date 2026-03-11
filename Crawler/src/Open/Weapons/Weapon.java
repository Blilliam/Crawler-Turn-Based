package Open.Weapons;

import main.enums.*;

public abstract class Weapon {
	public static int atk;
	public static Rarity rarity;
	public static Type type;

	public Weapon() {
		rarity = Rarity.BRONZE;
		type = Type.WEAPON;
	}
}
