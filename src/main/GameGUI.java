package main;

import content.hero.Diluc;
import main.condition.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GameGUI extends JPanel {
    private GameEngine engine;

    private Diluc diluc;

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        GameStatus gameStatus = engine.getGameStatus();

        diluc.render(g2);
//        if(gameStatus == GameStatus.START_SCREEN){
//            drawStartScreen(g2);
//        }
//        else if(gameStatus == GameStatus.MAP_SELECTION){
//            drawMapSelectionScreen(g2);
//        }
//        else if(gameStatus == GameStatus.ABOUT_SCREEN){
//            drawAboutScreen(g2);
//        }
//        else if(gameStatus == GameStatus.HELP_SCREEN){
//            drawHelpScreen(g2);
//        }
//        else if(gameStatus == GameStatus.GAME_OVER){
//            drawGameOverScreen(g2);
//        }
//        else {
//            Point camLocation = engine.getCameraLocation();
//            g2.translate(-camLocation.x, -camLocation.y);
//            engine.drawMap(g2);
//            g2.translate(camLocation.x, camLocation.y);
//
//            drawPoints(g2);
//            drawRemainingLives(g2);
//            drawAcquiredCoins(g2);
//            drawRemainingTime(g2);
//
//            if(gameStatus == GameStatus.PAUSED){
//                drawPauseScreen(g2);
//            }
//            else if(gameStatus == GameStatus.MISSION_PASSED){
//                drawVictoryScreen(g2);
//            }
//        }

        g2.dispose();
    }
}
