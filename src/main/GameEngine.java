package main;

import content.ObjectHandler;
import main.condition.GameStatus;
import main.view.Windows;
import textures.Texture;

import java.awt.*;

public class GameEngine extends Canvas implements Runnable {
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;

    private static final String NAME = "Diluc Run";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_HEIGHT = WINDOW_HEIGHT;
    private static final int SCREEN_OFFSET = 16 * 3;

    private boolean running;
    private GameUI gameUI;

    private int mapLevel = 0;

    private Thread thread;
    private ObjectHandler handler;
    private static Texture tex;

    public GameEngine() {
        initialize();
    }

    public static void main(String[] args) {
        new GameEngine();
    }

    private void initialize() {

        tex = new Texture();

        handler = new ObjectHandler();
        gameUI = new GameUI(this, handler);
        gameUI.addKeyListener(new GameKey(handler, gameUI, this));

        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, gameUI);


        start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        this.running = true;
    }

    private synchronized void stop() {
        try {
            thread.join();
            this.running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = NANOS_PER_SEC / NUM_TICKS;

        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
             long now = System.nanoTime();
             delta += (now - lastTime) / ns;
             lastTime = now;

             while (delta >= 1) {
                 tick();
                 updates++;
                 delta--;
             }

             if (running) {
                 render();
                 frames++;
             }

             if (System.currentTimeMillis() - timer > MILLIS_PER_SEC) {
                 timer += MILLIS_PER_SEC;
                 System.out.println("FPS: " + frames + " TPS: " + updates);
                 updates = 0;
                 frames = 0;
             }
        }

        stop();
    }

    private void tick() {
        if (handler.getHero() != null) {
            gameUI.tick();
        }
    }

    private void render() {
        gameUI.repaint();
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getScreenOffset() {
        return SCREEN_OFFSET;
    }

    public static Texture getTexture() {
        return tex;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void nextMapLevel() {
        handler.cleanHandler();
        this.mapLevel++;
        loadMap(mapLevel);
    }

    public boolean previousMapLevel() {
        if (mapLevel > 0) {
            handler.cleanHandler();
            this.mapLevel--;
            loadMap(mapLevel);
            return true;
        }
        return false;
    }

    public void loadMap(int level) {
        gameUI.createMap(level);
    }

    public int getMapLevel() {
        return mapLevel;
    }
}