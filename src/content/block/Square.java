package content.block;

import content.GameObject;
import content.ObjectID;

import java.awt.*;

public class Square extends GameObject {

    public Square(float x, float y, ObjectID id, float width, float height, int scale) {
        super(x, y, id, width, height, scale);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    protected void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
    }
}
