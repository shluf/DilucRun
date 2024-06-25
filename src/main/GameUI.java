package main;

import content.ObjectHandler;
import main.condition.GameStatus;
import textures.LevelCreator;
import main.view.Camera;
import textures.Texture;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameUI extends JPanel {
    private float bgOneX = 0, bgTwoX = 0, bgThreeX = 0, bgFourX = 0, bgFiveX = 0;

    private final GameEngine engine;
    private final LevelCreator levelCreator;
    private final ObjectHandler handler;
    private final Camera cam;

    private GameStatus gameStatus = GameStatus.START_SCREEN;
    private GameStatus inGameStatus = GameStatus.RUNNING;
    private Font gameFont;

    private final Texture tex;

    public GameUI(GameEngine engine, ObjectHandler handler) {
        this.handler = handler;
        this.engine = engine;

        tex = GameEngine.getTexture();

        int screenOffset = GameEngine.getScreenOffset();
        cam = new Camera(0, screenOffset);

        levelCreator = new LevelCreator(handler, engine, this);

        try {
            InputStream in = getClass().getResourceAsStream("/assets/font/emulogic.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        if(gameStatus == GameStatus.START_SCREEN){
            g2.setFont(gameFont.deriveFont(50f));
            g2.setColor(Color.BLACK);
            g2.drawString("START", GameEngine.getWindowWidth()/2 - 150, GameEngine.getWindowHeight()/2);
        }
        else if(gameStatus == GameStatus.ABOUT_SCREEN){
            g2.setFont(gameFont.deriveFont(50f));
            g2.setColor(Color.WHITE);
            g2.drawString("ABOUT", GameEngine.getWindowWidth()/2, GameEngine.getWindowHeight()/2);
        }
        else if(gameStatus == GameStatus.HELP_SCREEN){
            g2.setFont(gameFont.deriveFont(50f));
            g2.setColor(Color.WHITE);
            g2.drawString("HELP", GameEngine.getWindowWidth()/2, GameEngine.getWindowHeight()/2);
        }
        else if(gameStatus == GameStatus.GAME_OVER){
            g2.setFont(gameFont.deriveFont(50f));
            g2.setColor(Color.BLACK);
            g2.drawString("GAME OVER", GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2);
        }
        else if (gameStatus == GameStatus.RUNNING){

            renderBackground(g);

            g2.translate(-cam.getX(), -cam.getY());
            handler.render(g2);
            g2.translate(cam.getX(), cam.getY());



            drawRemainingLives(g2);
            drawHeroLevel(g2);;
            drawMapLevel(g2);;
            drawAcquiredCoins(g2);
            drawKilledSlimes(g2);
            drawAcquiredScore(g2);
            drawShowArrow(g2);

//            if(gameStatus == GameStatus.PAUSED){
//                drawPauseScreen(g2);
//            }
//            else if(gameStatus == GameStatus.MISSION_PASSED){
//                drawVictoryScreen(g2);
//            }

            switch (inGameStatus) {
                case PAUSED -> drawPausedScreen(g2);
                case LEVEL_COMPLETED -> drawLevelCompleted(g2);
                case FINISHED -> drawVictory(g2);
            }
        }

        g2.dispose();


    }

    public void tick() {
        handler.tick();
        animateBackground();
        cam.tick(handler.getHero());
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getInGameStatus() {
        return inGameStatus;
    }

    public void setInGameStatus(GameStatus inGameStatus) {
        this.inGameStatus = inGameStatus;
    }

    public void createMap(int level) {
        boolean loaded = levelCreator.start(level);
        if(loaded){
            setGameStatus(GameStatus.RUNNING);
        }
        else
            setGameStatus(GameStatus.START_SCREEN);
    }

    private void animateBackground() {
        float camX = cam.getX();
        bgOneX = 0;
        bgTwoX = -camX * 0.008f;
        bgThreeX = -camX * 0.02f;
        bgFourX = -camX * 0.04f;
        bgFiveX = -camX * 0.06f;

        if (bgTwoX <= -GameEngine.getWindowWidth()) bgTwoX += GameEngine.getWindowWidth();
        if (bgThreeX <= -GameEngine.getWindowWidth()) bgThreeX += GameEngine.getWindowWidth();
        if (bgFourX <= -GameEngine.getWindowWidth()) bgFourX += GameEngine.getWindowWidth();
        if (bgFiveX <= -GameEngine.getWindowWidth()) bgFiveX += GameEngine.getWindowWidth();
    }

    private void renderBackground(Graphics g) {

        g.drawImage(tex.getBackgroundOne(), (int) bgOneX, 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);

        g.drawImage(tex.getBackgroundTwo(), (int) bgTwoX, 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);
        g.drawImage(tex.getBackgroundTwo(), (int) bgTwoX + GameEngine.getWindowWidth(), 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);

        g.drawImage(tex.getBackgroundThree(), (int) bgThreeX, 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);
        g.drawImage(tex.getBackgroundThree(), (int) bgThreeX + GameEngine.getWindowWidth(), 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);

        g.drawImage(tex.getBackgroundFour(), (int) bgFourX, 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);
        g.drawImage(tex.getBackgroundFour(), (int) bgFourX + GameEngine.getWindowWidth(), 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);

        g.drawImage(tex.getBackgroundFive(), (int) bgFiveX, 0, GameEngine.getWindowWidth(), GameEngine.getWindowHeight(), null);
        g.drawImage(tex.getBackgroundFive(), (int) bgFiveX + GameEngine.getWindowWidth(), 0, GameEngine.getScreenWidth(), GameEngine.getWindowHeight(), null);

    }

    private void drawAcquiredCoins(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString(("Coins: "+handler.getCoinPicked().size()+"/"+levelCreator.getMinimumCoin()), 300, 40);
    }

    private void drawRemainingLives(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(10f));
        g2.setColor(Color.WHITE);
        g2.drawString("Health: " + handler.getHero().getLives(), 50, 60);
    }

    public void drawShowArrow(Graphics2D g2){
        g2.setFont(getGameFont().deriveFont(10f));
        g2.setColor(Color.WHITE);
        g2.drawString("Arrows: " + handler.getHero().getArrow(), 50, 80);
    }

    private void drawHeroLevel(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(10f));
        g2.setColor(Color.WHITE);
        g2.drawString("Hero Level: " + handler.getHero().getLevel(), 50, 40);
    }

    private void drawMapLevel(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString("Map: " + engine.getMapLevel(), GameEngine.getScreenWidth()-100, 40);
    }

    private void drawKilledSlimes(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString("Slime: " + handler.getDeathSlime().size(), 575, 40);
    }

    private void drawAcquiredScore(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString("" +handler.getSlimePoint() * handler.getHero().getLives(), 40, GameEngine.getWindowHeight()-40);
    }

    private void drawVictory(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(50f));
        g2.setColor(Color.WHITE);
        g2.drawString("Victory", GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2);

        g2.setFont(getGameFont().deriveFont(20f));
        g2.drawString("Level 1 " + engine.getScore(1), GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2+50);
        g2.drawString("Level 2 " + engine.getScore(2), GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2+75);
        g2.drawString("Level 3 " + engine.getScore(3), GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2+100);
        g2.drawString("Total Score ", GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2+130);
        g2.drawString("" + engine.getTotalScore(), GameEngine.getWindowWidth()/2-250 , GameEngine.getWindowHeight()/2+155);
    }

    private void drawPausedScreen(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString("Game Paused", GameEngine.getScreenWidth()/2 -40, GameEngine.getWindowHeight()-40);
    }

    private void drawLevelCompleted(Graphics2D g2) {
        g2.setFont(getGameFont().deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString("Level Completed", GameEngine.getScreenWidth()/2, 40);
    }

    public Font getGameFont() {
        return gameFont;
    }
}
