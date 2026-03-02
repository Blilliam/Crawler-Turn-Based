package fighting;

public class Wave {
	public Enemy[] enemies;
	
	public static final int maxEnemyCount = 5;
	public int enemyCount;
	
	public Wave(int enemyCount) {
		this.enemyCount = Math.min(enemyCount, maxEnemyCount);
		
		for (int i = 0; i < enemyCount; i++) {
			enemies[i] = new Enemy();
		}
	}
}
