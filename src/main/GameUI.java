package main;

import content.ObjectHandler;
import content.block.Hollow;
import content.block.Tile;
import content.enemy.Slime;
import content.hero.Diluc;
import main.condition.GameStatus;
import main.view.Camera;
import textures.Texture;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameUI extends JPanel {
    private int screenOffset;

    private GameEngine engine;

    private float bgOneX = 0, bgTwoX = 0, bgThreeX = 0, bgFourX = 0, bgFiveX = 0;

    private final ObjectHandler handler;
    private Camera cam;

    private GameStatus gameStatus = GameStatus.START_SCREEN;
    private Font gameFont;

    private Texture tex;

    public GameUI(GameEngine engine) {
        this.engine = engine;

        tex = GameEngine.getTexture();

        handler = engine.getHandler();

        screenOffset = GameEngine.getScreenOffset();

//        setLevel();


        try {
            InputStream in = getClass().getResourceAsStream("/assets/font/mario-font.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    public void tick() {

        animateBackground();
    }

    public void render(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;

        renderBackground(g);

    }

    private void setLevel() {

        handler.setHero(new Diluc(32 * 20,32,2, handler, engine));

        handler.addObj(new Slime(32 * 50, 32 * 11, 1, false, handler));

        handler.addObj(new Hollow(handler, 100));

        for (int i = 8; i < 23; i++) {
            if (i != 16 && i!=17 && i != 18) {
                handler.addObj(new Tile(i * 32, 32 * 10, 32, 32, 1));
            }
        }

        handler.addObj(new Tile(17 * 32, 32 * 14, 32, 32, 1));


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

        cam = new Camera(0, screenOffset);
    }

    private void gameStart() {

    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    private void animateBackground() {
        float playerSpeed = handler.getHero().getX();
        bgOneX = 0;
        bgTwoX = -playerSpeed * 0.008f;
        bgThreeX = -playerSpeed * 0.02f;
        bgFourX = -playerSpeed * 0.04f;
        bgFiveX = -playerSpeed * 0.06f;

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
