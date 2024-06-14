package content.block;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import main.GameEngine;

import java.awt.*;

public class Hollow extends GameObject {
    private ObjectHandler handler;

    public Hollow(ObjectHandler handler, int blocks) {
        super(32 *  -15, GameEngine.getWindowHeight()+(int)(GameEngine.getWindowHeight()/2), ObjectID.HOLLOW, 32*blocks, 1, 1);
        this.handler = handler;
    }

    @Override
    public void tick() {
        deathLine();
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.BLUE);
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY() , (int) getWidth(), (int) getHeight());
    }

    private void deathLine() {
        for (int i = 0; i < handler.getGameObjs().size(); i++) {
            GameObject temp = handler.getGameObjs().get(i);
            if (temp.getId() == ObjectID.HERO) {
                if (getBounds().intersects(temp.getBounds())) {
                    handler.getHero().setAction(ObjectAction.DEATH);
                }
            }
        }
    }
}
