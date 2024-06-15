package content.block;

import content.GameObject;
import content.ObjectAction;
import content.ObjectHandler;
import content.ObjectID;
import content.hero.Diluc;
import main.GameEngine;

import java.awt.*;

public class Hollow extends GameObject {
    private ObjectHandler handler;
    private GameEngine engine;

    public Hollow(ObjectHandler handler, int blocks, GameEngine engine) {
        super(0, GameEngine.getWindowHeight()+(int)(GameEngine.getWindowHeight()/2), ObjectID.HOLLOW, 32*blocks, 30, 1);
        this.handler = handler;
        this.engine = engine;
    }

    @Override
    public void tick() {
        deathLine();
    }

    @Override
    public void render(Graphics g) {
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY() , (int) getWidth(), (int) getHeight());
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(-30, 0, (int) getHeight(), GameEngine.getWindowHeight());
    }

    private void deathLine() {
        for (int i = 0; i < handler.getGameObjs().size(); i++) {
            GameObject temp = handler.getGameObjs().get(i);
            if (temp.getId() == ObjectID.HERO) {
                Diluc hero = (Diluc) temp;
                if (getBounds().intersects(temp.getBounds())) {
                    handler.getHero().setAction(ObjectAction.DEATH);
                }
                if (getBoundsLeft().intersects(hero.getBoundsRight())) {
                    boolean loaded = engine.previousMapLevel();
                    if (!loaded) {
                        hero.setX(0);
                        hero.setVelY(0);
                    }
                }
            }
        }
    }
}
