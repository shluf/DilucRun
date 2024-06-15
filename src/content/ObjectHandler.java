package content;

import content.block.Coin;
import content.enemy.Slime;
import content.hero.Diluc;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {
    private ArrayList<GameObject> gameObjs;
    private ArrayList<Slime> deathSlime;
    private ArrayList<Coin> coin;
    private Diluc hero;

    public ObjectHandler() {
        gameObjs = new ArrayList<>();
        deathSlime = new ArrayList<>();
    }

    public synchronized  void tick() {
        for (GameObject obj : gameObjs) {
            obj.tick();
        }
    }

    public synchronized  void render(Graphics g) {
        for (GameObject obj: gameObjs) {
            obj.render(g);
        }
    }

    public synchronized  void addObj(GameObject obj) {
        gameObjs.add(obj);
    }

    public synchronized  void removeObj(GameObject obj) {
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

    public ArrayList<Slime> getDeathSlime() {
        return deathSlime;
    }

    public void addDeathSlime(Slime deathSlime) {
        this.deathSlime.add(deathSlime);
    }

    public void allObject() {
        for (GameObject obj: gameObjs) {
            System.out.println(obj);
        }
    }

    public void cleanHandler() {
        hero = null;
        gameObjs.clear();
        deathSlime.clear();
//        coin.clear();
    }
}
