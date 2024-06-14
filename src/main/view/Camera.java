package main.view;

import content.GameObject;
import main.GameEngine;

public class Camera {
    private int x, y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject hero) {
        this.x = (int)hero.getX() - GameEngine.getWindowWidth() / 2;
        this.y = (int)hero.getY() - GameEngine.getWindowHeight() / 2;

        if (this.x < 0) this.x = 0;
        if (this.y < 0) this.y = 0;

        if (this.x > (187 * 32) - GameEngine.getWindowWidth()) this.x = 187 * 32 - GameEngine.getWindowWidth();
        if (this.y > (30 * 32) - GameEngine.getWindowHeight()) this.y = 30 * 32 - GameEngine.getWindowHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
