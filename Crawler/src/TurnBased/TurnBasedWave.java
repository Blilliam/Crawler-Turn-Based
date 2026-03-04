package TurnBased;

import java.awt.Graphics2D;

public class TurnBasedWave {
	public TurnBasedEnemy[] enemies;
	
	public static final int maxEnemyCount = 5;
	public int enemyCount;
	
	public TurnBasedWave(int enemyCount) {
		this.enemyCount = Math.min(enemyCount, maxEnemyCount);
		enemies = new TurnBasedEnemy[enemyCount];
		
		for (int i = -2; i < enemyCount - 2; i++) {
			enemies[i + 2] = new TurnBasedEnemy();
			enemies[i + 2].x += i * 200;
		}
	}
	
	public void draw(Graphics2D g2) {
		for (int i = 0; i<enemies.length; i ++ ) {
			if (enemies[i] == null) {
				continue;
			}
			enemies[i].draw(g2);
		}
	}
	
	public void update() {
		for (int i = 0; i< enemies.length; i++) {
			if (enemies[i] == null) {
				continue;
			}
			enemies[i].update();
			
		}
	}
}
