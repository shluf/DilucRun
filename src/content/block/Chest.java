package content.block;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import main.GameUI;
import main.condition.Notify;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chest extends Square implements Notify {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private boolean opened = false;
    private int notify = 0;
    private final int type;

    private final GameUI gameUI;
    private final ObjectHandler handler;

    private final BufferedImage[] chestTex;

    public Chest(float x, float y, int scale, GameUI gameUI, ObjectHandler handler) {
        super(x, y, ObjectID.CHEST, WIDTH, HEIGHT, scale);
        this.gameUI = gameUI;
        this.handler = handler;

        int[] index = {0, 2, 4, 6};
        Random random = new Random();
        this.type = index[random.nextInt(index.length)];

        chestTex = GameEngine.getTexture().getChest();
    }

    @Override
    public void tick() {
        if (handler.getHero() != null && handler.getHero().getAction() == ObjectAction.RESPAWN) {
            setOpened(false);
        }
    }

    @Override
    public void render(Graphics g) {
        if (!opened) {
            g.drawImage(chestTex[type], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
        } else {
            g.drawImage(chestTex[type+1], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
        }
        if (notify == 1) {
            g.setFont(gameUI.getGameFont().deriveFont(10f));
            g.setColor(Color.WHITE);
            g.drawString("Press Z to open", (int) (getX() - getWidth() - 20), (int) (getY() - getHeight() - 10));
        }
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

    public void setNotify(int notify) {
        this.notify = notify;
    }
}
