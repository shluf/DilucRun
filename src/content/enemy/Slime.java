package content.enemy;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Slime extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 25;
    private static final int SPEED = 2;

    private ObjectHandler handler;

    private ObjectAction action = ObjectAction.IDLE;
    private boolean isRight;

    private Texture tex;
    private Animation animIdle, animRun, animDeath;

    public Slime(float x, float y, int scale, boolean isRight, ObjectHandler handler) {
        super(x, y-(int) (HEIGHT/2), ObjectID.SLIME, WIDTH * 2, HEIGHT * 2, scale);
        this.isRight = isRight;
        this.handler = handler;

        tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getSlimeIdle());
        animRun = new Animation(5, tex.getSlimeMove());
        animDeath = new Animation(10, tex.getSlimeDeath());
    }

    public void respawn() {
        if (handler.getHero().getAction() == ObjectAction.RESPAWN) {
            setAction(ObjectAction.IDLE);
            animDeath.reset();
        }
    }

    public void movement() {
        if (!(action == ObjectAction.DEATH)) {
            for (int i = 0; i < handler.getGameObjs().size(); i++) {
                GameObject temp = handler.getGameObjs().get(i);
                if (!isRight) {
                    setVelX(-SPEED);
                    if (temp.getId() == ObjectID.TILE) {
                        if (getBounds().intersects(temp.getBounds())) {
                            isRight = true;
                        }
                        if (getBoundsBotom().intersects(temp.getBounds())) {
                            setVelY(0);
                        }
                    }
                } else {
                    setVelX(SPEED);
                    if (temp.getId() == ObjectID.TILE) {
                        if (getBounds().intersects(temp.getBounds())) {
                            isRight = false;
                        }
                        if (getBoundsBotom().intersects(temp.getBounds())) {
                            setVelY(0);
                        }
                    }
                }
            }
        } else {
            setBoundsDeath();
        }
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        applyGravity();
        movement();
        respawn();

        animRun.runAnimation();
        animIdle.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        switch (action) {
            case IDLE:
                if (!isRight) {
                    animIdle.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                } else {
                    animIdle.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                }
                break;
            case RUN:
                if (!isRight) {
                    animRun.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                } else {
                    animRun.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                }
                break;
            case DEATH:
                if (!isRight) {
                    animDeath.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                } else {
                    animDeath.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                }
                break;
        }

//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX() + 15,
                (int) getY() + 25,
                (int) getWidth() - 30,
                (int) getHeight() - 40);
    }

    public Rectangle getBoundsBotom() {
        return new Rectangle((int) getX() + 20,
                (int) getY() + 40,
                (int) getWidth() - 40,
                (int) getHeight()/3 -10);
    }


    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBoundsBotom());
        g2.draw(getBounds());
    }

    public void setBoundsDeath() {
//        setY(getY() + 29);
        animDeath.runSingleAnimation();
        setVelY(0);
        setVelX(0);
    }

    public void setAction(ObjectAction action) {
        this.action = action;
    }

    public ObjectAction getAction() {
        return action;
    }

    public static int getSlimeHeight() {
        return HEIGHT;
    }

    public static int getSlimeWidth() {
        return WIDTH;
    }
}
