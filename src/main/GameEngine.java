package main;

import content.ObjectHandler;
import content.block.Block;
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

        handler.setHero(new Diluc(32,32,1, handler));
        for (int i = 8; i < 23; i++) {
            if (i != 16 && i!=17 && i != 18) {
                handler.addObj(new Block(i * 32, 32 * 10, 32, 32, 1));
            }
        }

        for (int i = 32; i < 48; i++) {
            if (i != 37 && i!=38) {
                handler.addObj(new Block(i * 32, 32 * 13, 32, 32, 1));
            }
        }

        for (int i = 0; i < 30; i++) {
            handler.addObj(new Block(i*32,32*15,32,32,1));
        }

        cam = new Camera(0, SCREEN_OFFSET);
        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);

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
        handler.tick();
        cam.tick(handler.getHero());
    }

    private void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
//        g.drawImage(tex.getDilucTex()[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null );
        g.drawImage(tex.getBackgroundOne(), 0,0,(int) WINDOW_WIDTH,(int) WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundTwo(), 0,0,(int) WINDOW_WIDTH,(int) WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundThree(), 0,0,(int) WINDOW_WIDTH,(int) WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundFour(), 0,0,(int) WINDOW_WIDTH,(int) WINDOW_HEIGHT, null);
        g.drawImage(tex.getBackgroundFive(), 0,0,(int) WINDOW_WIDTH,(int) WINDOW_HEIGHT, null);

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