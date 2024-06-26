package main;

import content.ObjectHandler;
import main.condition.GameStatus;
import main.condition.Notify;
import main.view.Windows;
import textures.Texture;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameEngine extends Canvas implements Runnable, Notify {
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;

    private static final String NAME = "Diluc Run";
    private static final  String HIGHSCORE_FILE = "src/highscore.txt";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_OFFSET = 16 * 3;

    private boolean running;

    private int mapLevel = 1;
    private static final int totalLevel = 3;
    private HashMap<Integer, Integer> highScore;
    private HashMap<Integer, Integer> score;
    private int notify = 0;

    private GameUI gameUI;
    private Thread thread;
    private ObjectHandler handler;
    private static Texture tex;

    private MusicPlayer music;

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

        score = new HashMap<>();
        getHighScore();

        music = new MusicPlayer("tetris.wav");

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
                 if (gameUI.getInGameStatus() != GameStatus.PAUSED) {
                     tick();
                 }
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
        if (handler.getHero() != null) {
            gameUI.tick();
        }

        if (gameUI.getGameStatus() == GameStatus.RUNNING && gameUI.getInGameStatus() == GameStatus.RUNNING) {
            music.play();
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

    public static int getScreenOffset() {
        return SCREEN_OFFSET;
    }

    public static Texture getTexture() {
        return tex;
    }

    public static int getTotalLevel() {
        return  totalLevel;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void nextMapLevel() {
        handler.cleanHandler();
        this.mapLevel++;
        loadMap(mapLevel);
    }

    public void restartLevel() {
        handler.cleanHandler();
        this.mapLevel = 1;
        gameUI.setInGameStatus(GameStatus.RUNNING);
        loadMap(1);
    }

    public boolean previousMapLevel() {
        if (mapLevel > 1) {
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

    public void saveHighScores(HashMap<Integer, Integer> highScores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
            for (Map.Entry<Integer, Integer> entry : highScores.entrySet()) {
                if (!highScore.isEmpty()) {
                    if (entry.getValue() > highScore.get(entry.getKey())) {
                        writer.write(entry.getKey() + "=" + entry.getValue());
                        writer.newLine();

                        setNotify(1);
                        System.out.println("New Highscore!!!");
                    } else {
                        writer.write(entry.getKey() + "=" + highScore.get(entry.getKey()));
                        writer.newLine();
                    }
                }
            }
            if (mapLevel < totalLevel) {
                for (int i = mapLevel+1; i <= totalLevel; i++) {
                    writer.write(i + "=" + (highScore.get(i) == null ? 0 : highScore.get(i)));
                    writer.newLine();
                }
            }
        }
    }

    public static HashMap<Integer, Integer> loadHighScores() throws IOException {
        HashMap<Integer, Integer> highScores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    int key = Integer.parseInt(parts[0]);
                    int value = Integer.parseInt(parts[1]);
                    highScores.put(key, value);
                }
            }
        }
        return highScores;
    }

    public HashMap<Integer, Integer> getScore() {
        return score;
    }

    public int getScore(int level) {
        return (score.get(level) == null ? 0 : score.get(level));
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (Map.Entry<Integer, Integer> entry : score.entrySet()) {
            totalScore += entry.getValue();
        }
        return totalScore;
    }

    public void setScore(int level, int score) {
        this.score.put(level, score);
    }

    public void getHighScore() {
        highScore = new HashMap<>();
        try {
            highScore = loadHighScores();
            System.out.println("High scores level 1: "+ highScore.get(1));
            System.out.println("High scores level 2: "+ highScore.get(2));
            System.out.println("High scores level 3: "+ highScore.get(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore(int mapLevel) {
        return (highScore.get(mapLevel) == null ? 0 : highScore.get(mapLevel));
    }

    public MusicPlayer getMusic() {
        return music;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }
}