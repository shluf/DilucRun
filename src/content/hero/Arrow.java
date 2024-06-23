package content.hero;

import content.GameObject;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;
import textures.Animation;
import textures.Texture;

import java.awt.*;

public class Arrow extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private final ObjectHandler handler;
    private final boolean isRight;

    private final Animation animArrow;

    private int distance = 0;

    public Arrow(float x, float y, int scale, boolean isRight, ObjectHandler handler) {
        super(x, y, ObjectID.ARROW, WIDTH, HEIGHT, scale);
        this.isRight = isRight;
        this.handler = handler;

        Texture tex = GameEngine.getTexture();
        animArrow = new Animation(5, tex.getArrows());
    }

    @Override
    public void tick() {
        setX(getVelX() + getX());

        if (isRight) {
            setVelX(6);
        } else {
            setVelX(-6);
        }

        distance += 5;

        if (distance > 15 * 32) {
            handler.removeObj(this);
        }

        animArrow.runAnimation();
    }

    @Override
    public void render(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g.setColor(Color.red);
//        g2.draw(getBounds());
        if (isRight) {
            animArrow.drawAnimation(g, (int) getX() + 32, (int) getY() + 20, 50, 50);
        } else {
            animArrow.drawAnimation(g, (int) getX() + 32 + 25, (int) getY() + 20, -50, 50);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX()+32, (int) getY()+40, (int) getWidth(), (int) getHeight()-16);
    }
}
