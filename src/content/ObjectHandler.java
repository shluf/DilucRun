package content;

import content.block.Coin;
import content.enemy.Slime;
import content.hero.Diluc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectHandler {
    private final List<GameObject> gameObjs;
    private final List<Slime> deathSlime;
    private Diluc hero;

    public ObjectHandler() {
        gameObjs = new ArrayList<>();
        deathSlime = new ArrayList<>();
    }

    public synchronized void tick() {

        List<GameObject> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(gameObjs);
        }
        
        for (GameObject obj : snapshot) {
            obj.tick();
        }
    }

    public synchronized void render(Graphics g) {

//        List<GameObject> snapshot;
//        synchronized (this) {
//            snapshot = new ArrayList<>(gameObjs);
//        }
        for (GameObject obj : gameObjs) {
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
        return new ArrayList<>(gameObjs);
    }

    public int setHero(Diluc diluc) {
        if (this.hero != null) {
            return -1;
        }
        addObj(diluc);
        this.hero = diluc;
        return 0;
    }

    public void removeHero() {
        if (hero == null) {
            return;
        }
        removeObj(hero);
        this.hero = null;
    }

    public Diluc getHero() {
        return hero;
    }

    public List<Slime> getDeathSlime() {
        return new ArrayList<>(deathSlime);
    }

    public void addDeathSlime(Slime deathSlime) {
        this.deathSlime.add(deathSlime);
    }

    public void cleanHandler() {
        hero = null;
        gameObjs.clear();
        deathSlime.clear();
    }
}
