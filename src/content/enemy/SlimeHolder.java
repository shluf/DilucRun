package content.enemy;

import content.GameObject;
import content.ObjectID;

import java.awt.*;

public class SlimeHolder extends GameObject {

    public SlimeHolder(float x, float y, float width, float height, int scale) {
        super(x, y, ObjectID.SLIMEHOLDER, width, height, scale);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
//        showBounds(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    private void showBounds(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.red);
        g2.draw(getBounds());
    }
}
