package Open;

import java.awt.Color;
import java.awt.Graphics2D;

import main.AppPanel;
import main.GameObject;

public class OpenMap {
    public int tileSize = 100;
    public int rows = 50, cols = 50;
    public int[][] tiles = new int[rows][cols];
    
    public int WIDTH = tileSize * rows;
    public int HEIGHT = tileSize * cols;

    private GameObject gameObj;

    public OpenMap(GameObject gameObj) {
        this.gameObj = gameObj;

        // Random map generation
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = (Math.random() < 0.1) ? 1 : 0; // 10% walls
            }
        }
    }

    public void draw(Graphics2D g) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int screenX = c * tileSize - gameObj.player.x + AppPanel.WIDTH / 2;
                int screenY = r * tileSize - gameObj.player.y + AppPanel.HEIGHT / 2;

                if (tiles[r][c] == 0) g.setColor(Color.GREEN);
                else g.setColor(Color.GRAY);

                g.fillRect(screenX, screenY, tileSize - 2, tileSize - 2);
            }
        }
    }
}