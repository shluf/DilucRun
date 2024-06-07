package content.block;

import content.GameObject;
import content.ObjectID;
import main.GameEngine;

import java.awt.*;

public class Hollow extends GameObject {
    private GameEngine engine;

    public Hollow() {
        super(0, 0, ObjectID.HOLLOW, 1, 1, 1);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY() + engine.getHeight(), (int) getWidth() + engine.getWidth(), (int) getHeight());
    }
}
