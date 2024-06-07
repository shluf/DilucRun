package main;

import content.ObjectHandler;
import content.block.Hollow;
import content.block.Tile;
import content.enemy.Slime;
import content.hero.Diluc;
import main.condition.GameStatus;
import main.view.Camera;
import main.view.Windows;
import textures.Texture;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameEngine extends Canvas implements Runnable {
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;

    private static final String NAME = "Diluc Run";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_HEIGHT = WINDOW_HEIGHT;
    private static final int SCREEN_OFFSET = 16*3;

    private float bgOneX = 0, bgTwoX = 0, bgThreeX = 0, bgFourX = 0, bgFiveX = 0;

    private GameStatus gameStatus;
    private boolean running;

    private Thread thread;
    private ObjectHandler handler;
    private Camera cam;
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
        this.addKeyListener(new GameKey(handler));

        handler.setHero(new Diluc(32 * 20,32,2, handler));

        handler.addObj(new Slime(32 * 50, 32 * 11, 1, false, handler));

        handler.addObj(new Hollow());

        for (int i = 8; i < 23; i++) {
            if (i != 16 && i!=17 && i != 18) {
                handler.addObj(new Tile(i * 32, 32 * 10, 32, 32, 1));
            }
        }

        handler.addObj(new Tile(17 * 32, 32 * 14, 32, 32, 1));
//        handler.addObj(new Tile(10 * 32, 32 * 11, 32, 32, 1));
        handler.addObj(new Tile(90 * 32, 32 * 12, 32, 32, 1));
        handler.addObj(new Tile(45 * 32, 32 * 12, 32, 32, 1));

        handler.addObj(new Tile(10 * 32, 32 * 13, 32, 32, 1));
        handler.addObj(new Tile(10 * 32, 32 * 14, 32, 32, 1));

        for (int i = 32; i < 100; i++) {
            if (i != 37 && i!=38) {
                handler.addObj(new Tile(i * 32, 32 * 13, 32, 32, 1));
            }
        }

        for (int i = 0; i < 30; i++) {
            handler.addObj(new Tile(i*32,32*15,32,32,1));
        }

        cam = new Camera(0, SCREEN_OFFSET);
        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);

        handler.allObject();

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
        handler.tick();
        cam.tick(handler.getHero());

        float playerSpeed = handler.getHero().getX();
        bgOneX = 0;
        bgTwoX = -playerSpeed * 0.008f;
        bgThreeX = -playerSpeed * 0.02f;
        bgFourX = -playerSpeed * 0.04f;
        bgFiveX = -playerSpeed * 0.06f;

//        if (bgOneX <= -WINDOW_WIDTH) bgOneX += WINDOW_WIDTH;
        if (bgTwoX <= -WINDOW_WIDTH) bgTwoX += WINDOW_WIDTH;
        if (bgThreeX <= -WINDOW_WIDTH) bgThreeX += WINDOW_WIDTH;
        if (bgFourX <= -WINDOW_WIDTH) bgFourX += WINDOW_WIDTH;
        if (bgFiveX <= -WINDOW_WIDTH) bgFiveX += WINDOW_WIDTH;
    }

    private void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;

//        g.setColor(Color.BLACK);
//        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
//        g.drawImage(tex.getDilucTex()[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null );
        // Draw backgrounds with parallax effect

        g.drawImage(tex.getBackgroundOne(), (int) bgOneX, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
//        g.drawImage(tex.getBackgroundOne(), (int) bgOneX + WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        g.drawImage(tex.getBackgroundTwo(), (int) bgTwoX, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundTwo(), (int) bgTwoX + WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        g.drawImage(tex.getBackgroundThree(), (int) bgThreeX, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundThree(), (int) bgThreeX + WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        g.drawImage(tex.getBackgroundFour(), (int) bgFourX, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundFour(), (int) bgFourX + WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        g.drawImage(tex.getBackgroundFive(), (int) bgFiveX, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundFive(), (int) bgFiveX + WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        g2.translate(cam.getX(), cam.getY());
        handler.render(g);
        g2.translate(-cam.getX(), -cam.getY());

        g.dispose();
        buf.show();

    }

    public GameStatus getGameStatus() {
        return gameStatus;
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


    public static Texture getTexture() {
        return tex;
    }

}