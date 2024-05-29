package textures;

import content.hero.Diluc;

import java.awt.image.BufferedImage;

public class Texture {

    private ImageLoader loader;
    private BufferedImage hero_sheet;

    private BufferedImage[] diluc;

    private BufferedImage idleRight, idleLeft, runRight, runLeft;
    private BufferedImage backgroundOne, backgroundTwo, backgroundThree, backgroundFour, backgroundFive;


    public Texture() {

        loader = new ImageLoader();

        try {
            hero_sheet = loader.loadImage("/hero-Sheet.png", 's');
            idleRight = loader.loadImage("/hero-Idle.gif",'s');
            idleLeft = loader.loadImage("/hero-Idle-left.gif",'s');
            runRight = loader.loadImage("/hero-Run.gif",'s');
            runLeft = loader.loadImage("/hero-Run-left.gif",'s');
            backgroundOne = loader.loadImage("/plx-1.png",'a');
            backgroundTwo = loader.loadImage("/plx-2.png",'a');
            backgroundThree = loader.loadImage("/plx-3.png",'a');
            backgroundFour = loader.loadImage("/plx-4.png",'a');
            backgroundFive = loader.loadImage("/plx-5.png",'a');
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFrames();
//        getHeroTexture();
    }

//    private void getHeroTexture() {
//        int x_off = 1;
//        int y_off = 1;
//        int width = 37, height = 37;
//
//        for (int i = 0; i < 4; i++) {
//            diluc[i] = hero_sheet.getSubimage(x_off, i*(width), y_off, height);
//        }
//    }


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

    public BufferedImage[] getDilucTex() {
        return diluc;
    }



    public BufferedImage getIdleRight() {
        return idleRight;
    }

    public BufferedImage getIdleLeft() {
        return idleLeft;
    }

    public BufferedImage getRunRight() {
        return runRight;
    }

    public BufferedImage getRunLeft() {
        return runLeft;
    }

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
