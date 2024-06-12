package main;

import content.ObjectHandler;
import main.condition.GameStatus;
import main.menu.LevelCreator;
import main.view.Camera;
import textures.Texture;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameUI extends JPanel {
    private int screenOffset;

    private float bgOneX = 0, bgTwoX = 0, bgThreeX = 0, bgFourX = 0, bgFiveX = 0;

    private LevelCreator levelCreator;
    private final ObjectHandler handler;
    private Camera cam;

    private GameStatus gameStatus = GameStatus.START_SCREEN;
    private Font gameFont;

    private Texture tex;

    public GameUI(GameEngine engine, ObjectHandler handler) {
        this.handler = handler;

        tex = GameEngine.getTexture();

        screenOffset = GameEngine.getScreenOffset();
        cam = new Camera(0, screenOffset);

        levelCreator = new LevelCreator(handler, engine);
        levelCreator.start();

        try {
            InputStream in = getClass().getResourceAsStream("/assets/font/mario-font.ttf");
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
            g2.drawString("START", GameEngine.getWindowWidth()/2, GameEngine.getWindowHeight()/2);
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
            g2.drawString("GAME OVER", GameEngine.getWindowWidth()/2 , GameEngine.getWindowHeight()/2);
        }
        else {
            renderBackground(g);

            g2.translate(-cam.getX(), -cam.getY());
            handler.render(g2);
            g2.translate(cam.getX(), cam.getY());



//            System.out.println("HeroX: " + handler.getHero().getX());



//            drawPoints(g2);
//            drawRemainingLives(g2);
//            drawAcquiredCoins(g2);
//            drawRemainingTime(g2);

//            if(gameStatus == GameStatus.PAUSED){
//                drawPauseScreen(g2);
//            }
//            else if(gameStatus == GameStatus.MISSION_PASSED){
//                drawVictoryScreen(g2);
//            }
        }

        g2.dispose();


    }

    public void tick() {
        if (handler.getHero() != null) {
            handler.tick();
            animateBackground();
            cam.tick(handler.getHero());
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
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


}
