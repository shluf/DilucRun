package content.hero;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import content.block.Chest;
import content.block.Gate;
import content.enemy.Slime;
import main.GameEngine;
import main.condition.GameStatus;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Diluc extends GameObject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 37;

    private final GameEngine engine;
    private final ObjectHandler handler;
    private final Animation animIdle, animRun, animIdleSword, animRunSword, animSlash, animJump, animDoubleJump, animInteract;

    private ObjectAction action = ObjectAction.IDLE;
    private int jumped = 0;
    private boolean isRight = true;
    private boolean slash = false;

    private int level = 1;
    private int lives = 3;

    private boolean levelDecreased = false;


    public Diluc(float x, float y, int scale, ObjectHandler handler, GameEngine engine) {
        super(x, y, ObjectID.HERO, WIDTH, HEIGHT, scale);
        this.handler = handler;
        this.engine = engine;

        Texture tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getIdle());
        animIdleSword = new Animation(10, tex.getIdleSword());
        animRun = new Animation(5, tex.getRun());
        animRunSword = new Animation(5, tex.getRunSword());
        animSlash = new Animation(5, tex.getSlash());
        animJump = new Animation(10, tex.getJump());
        animDoubleJump = new Animation(5, tex.getDoubleJump());
        animInteract = new Animation(5, tex.getDilucInteract());
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
                    setX(temp.getX() - getWidth() + 25);
                }

                if (getBoundsLeft().intersects(temp.getBounds())) {
                    setX(temp.getX() + getWidth() / 5 - 10);
                }
            }

            if (temp.getId() == ObjectID.CHEST && action == ObjectAction.INTERACT) {
                Chest chest = (Chest) temp;
                if (getBoundsRight().intersects(chest.getOuterBounds()) || getBoundsLeft().intersects(chest.getOuterBounds())){
                    if (!chest.isOpened()) {
                        increaseLevel();
                    }
                    chest.setOpened(true);
                }
            }

            if (temp.getId() == ObjectID.GATE) {
                Gate gate = (Gate) temp;
                if (getBounds().intersects(gate.getOuterBounds()) && action == ObjectAction.INTERACT) {
                    gate.getAnimGate().runSingleAnimation();
                    gate.setOpened(true);
                    if (gate.getAnimGate().isFinished()) {
                        gate.setEnterable(true);
                    }
                }
                if (getBounds().intersects(gate.getBounds()) && gate.isEnterable()) {
                    System.out.println("Next Level");
                    setVelX(0);
                }
            }

            if (temp.getId() == ObjectID.SLIME) {
                Slime slime = (Slime) temp;
                if (getBoundsRight().intersects(slime.getBoundsAttackLeft())) {
                    slime.setAttackRight(true);
                }

                else if (getBoundsLeft().intersects(slime.getBoundsAttackRight())) {
                    slime.setAttackRight(false);
                }
                if ((getBounds().intersects(slime.getBoundsAttackLeft()) || getBounds().intersects(slime.getBoundsAttackRight())) && !(slime.getAction() == ObjectAction.DEATH)) {

                    if (slime.getAnimAttack().isFinished()) {
                        slime.getAnimAttack().reset();
                    }
                    setVelX(0);
                    slime.setVelX(0);
                    slime.getAnimAttack().runSingleAnimation();
                    slime.setAction(ObjectAction.ATTACK);

                    if (level > 1) {
                        if (slime.getAnimAttack().isFinished()) {
                            decreaseLevel();
                        }
                    }
                    else if (level == 1) {
                        if (slime.getAnimAttack().isFinished()) {
                            action = ObjectAction.DEATH;
                        }
                    }
                }

                if (slash) {
                    if (slime.getLives() >= 1) {
                        if (!isRight) {
                            if (getBoundsAttackLeft().intersects(temp.getBounds())) {
                                slime.decreaseLives();
                            }
                        } else {
                            if (getBoundsAttackRight().intersects(temp.getBounds())) {
//                                slime.setAction(ObjectAction.DEATH);
//                                System.out.println("Slime: " + slime);
                                slime.decreaseLives();
                                handler.addDeathSlime(slime);
                                System.out.println(handler.getDeathSlime());
                            }
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
                if (!levelDecreased) {
                    decreaseLives();
                    levelDecreased = true;
                }
                setX(32);
                setY(300);
                setAction(ObjectAction.RESPAWN);
            }
            else if (lives == 1){
                decreaseLives();
                engine.getGameUI().setGameStatus(GameStatus.GAME_OVER);
            }
        }
        levelDecreased = false;
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());
        setY(getVelY() + getY());
        applyGravity();
        collision();
        death();

//        System.out.println("X: " + getX() + ",  Y: " + getY());
//        System.out.println("Slime: " + handler.getDeathSlime().size());

        animRun.runAnimation();
        animIdle.runAnimation();
        animJump.runAnimation();
        animDoubleJump.runAnimation();
        animIdleSword.runAnimation();
        animRunSword.runAnimation();
        animSlash.runAnimation();
        animInteract.runSingleAnimation();


    }

    @Override
    public void render(Graphics g) {
        if (jumped == 0) {
            if (action== ObjectAction.INTERACT) {
                if (!isRight) {
                    animInteract.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight());
                } else {
                    animInteract.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
                }
            }
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

        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (getX() + getWidth() / 2 - (getWidth() / 8)),
                (int) (getY() + getHeight()/2),
                (int) getWidth()/4,
                (int) getHeight()/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (getX() + getWidth() / 2 - (getWidth() / 8)),
                (int) getY() + 10,
                (int) getWidth()/4,
                (int) getHeight()/2 - 10);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) (getX() + getWidth() - 30),
                (int) getY() + 15,
                5,
                (int) getHeight() -20);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX() + 25,
                (int) (getY() + 15),
                5,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsAttackLeft() {
        return new Rectangle((int) getX() - 10,
                (int) (getY() + 15),
                40,
                (int) (getHeight() - 20));
    }

    public Rectangle getBoundsAttackRight() {
        return new Rectangle((int) (getX() + getWidth()/2 + 20),
                (int) (getY() + 15),
                40,
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

    public void increaseLives() {
        this.lives++;
    }

    public void decreaseLives() {
        this.lives--;
        System.out.println("Sisa nyawa : " + lives);
    }

    public void increaseLevel() {
        this.level++;
        System.out.println("Level : " + level);
    }

    public int getLevel() {
        return level;
    }

    public void decreaseLevel() {
        this.level--;
        System.out.println("Level : " + level);
    }

    public Animation getAnimInteract() {
        return animInteract;
    }
}
