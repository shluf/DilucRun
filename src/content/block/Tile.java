package content.block;

import content.GameObject;
import content.ObjectID;
import main.GameEngine;
import textures.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends GameObject {
    private final Texture tex;
    private BufferedImage tileTexture;


    public Tile(float x, float y, float width, float height, int scale) {
        super(x, y, ObjectID.TILE, width, height, scale);

        tex = GameEngine.getTexture();

        tileTexture = tex.getTileLandBlock();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        g.drawImage(tileTexture, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
