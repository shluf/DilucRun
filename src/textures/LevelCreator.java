package textures;

import content.ObjectHandler;
import content.block.*;
import content.enemy.Slime;
import content.enemy.SlimeHolder;
import content.hero.Diluc;
import main.GameEngine;
import main.GameUI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelCreator {
    private final GameEngine engine;
    private final GameUI gameUI;
    private final ImageLoader loader;
    private final ObjectHandler handler;

    private BufferedImage mapTex;

    private int minimumCoin;
    private float slimeSpeed;
    private int slimeAttackSpeed;
    private int gateX;
    private int gateY;

    public LevelCreator(ObjectHandler handler, GameEngine engine, GameUI gameUI) {
        this.handler = handler;
        this.engine = engine;
        this.gameUI = gameUI;
        loader = new ImageLoader();
    }

    public boolean start(int mapLevel) {
        setLevel("/Map-"+ mapLevel +".png");
        return true;
    }

    private void setLevel(String mapPath) {
        this.mapTex = loader.loadImage(mapPath, 'l');

        int width = mapTex.getWidth();
        int height = mapTex.getHeight();

        int mapLevel = engine.getMapLevel();
        switch (mapLevel) {
            case 1:
                minimumCoin= 15;
                slimeAttackSpeed = 2;
                slimeSpeed=2.0F;
                gateX=181;
                gateY=18;
                break;
            case 2:
                minimumCoin = 25;
                slimeAttackSpeed = 4;
                slimeSpeed=2.3F;
                gateX=181;
                gateY=18;
                break;
            case 3:
                minimumCoin = 35;
                slimeAttackSpeed = 8;
                slimeSpeed=2.5F;
                gateX=181;
                gateY=18;
                break;
        }

        handler.addObj(new Hollow(handler, width, engine));
        handler.addObj(new Gate(gateX*32,gateY*32,1, minimumCoin, gameUI));
//        handler.addObj(new Gate(15*32,15*32,1, minimumCoin, gameUI));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = mapTex.getRGB(i,j);
                int diluc = new Color(255, 255, 0).getRGB();
                int slime = new Color(255, 0, 0).getRGB();
                int slimeHolder = new Color(160, 0, 160).getRGB();
                int tile = new Color(0, 0, 0).getRGB();
                int tileTopLeft = new Color(40, 40, 40).getRGB();
                int tileTopRight = new Color(80, 80, 80).getRGB();
                int tileMiddleLeft = new Color(80, 0, 80).getRGB();
                int tileMiddleRight = new Color(0, 80, 0).getRGB();
                int tileGround = new Color(160, 160, 160).getRGB();
                int tileGroundLeft = new Color(160, 160, 0).getRGB();
                int chest = new Color(0, 0, 255).getRGB();
                int coin = new Color(0, 255, 0).getRGB();

                if (pixel == diluc) {
                    handler.setHero(new Diluc(i * 32,j,2, handler, engine, gameUI));
                }

                else if (pixel == tile) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_BOTTOM));
                } else if (pixel == tileTopLeft) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_TOP_LEFT));
                } else if (pixel == tileTopRight) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_TOP_RIGHT));
                } else if (pixel == tileMiddleLeft) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_MIDDLE_LEFT));
                } else if (pixel == tileMiddleRight) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_MIDDLE_RIGHT));
                } else if (pixel == tileGround) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_TOP));
                } else if (pixel == tileGroundLeft) {
                    handler.addObj(new Tile(i * 32, 32 * j, 32, 32, 1, BlockType.GROUND_BOTTOM_LEFT));
                }

                else if (pixel == slime) {
                    handler.addObj(new Slime(i*32,j*32,1, false, handler, slimeAttackSpeed, slimeSpeed));
                } else if (pixel == slimeHolder) {
                    handler.addObj(new SlimeHolder(i*32,j*32, 32, 32, 1));
                } else if (pixel == chest) {
                    handler.addObj(new Chest(i*32,j*32,1, gameUI, handler));
                }
                else if (pixel == coin) {
                    handler.addObj(new Coin(i*32,j*32,1, handler));
                }
            }

        }
    }

    public int getMinimumCoin() {
        return minimumCoin;
    }
}
