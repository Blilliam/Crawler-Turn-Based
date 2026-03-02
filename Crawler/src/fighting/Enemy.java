package fighting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.AppPanel;

public class Enemy {
	public int hp;
	public int atk;
	
	public int size = 200;
	
	public int x = AppPanel.WIDTH/ 2 - size/2;
	public int y = AppPanel.HEIGHT/2 - 100 - size;

	public String name;

	private static BufferedImage sprite;

	public Enemy() {
		hp = 10;
		atk = 1;
		name = "tester";

		if (sprite == null) {
			try {
				sprite = ImageIO.read(getClass().getResource("/images/Sprite-Skeleton.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Enemy(String spriteName) {
		this();

		if (sprite == null) {
			try {
				sprite = ImageIO.read(getClass().getResource("/images/" + spriteName + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		if (sprite != null) {
	        g2.drawImage(
	            sprite,
	            x,
	            y,
	            size,
	            size,
	            null
	        );
	    }
	}
}
