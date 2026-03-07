package Open;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import TurnBased.TurnBasedCard;
import enums.Type;
import main.Animation;
import main.AppPanel;
import main.Assets;
import main.GameObject;

public class OpenPlayer {

	public int x, y; // world coordinates
	public int speed;
	public boolean isRight;
	
	public int height;
	public int width;

	public ArrayList<TurnBasedCard> deck = new ArrayList<>();
	public ArrayList<TurnBasedCard> hand = new ArrayList<>();

	// Animation
	private BufferedImage[] walkFrames;
	private Animation walkAnim;
	private int frameWidth = 192, frameHeight = 192;

	private GameObject gameObj;

	public OpenPlayer(GameObject gameObj) {
		this.gameObj = gameObj;
		x = gameObj.map.HEIGHT / 2;
		y = gameObj.map.WIDTH / 2;
		speed = 5;
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

		// Initialize deck
		Type[] types = { Type.WEAPON, Type.BUFF, Type.AURMOR, Type.MANA };
		for (int i = 0; i < 5; i++) {
			TurnBasedCard c = new TurnBasedCard(gameObj);
			c.name = "Card";
			c.type = types[i % 4];
			deck.add(c);
		}
		moveCards(deck, hand, 5);
	}

	public void moveCards(ArrayList<TurnBasedCard> from, ArrayList<TurnBasedCard> to, int count) {
		for (int i = 0; i < count && !from.isEmpty(); i++)
			to.add(from.remove(0));
		layoutHand();
	}

	// Update player movement
	public void updateOpen() {
		if (gameObj.keyH.isMoving)
			walkAnim.update();
		else
			walkAnim.setFrame(3);

		if (gameObj.keyH.up)
			y -= speed;
		if (gameObj.keyH.down)
			y += speed;
		if (gameObj.keyH.left) {
			x -= speed;
			isRight = false;
		}
		if (gameObj.keyH.right) {
			x += speed;
			isRight = true;
		}
	}

	// Draw player at center of screen
	public void drawOpen(Graphics2D g2) {
		int drawX = AppPanel.WIDTH / 2 - 50; // display width = 100
		int drawY = AppPanel.HEIGHT / 2 - 50;
		if (isRight)
			g2.drawImage(walkAnim.getFrame(), drawX, drawY, width, height, null);
		else
			g2.drawImage(walkAnim.getFrame(), drawX + width, drawY, -100, height, null);
	}

	// Turn-based card updates
	public void updateTurnBased() {
		TurnBasedCard clicked = null;
		for (int i = hand.size() - 1; i >= 0; i--) {
			if (hand.get(i).isClicked()) {
				clicked = hand.get(i);
				break;
			}
		}
		if (clicked != null) {
			hand.remove(clicked);
			layoutHand();
		}
		for (TurnBasedCard c : hand)
			c.update();
	}

	public void drawTurnBased(Graphics2D g2) {
		for (TurnBasedCard c : hand)
			c.draw(g2);
	}

	public void layoutHand() {
		int count = hand.size();
		if (count == 0)
			return;

		int centerX = AppPanel.WIDTH / 2 - TurnBasedCard.width / 2;
		int baseY = AppPanel.HEIGHT - 400;
		float spacing = 160f;
		float maxAngle = 24f;
		float curveHeight = 60f;
		float startX = centerX - ((count - 1) * spacing) / 2f;

		for (int i = 0; i < count; i++) {
			TurnBasedCard c = hand.get(i);
			float t = (count == 1) ? 0.5f : (float) i / (count - 1);
			c.x = (int) (startX + i * spacing);
			float curve = (float) (-Math.pow(t - 0.5, 2) + 0.25);
			c.y = (int) (baseY - curve * curveHeight);
			c.rotation = (t - 0.5f) * maxAngle;
		}
	}
}