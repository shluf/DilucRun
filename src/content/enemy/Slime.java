package content.enemy;

import content.GameObject;
import content.ObjectAction;
import content.ObjectID;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Slime extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 25;

    private ObjectAction action = ObjectAction.IDLE;
    private boolean isRight;

    private Texture tex;
    private Animation animIdle, animRun, animDeath;

    public Slime(float x, float y, int scale, boolean isRight) {
        super(x, y-(int) (HEIGHT/2), ObjectID.SLIME, WIDTH * 2, HEIGHT * 2, scale);
        this.isRight = isRight;

        System.out.println(scale);

        tex = GameEngine.getTexture();

        animIdle = new Animation(10, tex.getSlimeIdle());
        animRun = new Animation(5, tex.getSlimeMove());
        animDeath = new Animation(5, tex.getSlimeDeath());
    }

    @Override
    public void tick() {


        animRun.runAnimation();
        animIdle.runAnimation();
        animDeath.runAnimation();
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
        }

        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX() + 15,
                (int) getY() + 30,
                (int) getWidth() - 30,
                (int) getHeight() - 30);
    }


    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
    }



    public static int getSlimeHeight() {
        return HEIGHT;
    }

    public static int getSlimeWidth() {
        return WIDTH;
    }
}
