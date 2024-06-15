package content.block;

import content.GameObject;
import content.ObjectID;
import main.GameEngine;
import main.GameUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chest extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private boolean opened = false;
    private boolean notify = false;
    private final int type;

    private final GameUI gameUI;

    private final BufferedImage[] chestTex;

    public Chest(float x, float y, int scale, GameUI gameUI) {
        super(x, y, ObjectID.CHEST, WIDTH, HEIGHT, scale);
        this.gameUI = gameUI;

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
        if (notify) {
            g.setFont(gameUI.getGameFont().deriveFont(10f));
            g.setColor(Color.WHITE);
            g.drawString("Press Z to open", (int) (getX() - getWidth() - 20), (int) (getY() - getHeight() - 10));
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

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
