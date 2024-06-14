package textures;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int frames;
    private int index = 0;
    private int count = 0;
    private boolean isFinished = false;
    private final BufferedImage[] images;
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage... args) {
        this.speed = speed;
        this.images = args;
        this.frames = args.length;
    }

    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();
        }
    }

    private void nextFrame() {
        for (int i = 0; i < frames; i++) {
            if (count == i) {
                currentImg = images[i];
            }
        }
        count++;

        if (count > frames) {
            count = 0;
        }
    }

    public void runSingleAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextSingleFrame();;
        }
    }

    private void nextSingleFrame() {
        for (int i = 0; i < frames; i++) {
            if (isFinished) {
                currentImg = images[frames -1];
                break;
            }

            if (count == i) {
                currentImg = images[i];
            }
        }
        count++;

        if (count > frames) {
            isFinished = true;
            count = 0;
        }
    }

    public void drawAnimation(Graphics g, int x, int y, int w, int h) {
        g.drawImage(currentImg, x, y, w, h, null);
    }


    public void reset() {
        isFinished = false;
        index = 0;
        count = 0;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
