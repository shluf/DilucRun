package main;

import main.condition.GameStatus;

import javax.swing.*;

public class GameUI extends JPanel {
    private GameEngine engine;
    private GameStatus gameStatus;

    public GameUI(GameEngine engine) {
        this.engine = engine;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
