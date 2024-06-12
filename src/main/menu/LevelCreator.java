package main.menu;

import content.ObjectHandler;
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
        setLevel("/level/Map 1.png");
    }

    public void setLevel(String mapPath) {
        this.map = loader.loadImage(mapPath, 'a');

        int width = map.getWidth() - 100;
        int height = map.getHeight() - 100;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = map.getRGB(i,j);
                int diluc = new Color(160, 160, 160).getRGB();
                int slime = new Color(0, 0, 255).getRGB();
                int tile = new Color(0, 255, 0).getRGB();
                int groundTile = new Color(255, 255, 0).getRGB();
                int coin = new Color(255, 0, 0).getRGB();
                int chest = new Color(0, 255, 255).getRGB();
                int gate = new Color(160, 0, 160).getRGB();

                if (pixel == diluc) {
                    handler.setHero(new Diluc(i*32,j*32,2, handler, engine));
                }
            }

        }
    }



//    int block = new Color(255, 0, 255).getRGB();

}
