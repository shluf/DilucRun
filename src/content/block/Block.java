package content.block;

import content.GameObject;
import content.ObjectID;

import java.awt.*;

public class Block extends GameObject {
    public Block(float x, float y, float width, float height, int scale) {
        super(x, y, ObjectID.BLOCK, width, height, scale);
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
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
