package Open;

import java.util.ArrayList;

import TurnBased.TurnBasedCard;
import enums.GameState;
import enums.Rarity;
import main.AppPanel;
import main.GameObject;
import main.MouseInput;

public class OpenPlayer {

	public int hp;

	public ArrayList<TurnBasedCard> deck;
	public ArrayList<TurnBasedCard> hand;
	public ArrayList<TurnBasedCard> discardDeck;

	public GameObject gameobj;

	public Rarity[] rarities;

	public OpenPlayer(GameObject gameobj) {

		this.gameobj = gameobj;

		deck = new ArrayList<>();
		hand = new ArrayList<>();
		discardDeck = new ArrayList<>();

		// create cards
		for (int i = 0; i < 5; i++) {
			TurnBasedCard c = new TurnBasedCard(gameobj);
			c.name = "Warrior";
			c.rarity = rarities[i % rarities.length];
			deck.add(c);
		}

		moveCards(deck, hand, 5);
	}

	public void moveCards(ArrayList<TurnBasedCard> from, ArrayList<TurnBasedCard> to, int count) {

		if (from.size() < count)
			return;

		for (int i = 0; i < count; i++) {
			to.add(from.remove(0));
		}

		layoutHand();
	}

	/**
	 * BALATRO STYLE HAND POSITIONING
	 */
	public void layoutHand() {

		int cardCount = hand.size();
		if (cardCount == 0)
			return;

		int centerX = AppPanel.WIDTH / 2 - 180/2;
		int baseY = AppPanel.HEIGHT - 400;

		float spacing = 160f;
		float maxAngle = 24f;
		float curveHeight = 60f;

		float startX = centerX - ((cardCount - 1) * spacing) / 2f;

		for (int i = 0; i < cardCount; i++) {

			TurnBasedCard c = hand.get(i);

			float t;

			if (cardCount == 1) {
				t = 0.5f;
			} else {
				t = (float) i / (cardCount - 1);
			}

			// horizontal position
			c.x = (int) (startX + i * spacing);

			// curve
			float curve = (float) (-Math.pow(t - 0.5f, 2) + 0.25);

			c.y = (int) (baseY - curve * curveHeight);

			// rotation toward middle
			c.rotation = (t - 0.5f) * maxAngle;
		}
	}
	
	public void update() {
		if (gameobj.state == GameState.PLAY && MouseInput.mouseClicked) {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).isClicked()) {
					hand.remove(i);
					layoutHand();
				}
			}
		}
		for (TurnBasedCard c : hand) {
			c.update();
		}
	}
}