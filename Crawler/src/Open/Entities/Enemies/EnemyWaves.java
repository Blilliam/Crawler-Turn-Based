package Open.Entities.Enemies;

import main.AppPanel;
import main.GameObject;
import main.enums.GameState;

public class EnemyWaves {
	GameObject gameObj;
	int waveNum; // make instance variable

	public EnemyWaves(GameObject gameObj) {
		this.gameObj = gameObj;
		waveNum = 1;
		createEnemies();
	}

	public void update() {
		// Only check for wave completion while playing
		if (gameObj.state == GameState.OPEN && gameObj.enemies.size() == 0) {
			waveNum++;
			createEnemies();
		}
	}

	/**
	 * creates more enemies in the enemies ArrayList
	 */
	public void createEnemies() {
	    int numEnemies;
	    int baseTier;

	    if (waveNum <= 10) {
	        numEnemies = waveNum + 2;
	        baseTier = 1;
	    } else if (waveNum <= 20) {
	        numEnemies = 10 + (waveNum - 10) * 2;
	        baseTier = 3;
	    } else {
	        numEnemies = 30 + (waveNum - 20) * 3;
	        baseTier = 5;
	    }

	    int margin = 200; // distance from player view

	    for (int i = 0; i < numEnemies; i++) {
	        int tier = baseTier + (int) (Math.random() * 3);

	        int tempX, tempY;

	        boolean spawnLeftOrRight = Math.random() < 0.5;

	        if (spawnLeftOrRight) {
	            // Spawn left or right off-screen
	            tempX = Math.random() < 0.5
	                    ? gameObj.player.x - AppPanel.WIDTH / 2 - margin  // left
	                    : gameObj.player.x + AppPanel.WIDTH / 2 + margin; // right
	            tempY = (int) (Math.random() * gameObj.map.HEIGHT);
	        } else {
	            // Spawn top or bottom off-screen
	            tempY = Math.random() < 0.5
	                    ? gameObj.player.y - AppPanel.HEIGHT / 2 - margin // top
	                    : gameObj.player.y + AppPanel.HEIGHT / 2 + margin; // bottom
	            tempX = (int) (Math.random() * gameObj.map.WIDTH);
	        }

	        // Clamp to map boundaries
	        tempX = Math.max(0, Math.min(tempX, gameObj.map.WIDTH - 1));
	        tempY = Math.max(0, Math.min(tempY, gameObj.map.HEIGHT - 1));

	        gameObj.enemies.add(new Enemy(gameObj, tempX, tempY, tier));
	    }

	    // Optional boss
	    if (waveNum % 10 == 0) {
	        int bossTier = baseTier * 5;
	        int bossX = gameObj.player.x; // can be centered on player X
	        int bossY = gameObj.player.y - AppPanel.HEIGHT / 2 - margin; // above view
	        bossX = Math.max(0, Math.min(bossX, gameObj.map.WIDTH - 1));
	        bossY = Math.max(0, Math.min(bossY, gameObj.map.HEIGHT - 1));
	        gameObj.enemies.add(new Enemy(gameObj, bossX, bossY, bossTier));
	    }
	}
}