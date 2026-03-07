package Weapons;

import enums.*;

public abstract class OpenWeapon {
	public static int atk;
	public static Rarity rarity;
	public static Type type; 
	
	public OpenWeapon() {
		rarity = Rarity.BRONZE;
		type = Type.WEAPON;
	}
}
