package textures;

import content.hero.Diluc;

import java.awt.image.BufferedImage;

public class Texture {

    private ImageLoader loader;
    private BufferedImage hero_sheet;

    private BufferedImage[] diluc;

    private BufferedImage[] idle, idleSword, run, slash, jump, doubleJump;
    private BufferedImage backgroundOne, backgroundTwo, backgroundThree, backgroundFour, backgroundFive;
    private BufferedImage tile;

    public Texture() {

        loader = new ImageLoader();

        try {
            hero_sheet = loader.loadImage("/hero-Sheet.png", 's');
            tile = loader.loadImage("/jungle tileset.png",'a');
            backgroundOne = loader.loadImage("/plx-1.png",'a');
            backgroundTwo = loader.loadImage("/plx-2.png",'a');
            backgroundThree = loader.loadImage("/plx-3.png",'a');
            backgroundFour = loader.loadImage("/plx-4.png",'a');
            backgroundFive = loader.loadImage("/plx-5.png",'a');
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFrames();
        getHeroTexture();
    }

    private void getHeroTexture() {
        idle = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            idle[i] = diluc[i];
        }

        run = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            run[i] = diluc[8 + i];
        }

        idleSword = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            idleSword[i] = diluc[38 + i];
        }

        slash = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            slash[i] = diluc[43 + i];
        }

        jump = new BufferedImage[2];
        for (int i = 0; i < 2; i++) {
            jump[i] = diluc[15 + i];
        }

        doubleJump = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            doubleJump[i] = diluc[17 + i];
        }
    }


    public void getFrames() {
//        System.out.println(hero_sheet.getHeight());
//        System.out.println(hero_sheet.getHeight());
        int heroHeight = Diluc.getHeroHeight();
        int heroWidth = Diluc.getHeroWidth();
        int rows = hero_sheet.getHeight() / heroHeight;
        int cols = hero_sheet.getWidth() / heroWidth;

        diluc = new BufferedImage[rows * cols];

        int index = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                diluc[index++] = hero_sheet.getSubimage(x * heroWidth, y * heroHeight, heroWidth, heroHeight);
            }
        }

    }

    public BufferedImage[] getIdle() {
        return idle;
    }

    public BufferedImage[] getIdleSword() {
        return idleSword;
    }

    public BufferedImage[] getRun() {
        return run;
    }

    public BufferedImage[] getSlash() {
        return slash;
    }

    public BufferedImage[] getJump() {
        return jump;
    }

    public BufferedImage[] getDoubleJump() {
        return doubleJump;
    }

    public BufferedImage[] getDilucTex() {
        return diluc;
    }


//    public BufferedImage getIdleRight() {
//        return idleRight;
//    }
//
//    public BufferedImage getIdleLeft() {
//        return idleLeft;
//    }
//
//    public BufferedImage getRunRight() {
//        return runRight;
//    }
//
//    public BufferedImage getRunLeft() {
//        return runLeft;
//    }

    public BufferedImage getBackgroundOne() {
        return backgroundOne;
    }

    public BufferedImage getBackgroundTwo() {
        return backgroundTwo;
    }

    public BufferedImage getBackgroundThree() {
        return backgroundThree;
    }

    public BufferedImage getBackgroundFour() {
        return backgroundFour;
    }

    public BufferedImage getBackgroundFive() {
        return backgroundFive;
    }
}
