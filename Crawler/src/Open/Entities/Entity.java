package Open.Entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.AppPanel;
import main.GameObject;

public abstract class Entity { // main parent class of all moving objects on screen (weapon effects, player, enemies)

	public int x, y; // world coordinates

	//width and heigh
	public int width;
	public int height;

	//if youre dead
	public boolean isDead;

	public int speed; // pixels per update
	public int hp;

	public GameObject gameObj;

	public Entity(GameObject gameObj) {
		this.gameObj = gameObj;
	}

	//abscract/required methods to be an entity
	public abstract void update();

	// Draw relative to player
	public abstract void draw(Graphics2D g);

	//colision of 2 entities
	public static boolean rectCollision(Entity e1, Entity e2) {
		return e1.x < e2.x + e2.width && e1.x + e1.width > e2.x && e1.y < e2.y + e2.height && e1.y + e1.height > e2.y;
	}
}
