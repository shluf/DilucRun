package content.enemy;

import content.*;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;
import java.util.Random;

public class Slime extends GameObject implements ObjectBehavior {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 25;
    private final float SPEED;

    private final ObjectHandler handler;

    private ObjectAction action = ObjectAction.IDLE;
    private boolean isRight;
    private int lives = 1;

    private final int slimePoint;

    private final Animation animIdle, animRun, animDeath, animAttack;
    private boolean attackRight;

    public Slime(float x, float y, int scale, boolean isRight, ObjectHandler handler, int attackSpeed, float speed) {
        super(x, y-(int) (HEIGHT/2), ObjectID.SLIME, WIDTH * 2, HEIGHT * 2, scale);
        this.isRight = isRight;
        this.handler = handler;
        this.SPEED = speed;

        Random random = new Random();
        int min = 100;
        int max = 500;
        slimePoint = random.nextInt((max - min) + 1) + min;

        Texture tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getSlimeIdle());
        animRun = new Animation(8, tex.getSlimeMove());
        animDeath = new Animation(10, tex.getSlimeDeath());
        animAttack = new Animation(10 - attackSpeed, tex.getSlimeAttack());
    }

    private void respawn() {
        if (handler.getHero().getAction() == ObjectAction.RESPAWN) {
            setAction(ObjectAction.RUN);
            animDeath.reset();
            lives = 1;
        }
    }

    private void collision() {
        if (getVelX() != 0) {
            setAction(ObjectAction.RUN);
        }

        if (!(action == ObjectAction.DEATH)) {
            for (int i = 0; i < handler.getGameObjs().size(); i++) {
                GameObject temp = handler.getGameObjs().get(i);
                if (temp.getId() == ObjectID.SLIMEHOLDER) {
                    if (!isRight) {
                        setVelX(-SPEED);
                            if (getBounds().intersects(temp.getBounds())) {
                                isRight = true;
                            }
                    } else {
                        setVelX(SPEED);
                        if (temp.getId() == ObjectID.SLIMEHOLDER) {
                            if (getBounds().intersects(temp.getBounds())) {
                                isRight = false;
                            }
                        }
                    }
                }

                if (temp.getId() == ObjectID.ARROW) {
                    if (lives > 0) {
                        if (getBounds().intersects(temp.getBounds())) {
                            decreaseLives();
                            handler.addDeathSlime(this);
                            handler.removeObj(temp);
                        }
                    }
                }
            }
        }
    }

    private void resetAnim() {
        if (action != ObjectAction.ATTACK) {
            animAttack.reset();
        }
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        death();
        resetAnim();
        if (handler.getHero() != null) {
            collision();
            respawn();
        }

        animRun.runAnimation();
        animIdle.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        switch (action) {
            case IDLE:
                if (!isRight) {
                    animIdle.drawAnimation(g, (int) getX(), (int) getY()-4, (int) getWidth(), (int) getHeight());
                } else {
                    animIdle.drawAnimation(g, (int) (getX() + getWidth()), (int) getY()-4, -(int) getWidth(), (int) getHeight());
                }
                break;
            case RUN:
                if (isRight) {
                    animRun.drawAnimation(g, (int) (getX() + getWidth()), (int) getY()-4, -(int) getWidth(), (int) getHeight());
                } else {
                    animRun.drawAnimation(g, (int) getX(), (int) getY()-4, (int) getWidth(), (int) getHeight());
                }
                break;
            case DEATH:
                if (!isRight) {
                    animDeath.drawAnimation(g, (int) (getX() + getWidth()), (int) getY()-4, -(int) getWidth(), (int) getHeight());
                } else {
                    animDeath.drawAnimation(g, (int) getX(), (int) getY()-4, (int) getWidth(), (int) getHeight());
                }
                break;
            case ATTACK:
                if (!attackRight) {
                    animAttack.drawAnimation(g, (int) (getX() + getWidth()), (int) getY()-4, -(int) getWidth(), (int) getHeight());
                } else {
                    animAttack.drawAnimation(g, (int) getX(), (int) getY()-4, (int) getWidth(), (int) getHeight());
                }
                break;
        }

//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX() + 15,
                (int) getY() + 15,
                (int) getWidth() - 30,
                (int) getHeight() - 20);
    }

    public Rectangle getBoundsAttackLeft() {
        return new Rectangle((int) getX() ,
                (int) (getY() + 15),
                30,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsAttackRight() {
        return new Rectangle((int) (getX() + getWidth()/2),
                (int) (getY() + 15),
                30,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsBotom() {
        return new Rectangle((int) getX() + 20,
                (int) getY() + 50,
                (int) getWidth() - 40,
                (int) getHeight()/3 -10);
    }


    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBoundsBotom());
        g2.draw(getBounds());
        g2.draw(getBoundsAttackLeft());
        g2.draw(getBoundsAttackRight());
    }

    private void death() {
        if (lives <= 0) {
            action = ObjectAction.DEATH;
            deathAnimation();
        }
    }

    public void deathAnimation() {
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

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Animation getAnimAttack() {
        return animAttack;
    }

    public void setAttackRight(boolean right) {
        attackRight = right;
    }

    public int getSlimePoint() {
        return slimePoint;
    }
}
