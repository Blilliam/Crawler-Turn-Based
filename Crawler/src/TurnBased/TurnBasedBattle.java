package TurnBased;

import java.awt.Graphics2D;

import Open.OpenPlayer;

public class TurnBasedBattle {
	public TurnBasedWave[] waves;

	public OpenPlayer tempPlayer;

	public TurnBasedBattle() {
		waves = new TurnBasedWave[1];

		for (int i = 0; i < 1; i++) {
			waves[i] = new TurnBasedWave(5);
		}
	}

	public void draw(Graphics2D g2) {
		for (int i = 0; i < waves.length; i++) {
			waves[i].draw(g2);
		}
	}

	public void update() {
		for (int i = 0; i < waves.length; i++) {
			waves[i].update();
		}
	}
}
