package Open;

import java.awt.Color;
import java.awt.Graphics2D;

import main.AppPanel;
import main.GameObject;

public class OpenEnemy {

    public int x, y; // world coordinates
    public int width = 28, height = 28;
    public int speed = 10; // pixels per update
    public int hp = 30;

    private GameObject gameObj;

    public OpenEnemy(GameObject gameObj, int spawnX, int spawnY) {
        this.gameObj = gameObj;
        this.x = spawnX;
        this.y = spawnY;
    }

    // Follow player
    public void update() {
    	double dx = gameObj.player.x - x;
    	double dy = gameObj.player.y - y;
    	double dist = Math.sqrt(dx*dx + dy*dy);

    	if (dist > 0) {
    	    double moveX = (dx / dist) * speed;
    	    double moveY = (dy / dist) * speed;

    	    // Smooth the movement a bit
    	    x += moveX * 0.5 + (Math.random() - 0.5) * 0.3;
    	    y += moveY * 0.5 + (Math.random() - 0.5) * 0.3;
    	}

    }

    // Draw relative to player
    public void draw(Graphics2D g) {
        int screenX = x - gameObj.player.x + AppPanel.WIDTH / 2;
        int screenY = y - gameObj.player.y + AppPanel.HEIGHT / 2;
        g.setColor(Color.RED);
        g.fillRect(screenX, screenY, width, height);
    }
}