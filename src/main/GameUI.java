package main;

import main.condition.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameUI extends JPanel {
    private GameEngine engine;
    private GameStatus gameStatus = GameStatus.START_SCREEN;
    private int WIDTH, HEIGHT;
    private Font gameFont;

    public GameUI(GameEngine engine) {
        this.gameStatus = gameStatus;
        this.engine = engine;

        setPreferredSize(new Dimension(engine.getWidth(), engine.getHeight()));
        setMaximumSize(new Dimension(engine.getWidth(), engine.getHeight()));
        setMinimumSize(new Dimension(engine.getWidth(), engine.getHeight()));

        try {
            InputStream in = getClass().getResourceAsStream("/assets/font/mario-font.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    private void tick() {

    }

    private void render() {

    }

    private void gameStart() {

    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
