package main;

import content.ObjectHandler;
import content.hero.HeroAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKey implements KeyListener {
    private boolean[] keyDown = new boolean[4];
    private ObjectHandler handler;

    public GameKey(ObjectHandler handler) {
        this.handler = handler;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_UP:
                if (handler.getHero().isJumped() < 2) {
                    handler.getHero().setJumped();
                    handler.getHero().setVelY(-10);
                }
                break;
            case KeyEvent.VK_LEFT:
                handler.getHero().setRight(false);
                if (handler.getHero().isJumped() == 0) {
                    handler.getHero().setAction(HeroAction.RUN);
                }
                handler.getHero().setVelX(-8);
                keyDown[1] = true;
                break;
            case KeyEvent.VK_RIGHT:
                handler.getHero().setRight(true);
                if (handler.getHero().isJumped() == 0) {
                    handler.getHero().setAction(HeroAction.RUN);
                }
                handler.getHero().setVelX(8);
                keyDown[2] = true;
                break;
            case KeyEvent.VK_SPACE:
                handler.getHero().setAction(HeroAction.ATTACK);
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
                handler.getHero().setAction(HeroAction.IDLE);
                break;
            case KeyEvent.VK_RIGHT:
                keyDown[2] = false;
                handler.getHero().setAction(HeroAction.IDLE);
                break;
//            case KeyEvent.VK_SPACE:
//                handler.getHero().setAction(HeroAction.IDLE);
//                break;
        }
        if (!keyDown[1] && !keyDown[2]) {
            handler.getHero().setVelX(0);

        }
    }

//    private void notifyInput(ButtonAction action) {
//        if(action != ButtonAction.NO_ACTION)
//            gameEngine.receiveInput(action);
//    }
}
