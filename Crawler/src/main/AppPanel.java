package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class AppPanel extends JPanel implements Runnable {
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	public static final int WIDTH = ((int) tk.getScreenSize().getWidth());
	public static final int HEIGHT = ((int)tk.getScreenSize().getHeight()) - 38;
	public Dimension d = new Dimension(WIDTH, HEIGHT);
	public Thread t = new Thread(this);
	MouseInput mouseHandler = new MouseInput();
	GameObject gameObj = new GameObject(mouseHandler);
	
	// Constructor
	public AppPanel() {
		setPreferredSize(d);
		setFocusable(true);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
		t.start();
	}
	
	// Overriding functions
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        gameObj.draw(g2);
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			gameObj.update();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}