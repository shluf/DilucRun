package content.hero;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import content.enemy.Slime;
import main.GameEngine;
import main.condition.GameStatus;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Diluc extends GameObject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 37;

    private ObjectAction action = ObjectAction.IDLE;
    private int jumped = 0;
    private boolean isRight = true;

    private GameEngine engine;

    private boolean slash = false;

    private int level = 2;
    private int lives = 3;

    private final ObjectHandler handler;
    private final Texture tex;
    private final Animation animIdle, animRun, animIdleSword, animRunSword, animSlash, animJump, animDoubleJump;

    public Diluc(float x, float y, int scale, ObjectHandler handler, GameEngine engine) {
        super(x, y, ObjectID.HERO, WIDTH, HEIGHT, scale);
        this.handler = handler;
        this.engine = engine;

        tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getIdle());
        animIdleSword = new Animation(10, tex.getIdleSword());
        animRun = new Animation(5, tex.getRun());
        animRunSword = new Animation(5, tex.getRunSword());
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

            if (temp.getId() == ObjectID.SLIME) {
                Slime slime = (Slime) temp;
                if (getBounds().intersects(temp.getBounds()) && !(slime.getAction() == ObjectAction.DEATH)) {
                    setVelY(0);
                    setVelX(0);
                    if (level > 1) {
                        decreaseLevel();
                        slime.setAction(ObjectAction.DEATH);
                    }
                    else if (level == 1) {
                        action = ObjectAction.DEATH;
                    }
                }

                if (slash) {
                    if(!isRight) {
                        if (getBoundsAttackLeft().intersects(temp.getBounds())) {
//                            handler.removeObj(temp);
                            slime.setAction(ObjectAction.DEATH);
                        }
                    } else {
                        if (getBoundsAttackRight().intersects(temp.getBounds())) {
//                            System.out.println("Slime: " + slime);
//                            handler.removeObj(slime);
                            slime.setAction(ObjectAction.DEATH);
                        }
                    }
                }
            }
        }
    }

    private void death() {
        if (action == ObjectAction.DEATH) {
            level = 1;
            if (lives > 1) {
                decreaseLives();
                setX(32);
                setY(300);
                setAction(ObjectAction.RESPAWN);
            }
            else if (lives == 1){
                decreaseLives();
                engine.getGameUI().setGameStatus(GameStatus.GAME_OVER);
            }
        }
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        applyGravity();
        collision();
        death();

//        System.out.println("X: " + getX() + ",  Y: " + getY());

        animRun.runAnimation();
        animIdle.runAnimation();
        animJump.runAnimation();
        animDoubleJump.runAnimation();
        animIdleSword.runAnimation();
        animRunSword.runAnimation();
        animSlash.runAnimation();


    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.yellow);
//        g.fillRect((int) getX(), (int) getY(), (int) WIDTH, (int) HEIGHT);
        if (jumped == 0) {
            if (level >= 2) {
                switch (action) {
                    case RUN:
                        //                g.drawImage(diluc[8], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
                        if (!isRight) {
                            animRunSword.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animRunSword.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                        break;
                    case RESPAWN:
                    case IDLE:
                        if (!isRight) {
                            animIdleSword.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animIdleSword.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                        break;

                    case ATTACK:
                        if (!isRight) {
                            animSlash.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animSlash.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                        break;
                }
            } else {
                switch (action) {
                    case RUN:
                        if (!isRight) {
                            animRun.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animRun.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                        break;
                    case RESPAWN:
                    case IDLE:
                        if (!isRight) {
                            animIdle.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                        } else {
                            animIdle.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                        }
                        break;
                }
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
                (int) getY() + 15,
                5,
                (int) getHeight() -20);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX() + 15,
                (int) (getY() + 15),
                5,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsAttackLeft() {
        return new Rectangle((int) getX() - 15,
                (int) (getY() + 15),
                30,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsAttackRight() {
        return new Rectangle((int) (getX() + getWidth() - 15),
                (int) (getY() + 15),
                30,
                (int) (getHeight() - 20));
    }

    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
        g2.draw(getBoundsRight());
        g2.draw(getBoundsLeft());
        g2.draw(getBoundsTop());
        g2.draw(getBoundsAttackRight());
        g2.draw(getBoundsAttackLeft());

    }

    public int isJumped() {
        return jumped;
    }

    public void setJumped() {
        jumped++;
    }

    public ObjectAction getAction() {
        return action;
    }

    public void setAction(ObjectAction action) {
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

    public void setSlash(boolean slash) {
        this.slash = slash;
    }

    public int getLives() {
        return lives;
    }

    public void increaseLives(int lives) {
        this.lives++;
    }

    public void decreaseLives() {
        this.lives--;
        System.out.println("Sisa nyawa : " + lives);
    }

    public void increaseLevel(int lives) {
        this.level++;
    }

    public void decreaseLevel() {
        this.level--;
        System.out.println("Level : " + level);
    }
}
