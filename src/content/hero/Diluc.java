package content.hero;

import content.GameObject;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import main.condition.ButtonAction;
import textures.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Diluc extends GameObject {
    private static final float WIDTH = 37;
    private static final float HEIGHT = 37;

    private HeroAction action = HeroAction.IDLE_RIGHT;
    private boolean jumped = false;
    private ButtonAction buttonAction;

    private ObjectHandler handler;
    private Texture tex;
    private BufferedImage idleRight, idleLeft, runLeft, runRight;

    public Diluc(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectID.HERO, WIDTH, HEIGHT, scale);
        this.handler = handler;

        tex = GameEngine.getTexture();
        idleRight = tex.getIdleRight();
        idleLeft = tex.getIdleLeft();
        runRight = tex.getRunRight();
        runLeft = tex.getRunLeft();
    }

    private void collision() {
        for (int i = 0; i < handler.getGameObjs().size(); i++) {
            GameObject temp = handler.getGameObjs().get(i);

            if (temp.getId() == ObjectID.BLOCK || temp.getId() == ObjectID.PIPE) {
                if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelY(0);
                    jumped = false;
                }

                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelY(0);
                }

                if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth());
                }

                if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth());
                }

            }
        }
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        applyGravity();
        collision();
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.yellow);
//        g.fillRect((int) getX(), (int) getY(), (int) WIDTH, (int) HEIGHT);
        switch (action) {
            case RUN_LEFT:
                g.drawImage(runLeft, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case RUN_RIGHT:
                g.drawImage(runRight, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case IDLE_LEFT:
                g.drawImage(idleLeft, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case IDLE_RIGHT:
                g.drawImage(idleRight, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
        }

//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - getWidth() / 4),
                (int) (getY() + getHeight()/2),
                (int) getWidth()/2,
                (int) getHeight()/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth()/2 - getWidth()/4),
                (int) getY(),
                (int) getWidth()/2,
                (int) getHeight()/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 5),
                (int) getY() + 5,
                5,
                (int) getHeight() -10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(),
                (int) (getY() +5),
                5,
                (int) (getHeight() - 10));
    }

    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
        g2.draw(getBoundsRight());
        g2.draw(getBoundsLeft());
        g2.draw(getBoundsTop());
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public HeroAction getAction() {
        return action;
    }

    public void setAction(HeroAction action) {
        this.action = action;
    }
}
