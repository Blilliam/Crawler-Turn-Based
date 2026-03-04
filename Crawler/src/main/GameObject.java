package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Open.OpenPlayer;
import TurnBased.TurnBasedBattle;
import TurnBased.TurnBasedCard;
import enums.GameState;

public class GameObject {
	MouseInput mouseHandler;

	int startButtonWidth;
	int startButtonHeight;
	GameButton startButton;

	int exitControlButtonWidth;
	int exitControlButtonHeight;
	GameButton exitControlButton;

	int controlButtonWidth;
	int controlButtonHeight;
	GameButton controlButton;

	public static GameState state;
	
	TurnBasedBattle testBattle;
	
	OpenPlayer player;

	public GameObject(MouseInput mouseHandler) {
		this.mouseHandler = mouseHandler;
		state = GameState.START;
		
		player = new OpenPlayer(this);

		startButtonWidth = 300;
		startButtonHeight = 100;

		startButton = new GameButton(AppPanel.WIDTH / 2 - startButtonWidth / 2,
				AppPanel.HEIGHT / 2 - startButtonHeight / 2, startButtonWidth, startButtonHeight, "START",
				this::startGame, new Color(0, 60, 60), Color.BLACK);

		controlButton = new GameButton(AppPanel.WIDTH / 2 - startButtonWidth / 2,
				AppPanel.HEIGHT / 2 - startButtonHeight / 2 + 230 + controlButtonHeight / 2, startButtonWidth,
				startButtonHeight, "CONTROLS", this::showControls, new Color(0, 60, 60), Color.BLACK);

		exitControlButton = new GameButton(AppPanel.WIDTH / 2 - startButtonWidth / 2,
				AppPanel.HEIGHT / 2 + startButtonHeight / 2 + 50, startButtonWidth, startButtonHeight, "EXIT BACK",
				this::toStart);
		testBattle = new TurnBasedBattle();
	}

	public void update() {
		if (state == GameState.START) {
			startButton.update();
			controlButton.update();

		} else if (state == GameState.PLAY) {
			player.update();
			testBattle.update();
		} else if (state == GameState.DEAD) {
			exitControlButton.update();

		} else if (state == GameState.UPGRADING) {

		} else if (state == GameState.CONTROLS) {
			exitControlButton.update();
		}
		// Clear click flag after updates
		MouseInput.update();
	}
	
	public void draw(Graphics2D g2) {

		if (state == GameState.START) {
			startButton.draw(g2);
			controlButton.draw(g2);
		} else if (state == GameState.PLAY) {
			if (player.hand != null) {
				for (TurnBasedCard c : player.hand) {
					c.draw(g2);
				}
			}
			testBattle.draw(g2);
		} else if (state == GameState.DEAD) {
			exitControlButton.draw(g2);

		} else if (state == GameState.UPGRADING) {

		} else if (state == GameState.CONTROLS) {
			drawControls(g2);
			exitControlButton.draw(g2);
		}
	}

	public void drawControls(Graphics2D g2) {
		g2.setColor(new Color(30, 30, 80, 200)); // dark blue overlay
		g2.fillRect(0, 0, AppPanel.WIDTH, AppPanel.HEIGHT);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 30));
		FontMetrics fm = g2.getFontMetrics();

		String s1 = "Up: W";
		String s2 = "Down: S";
		String s3 = "Left: A";
		String s4 = "Right: D";

		int x1 = (AppPanel.WIDTH - fm.stringWidth(s1)) / 2;
		int x2 = (AppPanel.WIDTH - fm.stringWidth(s2)) / 2;
		int x3 = (AppPanel.WIDTH - fm.stringWidth(s3)) / 2;
		int x4 = (AppPanel.WIDTH - fm.stringWidth(s4)) / 2;

		g2.drawString(s1, x1, AppPanel.HEIGHT / 2 - 100);
		g2.drawString(s2, x2, AppPanel.HEIGHT / 2 - 70);
		g2.drawString(s3, x3, AppPanel.HEIGHT / 2 - 40);
		g2.drawString(s4, x4, AppPanel.HEIGHT / 2 - 10);
	}

	private void startGame() {
		state = GameState.PLAY;
	}

	private void showControls() {
		state = GameState.CONTROLS;
	}

	private void toStart() {
		state = GameState.START;

	}
}
