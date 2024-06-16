package content.block;

import content.GameObject;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Coin extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private final ObjectHandler handler;

    private final Animation animCoin, animCoinPick;

    private boolean isPicked = false;
    private boolean claimed = false;

    public Coin(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectID.COIN, WIDTH, HEIGHT, scale);
        this.handler = handler;

        Texture tex = GameEngine.getTexture();
        animCoin = new Animation(5, tex.getCoin());
        animCoinPick = new Animation(10, tex.getCoinPick());
    }

    @Override
    public void tick() {
        animCoin.runAnimation();

        if (isPicked) {
            animCoinPick.runSingleAnimation();

            if (animCoinPick.isFinished()) {
                isPicked = false;
            }

            if (!claimed) {
                handler.addCoinPicked(this);
                System.out.println("Coin : " + handler.getCoinPicked().size());
                claimed = true;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (!isPicked) {
            if (!claimed) {
                animCoin.drawAnimation(g, (int) getX()+8, (int) getY(), (int) getWidth()-16, (int) getHeight()-16);
            }
            } else {
                animCoinPick.drawAnimation(g, (int) getX()-8, (int) getY()-16, (int) getWidth() +16, (int) getHeight()+16);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX()+8, (int) getY(), (int) getWidth()-16, (int) getHeight()-16);
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public boolean isClaimed() {
        return claimed;
    }
}
