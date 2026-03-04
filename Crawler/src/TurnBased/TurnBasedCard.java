package TurnBased;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Rarity;
import enums.Type;
import main.GameObject;
import main.MouseInput;

public class TurnBasedCard {

	public Type type;
	public Rarity rarity;
	public String name;
	public String description;

	public GameObject gameobj;

	public int x;
	public int y;

	public int width = 180;
	public int height = 270;

	public int cost;
	public int atk;
	public int def;

	public float rotation = 0f;

	private float scale = 1f;
	private float scaleSpeed = 0.05f;
	private boolean hovering = false;

	private float floatOffset = 0f;
	private float floatTime;
	private float floatSpeed;
	private float floatAmount;

	private static BufferedImage manaSphere;
	private BufferedImage itemSprite;

	public TurnBasedCard(GameObject gameobj) {
		this.gameobj = gameobj;
		this.rarity = Rarity.BRONZE;
		this.name = "Card";
		this.description = "example";

		floatTime = (float) (Math.random() * Math.PI * 2);
		floatSpeed = 0.02f + (float) Math.random() * 0.015f;
		floatAmount = 4f + (float) Math.random() * 3f;

		if (manaSphere == null) {
			try {
				manaSphere = ImageIO.read(getClass().getResource("/EnemyImage/edited mana sphere.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {

		hovering = isHovering();

		if (hovering) {
			if (scale < 1.1f)
				scale += scaleSpeed;
		} else {
			if (scale > 1f)
				scale -= scaleSpeed;
		}

		floatTime += floatSpeed;

		floatOffset = (float) (Math.sin(floatTime) * floatAmount + Math.sin(floatTime * 0.6f) * 1.5f);

	}

	public void draw(Graphics2D g2) {

		AffineTransform old = g2.getTransform();

		g2.translate(x + width / 2, y + height / 2 + floatOffset);

		g2.rotate(Math.toRadians(rotation));
		g2.scale(scale, scale);

		g2.translate(-width / 2, -height / 2);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color mainColor = getRarityColor(rarity);

		g2.setColor(new Color(0, 0, 0, 80));
		g2.fillRoundRect(5, 5, width, height, 20, 20);

		GradientPaint gradient = new GradientPaint(0, 0, mainColor, 0, height, mainColor.darker());

		g2.setPaint(gradient);
		g2.fillRoundRect(0, 0, width, height, 20, 20);

		g2.setStroke(new BasicStroke(4));
		g2.setColor(mainColor.brighter());
		g2.drawRoundRect(0, 0, width, height, 20, 20);
		
		
		//draw mana orb and text
		drawText(g2);
		
		if (itemSprite != null)
			g2.drawImage(itemSprite, 40, 40, 100, 100, null);
		
		g2.setTransform(old);

	}

	private void drawText(Graphics2D g2) {
		int x = 3;
		int y = 5;
		int size = 40;

		if (manaSphere != null) {
			g2.drawImage(manaSphere, x, y, size, size, null);
		}

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 28));

		g2.drawString(cost + "   " + name, 15, 35);
	}

	private Color getRarityColor(Rarity rarity) {
		switch (rarity) {
		case BRONZE:
			return new Color(205, 127, 50);
		case SILVER:
			return new Color(192, 192, 192);
		case GOLD:
			return new Color(255, 215, 0);
		case DIAMOND:
			return new Color(80, 220, 255);
		default:
			return Color.WHITE;
		}
	}

	public boolean isHovering() {

		int mx = MouseInput.mouseX;
		int my = MouseInput.mouseY;

		return mx >= x && mx <= x + width && my >= y && my <= y + height;
	}

	public boolean isClicked() {
		int mx = MouseInput.mouseX;
		int my = MouseInput.mouseY;

		return mx >= x && mx <= x + width && my >= y && my <= y + height;
	}
}