package main;

import utility.Windows;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameEngine extends Canvas implements Runnable {
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;

    private static final String NAME = "Diluc Run";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    private boolean running;

    private Thread thread;

    public GameEngine() {
        initialize();
    }

    public static void main(String[] args) {
        new GameEngine();
    }

    private void initialize() {
        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);

        start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        this.running=true;
    }
    private synchronized void stop() {
        try {
            thread.join();
            this.running=false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
         double amountOfTick = NUM_TICKS;
         double ns = NANOS_PER_SEC / amountOfTick;
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

    }

    private void  render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        g.dispose();
        buf.show();

    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}