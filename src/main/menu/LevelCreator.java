package main.menu;

import content.ObjectHandler;
import content.block.Hollow;
import content.block.Tile;
import content.enemy.Slime;
import content.hero.Diluc;
import main.GameEngine;
import textures.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelCreator {
    private static final int PIXEL_SIZE = 32;

    private GameEngine engine;
    private ImageLoader loader;
    private BufferedImage map;
    private ObjectHandler handler;


    public LevelCreator(ObjectHandler handler, GameEngine engine) {
        this.handler = handler;
        loader = new ImageLoader();
        this.engine = engine;
    }

    public void start() {
        setLevel("/level/Map-0.png");
    }

    public void setLevel(String mapPath) {
        this.map = loader.loadImage(mapPath, 'a');

        int width = map.getWidth();
        int height = map.getHeight();

        handler.addObj(new Hollow(handler, width));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = map.getRGB(i,j);
                int diluc = new Color(255, 255, 0).getRGB();
                int slime = new Color(255, 0, 0).getRGB();
                int tile = new Color(0, 0, 0).getRGB();
                int tileGround = new Color(160, 160, 160).getRGB();
                int chest = new Color(0, 0, 255).getRGB();
                int gate = new Color(0, 255, 0).getRGB();

                if (pixel == diluc) {
                    handler.setHero(new Diluc(i * 32,j,2, handler, engine));
                } else if (pixel == tile) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1));
                } else if (pixel == slime) {
                    handler.addObj(new Slime(i*32,j*32,1, false, handler));
                }
            }

        }
    }

}
