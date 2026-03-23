package Open.Weapons;

import java.awt.image.BufferedImage;

import Open.Entities.Entity;
import main.GameObject;

public abstract class WeaponEntity extends Entity {
	BufferedImage sprite;
	public double rotationSpeed;
	public int projectileSpeed;
	

	public WeaponEntity(GameObject gameObj, int x, int y, int width, int height, double rotationSpeed, int projectileSpeed, BufferedImage sprite) {
		super(gameObj);
		this.x =x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.sprite = sprite;
	}
	
	
}
