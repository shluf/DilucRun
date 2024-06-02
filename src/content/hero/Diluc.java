package content.hero;

import content.GameObject;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Diluc extends GameObject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 37;

    private HeroAction action = HeroAction.IDLE;
    private int jumped = 0;
    private boolean isRight = true;

    private final ObjectHandler handler;
    private final Texture tex;
    private final Animation animIdle, animRun, animIdleSword, animSlash, animJump, animDoubleJump;

    public Diluc(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectID.HERO, WIDTH, HEIGHT, scale);
        this.handler = handler;

        tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getIdle());
        animRun = new Animation(5, tex.getRun());
        animIdleSword = new Animation(10, tex.getIdleSword());
        animSlash = new Animation(5, tex.getSlash());
        animJump = new Animation(10, tex.getJump());
        animDoubleJump = new Animation(5, tex.getDoubleJump());
    }

    private void collision() {
        for (int i = 0; i < handler.getGameObjs().size(); i++) {
            GameObject temp = handler.getGameObjs().get(i);

            if (temp.getId() == ObjectID.TILE || temp.getId() == ObjectID.CHEST) {
                if (getBounds().intersects(temp.getBounds())) {
                    setY(temp.getY() - getHeight());
                    setVelY(0);
                    jumped = 0;
                }

                if (getBoundsTop().intersects(temp.getBounds())) {
                    setY(temp.getY() + temp.getHeight());
                    setVelY(0);
                }

                if (getBoundsRight().intersects(temp.getBounds())) {
                    setX(temp.getX() - getWidth() + 15);
                }

                if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth() / 5);
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

        animRun.runAnimation();
        animIdle.runAnimation();
        animJump.runAnimation();
        animDoubleJump.runAnimation();
        animSlash.runAnimation();
        animIdleSword.runAnimation();
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.yellow);
//        g.fillRect((int) getX(), (int) getY(), (int) WIDTH, (int) HEIGHT);
        if (jumped == 0) {
            switch (action) {
                case RUN:
    //                g.drawImage(diluc[8], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
                    if (jumped == 0) {
                        if (!isRight) {
                            animRun.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animRun.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                    }
                    break;

                case IDLE:
                    if (!isRight) {
                        animIdle.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                    } else {
                        animIdle.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                    }
                    break;

                case ATTACK:
                    if (!isRight) {
                        g.drawImage(tex.getSlash()[1], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
                    } else {
                        g.drawImage(tex.getSlash()[1], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                    }
                    break;
            }
        } else if (jumped == 2) {
            if (!isRight) {
                animDoubleJump.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
            } else {
                animDoubleJump.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
            }
        } else {
            if (!isRight) {
                animJump.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
            } else {
                animJump.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
            }
        }
//        System.out.println("Jump: " + jumped);
//        System.out.println("Action: " + action);


//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - (getWidth() / 4)),
                (int) (getY() + getHeight()/2),
                (int) getWidth()/2,
                (int) getHeight()/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - (getWidth() / 4)),
                (int) getY() + 10,
                (int) getWidth()/2,
                (int) getHeight()/2 - 10);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 20),
                (int) getY() + 10,
                5,
                (int) getHeight() -15);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX() + 15,
                (int) (getY() + 10),
                5,
                (int) (getHeight() - 15));
    }

    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
        g2.draw(getBoundsRight());
        g2.draw(getBoundsLeft());
        g2.draw(getBoundsTop());
    }

    public int isJumped() {
        return jumped;
    }

    public void setJumped() {
        jumped++;
    }

    public HeroAction getAction() {
        return action;
    }

    public void setAction(HeroAction action) {
        this.action = action;
    }

    public static int getHeroHeight() {
        return HEIGHT;
    }

    public static int getHeroWidth() {
        return WIDTH;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
