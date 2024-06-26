package main;

import content.ObjectAction;
import content.ObjectHandler;
import main.condition.GameStatus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKey implements KeyListener {
    private final boolean[] keyDown = new boolean[4];
    private final ObjectHandler handler;
    private final GameUI gameUI;
    private final GameEngine engine;

    public GameKey(ObjectHandler handler, GameUI gameUI, GameEngine engine) {
        this.handler = handler;
        this.gameUI = gameUI;
        this.engine = engine;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                if (gameUI.getGameStatus() == GameStatus.RUNNING) {
                    gameUI.setGameStatus(GameStatus.START_SCREEN);
                    if (gameUI.getInGameStatus() != GameStatus.PAUSED) {
                        gameUI.setInGameStatus(GameStatus.PAUSED);
                        engine.getMusic().stop();
                    }
                }
                if (gameUI.getGameStatus() == GameStatus.TUTORIAL || gameUI.getGameStatus() == GameStatus.CREDIT || gameUI.getGameStatus() == GameStatus.HIGHSCORES) {
                    gameUI.setGameStatus(GameStatus.START_SCREEN);
                }
                break;
            case KeyEvent.VK_UP:
                switch (gameUI.getGameStatus()) {
                    case START_SCREEN:
                        gameUI.keyUp();
                        break;
                    case RUNNING:
                        if (handler.getHero().isJumped() < 2) {
                            handler.getHero().setJumped();
                            handler.getHero().setVelY(-10);
                        }
                        break;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (gameUI.getGameStatus() == GameStatus.START_SCREEN) {
                    gameUI.keyDown();
                }
                break;
            case KeyEvent.VK_LEFT:
                handler.getHero().setRight(false);
                if (handler.getHero().isJumped() == 0) {
                    handler.getHero().setAction(ObjectAction.RUN);
                }
                handler.getHero().setVelX(-5);
                keyDown[1] = true;
                break;
            case KeyEvent.VK_RIGHT:
                handler.getHero().setRight(true);
                if (handler.getHero().isJumped() == 0) {
                    handler.getHero().setAction(ObjectAction.RUN);
                }
                handler.getHero().setVelX(5);
                keyDown[2] = true;
                break;
            case KeyEvent.VK_SPACE:
                if (handler.getHero().getLevel() >= 2) {
                    handler.getHero().setVelX(0);
                    handler.getHero().setAction(ObjectAction.ATTACK);
//                    handler.getHero().setSlash(true);
                }
                break;
            case KeyEvent.VK_Z:
                handler.getHero().setAction(ObjectAction.INTERACT);
                break;
            case KeyEvent.VK_X:
                if (handler.getHero().getLevel() >= 3 && handler.getArrow() > 0) {
                    handler.getHero().setVelX(0);
                    handler.getHero().setAction(ObjectAction.BOW);
                }
                break;
            case KeyEvent.VK_ENTER:
                if (gameUI.getGameStatus() == GameStatus.START_SCREEN) {
                    switch (gameUI.getSelect()) {
                        case 0:
                            if (handler.getGameObjs().isEmpty()) {
                                engine.loadMap(engine.getMapLevel());
                            } else {
                                gameUI.setGameStatus(GameStatus.RUNNING);
                            }
                            break;
                        case 1:
                            gameUI.setGameStatus(GameStatus.HIGHSCORES);
                            break;
                        case 2:
                            gameUI.setGameStatus(GameStatus.TUTORIAL);
                            break;
                        case 3:
                            gameUI.setGameStatus(GameStatus.CREDIT);
                            break;
                        case 4:
                            System.exit(0);
                            break;
                    }
                }
                if (gameUI.getGameStatus() == GameStatus.LEVEL_COMPLETED) {
                    engine.nextMapLevel();
                }
                break;
            case KeyEvent.VK_R:
                if (gameUI.getInGameStatus() == GameStatus.GAME_OVER) {
                    engine.restartLevel();
                }
                break;
            case KeyEvent.VK_P:
                if (gameUI.getInGameStatus() != GameStatus.PAUSED) {
                    gameUI.setInGameStatus(GameStatus.PAUSED);
                    engine.getMusic().stop();
                } else {
                    gameUI.setInGameStatus(GameStatus.RUNNING);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                keyDown[0] = false;
                break;
            case KeyEvent.VK_LEFT:
                keyDown[1] = false;
                handler.getHero().setAction(ObjectAction.IDLE);
                break;
            case KeyEvent.VK_RIGHT:
                keyDown[2] = false;
                handler.getHero().setAction(ObjectAction.IDLE);
                break;
            case KeyEvent.VK_Z:
                handler.getHero().getAnimInteract().reset();
                handler.getHero().setAction(ObjectAction.IDLE);
                break;
        }
        if (!keyDown[1] && !keyDown[2]) {
            if (handler.getHero() != null) {
                handler.getHero().setVelX(0);
            }

        }
    }
}
