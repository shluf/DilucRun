package content.block;

import content.GameObject;
import content.ObjectID;

import java.awt.*;

public class Pipe extends GameObject {
    private boolean enterable;

    public Pipe(float x, float y, float width, float height, int scale, boolean enterable) {
        super(x, y, ObjectID.PIPE, width, height, scale);
        this.enterable = enterable;
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
}
