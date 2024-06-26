package content.block;

import content.GameObject;
import content.ObjectID;
import main.GameEngine;
import main.GameUI;
import main.condition.Notify;
import textures.Animation;
import textures.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gate extends GameObject implements Notify {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 90;

    private boolean opened = false;
    private boolean enterable = false;
    private int notify = 0;

    private final int minimumCoin;

    private final GameUI gameUI;

    private final Animation animGate, animGateBack;
    private final BufferedImage[] texGate;

    public Gate(float x, float y, int scale, int minimumCoin, GameUI gameUI) {
        super(x, y-50, ObjectID.GATE, WIDTH, HEIGHT, scale);
        this.gameUI = gameUI;
        this.minimumCoin = minimumCoin;

        Texture tex = GameEngine.getTexture();

        texGate = tex.getGate();
        animGate = new Animation(8, texGate);
        animGateBack = new Animation(8, tex.getGateBackground());
    }

    @Override
    public void tick() {
        animGateBack.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if (!opened) {
            g.drawImage(texGate[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        } else {
            animGateBack.drawAnimation(g, (int) getX()+10, (int) getY()+10, (int) getWidth()-20, (int) getHeight());
            animGate.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }

        switch (notify){
            case 1:
                g.setFont(gameUI.getGameFont().deriveFont(10f));
                g.setColor(Color.WHITE);
                g.drawString("Locked", (int) getX() + 15, (int) (getY() - 10));
                break;
            case 2:
                if (!opened) {
                    g.setFont(gameUI.getGameFont().deriveFont(10f));
                    g.setColor(Color.WHITE);
                    g.drawString("Hold Z to open", (int) getX() - 15, (int) (getY() - 10));
                }
                break;
        }
//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth()/2), (int) getY(), (int) getWidth()/10, (int) getHeight());
    }

    public Rectangle getOuterBounds() {
        return new Rectangle((int) getX()-50, (int) getY()-50, (int) getWidth()+100, (int) getHeight()+100);
    }

    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getOuterBounds());
        g2.draw(getBounds());
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isEnterable() {
        return enterable;
    }

    public void setEnterable(boolean enterable) {
        this.enterable = enterable;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public Animation getAnimGate() {
        return animGate;
    }

    public int getMinimumCoin() {
        return minimumCoin;
    }
}
