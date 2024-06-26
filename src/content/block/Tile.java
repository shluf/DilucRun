package content.block;

import content.GameObject;
import content.ObjectID;
import main.GameEngine;
import textures.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Square {
    private final Texture tex;
    private final BufferedImage[] tile;
    private final BlockType type;


    public Tile(float x, float y, float width, float height, int scale, BlockType type) {
        super(x, y, ObjectID.TILE, width, height, scale);
        this.type = type;

        tex = GameEngine.getTexture();
        tile = tex.getTileBlock();
    }

    @Override
    public void render(Graphics g) {
//        super.showBounds(g);

        switch (type) {
            case GROUND_TOP:
                g.drawImage(tile[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_TOP_LEFT:
                g.drawImage(tile[2], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_TOP_RIGHT:
                g.drawImage(tile[2], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_MIDDLE_LEFT:
                g.drawImage(tile[3], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_MIDDLE_RIGHT:
                g.drawImage(tile[3], (int) (getX() + getWidth()), (int) getY(), -(int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_BOTTOM:
                g.drawImage(tile[1], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
            case GROUND_BOTTOM_LEFT:
                g.drawImage(tile[4], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
                break;
        }
    }
}
