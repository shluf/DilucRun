package content;

import java.awt.*;

public abstract class GameObject {

    private float x, y;
    private float velX, velY;
    private final ObjectID id;
    private float width, height;

    public GameObject(float x, float y, ObjectID id, float width, float height, int scale) {
        this.x = x * scale;
        this.y = y * scale;
        this.id = id;
        this.width = width * scale;
        this.height = height * scale;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public void applyGravity() {
        velY += 0.5f;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public ObjectID getId() {
        return id;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
