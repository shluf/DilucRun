package main.view;

import content.GameObject;
import main.GameEngine;

public class Camera {
    private int x, y;
//    komen
//    komen 2

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject hero) {
        x = (int) -hero.getX() + GameEngine.getScreenWidth()/2;
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
