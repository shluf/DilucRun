package content;

import content.hero.Diluc;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {
    private final List<GameObject> gameObjs;
    private Diluc hero;

    public ObjectHandler() {
        gameObjs = new LinkedList<>();
    }

    public void tick() {
        for (GameObject obj : gameObjs) {
            obj.tick();
        }
    }

    public void render(Graphics g) {
        for (GameObject obj: gameObjs) {
            obj.render(g);
        }
    }

    public void addObj(GameObject obj) {
        gameObjs.add(obj);
    }

    public void removeObj(GameObject obj) {
        gameObjs.remove(obj);
    }

    public List<GameObject> getGameObjs() {
        return gameObjs;
    }

    public int setHero(Diluc diluc) {
        if (this.hero != null) {
            return -1;
        }

        addObj(diluc);
        this.hero = diluc;
        return 0;
    }

    public int removeHero() {
        if (hero == null) {
            return -1;
        }
        removeObj(hero);
        this.hero = null;
        return 0;
    }

    public Diluc getHero() {
        return hero;
    }
}
