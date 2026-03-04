package main;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;

    private long lastTime;
    private long delay; // milliseconds between frames

    public Animation(BufferedImage[] frames, long delay) {
        this.frames = frames;
        this.delay = delay;
        currentFrame = 0;
        lastTime = System.currentTimeMillis();
    }

    public void update() {

        long now = System.currentTimeMillis();

        if(now - lastTime > delay) {
            currentFrame++;
            lastTime = now;

            if(currentFrame >= frames.length)
                currentFrame = 0;
        }
    }

    public BufferedImage getFrame() {
        return frames[currentFrame];
    }
}