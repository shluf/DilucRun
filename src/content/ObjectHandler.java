package content;

import content.enemy.Slime;
import content.hero.Diluc;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {
    private final List<GameObject> gameObjs;
    private Diluc hero;
    private Slime slime;

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

    public int setSlime(Slime slime)

    public void removeHero() {
        if (hero == null) {
            return;
        }
        removeObj(hero);
        this.hero = null;
    }

    public void removeSlime(){
        if ()
    }

    public Diluc getHero() {
        return hero;
    }

    public void allObject() {
        for (GameObject obj: gameObjs) {
            System.out.println(obj);
        }
    }
}
