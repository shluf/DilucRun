package content.block;

import content.GameObject;
import content.ObjectID;

import java.awt.*;

public class Coin extends GameObject {
    public Coin(float x, float y, ObjectID id, float width, float height, int scale) {
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
        return null;
    }
}
