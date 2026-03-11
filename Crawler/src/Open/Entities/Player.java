package Open.Entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Open.Artifacts.Artifact;
import Open.Entities.Enemies.Enemy;
import main.Animation;
import main.AppPanel;
import main.Assets;
import main.GameObject;
import main.enums.GameState;

public class Player extends Entity {
	public ArrayList<Artifact> artifacts = new ArrayList<>();

	public int expToUpgrade = 10;
	public int totalUpgradesAvailible = 1;
	public boolean expCollectedForUpgrade = false;
	public int currExp;

	public boolean isRight;

	// Animation
	private BufferedImage[] walkFrames;
	private Animation walkAnim;
	private int frameWidth = 192, frameHeight = 192;

	public Player(GameObject gameObj) {
		super(gameObj);
		
		hp = 10;

		x = gameObj.map.HEIGHT / 2;
		y = gameObj.map.WIDTH / 2;

		speed = 6;

		isRight = true;

		height = 100;
		width = 100;

		// Load walk frames
		int frameCount = 4;
		walkFrames = new BufferedImage[frameCount];
		for (int i = 0; i < frameCount; i++) {
			walkFrames[i] = Assets.playerSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
		}
		walkAnim = new Animation(walkFrames, 100);
	}

	// Update player movement
	public void update() {
		if (hp <= 0) {
			isDead = true;
			gameObj.state = GameState.DEAD;
		}
		
		updateOpenMovement();

		for (Enemy e : gameObj.enemies) {
			// e.damage(5);
		}
		handleEnemyCollisions();

	}

	public void updateOpenMovement() {

		if (gameObj.keyH.isMoving)
			walkAnim.update();
		else
			walkAnim.setFrame(3);

		double dx = 0;
		double dy = 0;

		if (gameObj.keyH.up)
			dy -= 1;
		if (gameObj.keyH.down)
			dy += 1;
		if (gameObj.keyH.left) {
			dx -= 1;
			isRight = false;
		}
		if (gameObj.keyH.right) {
			dx += 1;
			isRight = true;
		}

		double length = Math.sqrt(dx * dx + dy * dy);

		if (length > 0) {
			dx /= length;
			dy /= length;

			x += dx * speed;
			y += dy * speed;
		}
	}

	// Draw player at center of screen
	public void draw(Graphics2D g2) {
		int drawX = AppPanel.WIDTH / 2 - 50; // display width = 100
		int drawY = AppPanel.HEIGHT / 2 - 50;
		if (isRight)
			g2.drawImage(walkAnim.getFrame(), drawX, drawY, width, height, null);
		else
			g2.drawImage(walkAnim.getFrame(), drawX + width, drawY, -100, height, null);

		drawXPBar(g2);
	}

	private void drawXPBar(Graphics2D g2) {
		if (totalUpgradesAvailible != 0) {
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 30));
			FontMetrics fm = g2.getFontMetrics();
		}
		// Bar position & size
		int barWidth = AppPanel.WIDTH;
		int barHeight = 30;
		int x = (AppPanel.WIDTH - barWidth) / 2;
		int y = 0; // below score stats

		// Percentage filled
		float percent = Math.min(1.0f, (float) currExp / expToUpgrade);

		// Background
		g2.setColor(new Color(50, 50, 50, 180));
		g2.fillRect(x, y, barWidth, barHeight);

		// Filled portion
		g2.setColor(new Color(0, 200, 255));
		g2.fillRect(x, y, (int) (barWidth * percent), barHeight);

		// Border
		g2.setColor(Color.WHITE);
		g2.drawRect(x, y, barWidth, barHeight);

		// Text
		g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		String text = currExp + " / " + expToUpgrade + " XP";
		int textWidth = g2.getFontMetrics().stringWidth(text);
		g2.drawString(text, x + (barWidth - textWidth) / 2, y + barHeight - 5);
	}

	public void handleEnemyCollisions() {
		for (int i = 0; i < gameObj.enemies.size(); i++) {

			Enemy e2 = gameObj.enemies.get(i);

			if (Entity.rectCollision(this, e2)) { // avoid divide by zero
				hp--;
			}
		}
	}
}