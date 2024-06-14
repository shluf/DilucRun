package content.block;

import content.GameObject;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Chest extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private boolean opened = false;
    private int type;

    private final BufferedImage[] chestTex;

    public Chest(float x, float y, int scale) {
        super(x, y, ObjectID.CHEST, WIDTH, HEIGHT, scale);

        int[] index = {0, 2, 4, 6};
        Random random = new Random();
        this.type = index[random.nextInt(index.length)];

        chestTex = GameEngine.getTexture().getChest();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (!opened) {
            g.drawImage(chestTex[type], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
        } else {
            g.drawImage(chestTex[type+1], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    public Rectangle getOuterBounds() {
        return new Rectangle((int) getX()-5, (int) getY()-5, (int) getWidth()+10, (int) getHeight()+10);
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }
}
