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

	public ArrayList<TurnBasedCard> deck = new ArrayList<>();
	public ArrayList<TurnBasedCard> hand = new ArrayList<>();

	int frameWidth;
	int frameHeight;
	int frameCount;

	BufferedImage[] walkFrames;

	Animation walkAnim;

	GameObject gameobj;

	public OpenPlayer(GameObject gameobj) {

		frameWidth = 64;
		frameHeight = 64;
		frameCount = 8;

		walkFrames = new BufferedImage[frameCount];

		for (int i = 0; i < frameCount; i++) {

			walkFrames[i] = Assets.playerSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
		}

		walkAnim = new Animation(walkFrames, 100);

		this.gameobj = gameobj;

		Type[] types = { Type.WEAPON, Type.BUFF, Type.AURMOR, Type.MANA };

		for (int i = 0; i < 5; i++) {
			TurnBasedCard c = new TurnBasedCard(gameobj);

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
	
	public void updateOpen() {
		walkAnim.update();
	}
	
	public void drawOpen(Graphics2D g2) {
		g2.drawImage(
			    walkAnim.getFrame(),
			    x,
			    y,
			    null
			);
	}

	public void updateTurnBased() {

		TurnBasedCard clicked = null;

		// TOP CARD FIRST
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