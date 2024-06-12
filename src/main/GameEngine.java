package main;

import content.ObjectHandler;
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
        gameUI.addKeyListener(new GameKey(handler, gameUI));


/////////////////////////////////////////////////////////////////////////////////////////////////////////

//        handler.setHero(new Diluc(32,32,2, handler, this));
//        System.out.println("Sisa nyawa : " + handler.getHero().getLives());
//
//        handler.addObj(new Slime(32 * 50, 32 * 11, 1, false, handler));
//        handler.addObj(new Slime(32 * 57, 32 * 11, 1, false, handler));
//        handler.addObj(new Slime(32 * 11, 32 * 13, 1, true, handler));
//
//        handler.addObj(new Hollow(handler, 120));
//
//        for (int i = 8; i < 23; i++) {
//            if (i != 16 && i!=17 && i != 18) {
//                handler.addObj(new Tile(i * 32, 32 * 10, 32, 32, 1));
//            }
//        }
//
//        handler.addObj(new Tile(17 * 32, 32 * 14, 32, 32, 1));
//
//        handler.addObj(new Tile(90 * 32, 32 * 12, 32, 32, 1));
//        handler.addObj(new Tile(45 * 32, 32 * 12, 32, 32, 1));
//
//        handler.addObj(new Tile(10 * 32, 32 * 13, 32, 32, 1));
//        handler.addObj(new Tile(10 * 32, 32 * 14, 32, 32, 1));
//
//        for (int i = 32; i < 100; i++) {
//            if (i != 37 && i!=38) {
//                handler.addObj(new Tile(i * 32, 32 * 13, 32, 32, 1));
//            }
//        }
//
//        for (int i = 0; i < 30; i++) {
//            handler.addObj(new Tile(i*32,32*15,32,32,1));
//        }
//
//        handler.allObject();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
//                 System.out.println("FPS: " + frames + " TPS: " + updates);
                 updates = 0;
                 frames = 0;
             }
        }

        stop();
    }

    private void tick() {
        gameUI.tick();
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
}