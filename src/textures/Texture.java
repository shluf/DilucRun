package textures;

import content.enemy.Slime;
import content.hero.Diluc;

import java.awt.image.BufferedImage;

public class Texture {

    private final ImageLoader loader;
    private BufferedImage hero_sheet;
    private BufferedImage slime_sheet;

    private BufferedImage[] diluc;
    private BufferedImage[] dilucIdle, dilucIdleSword, dilucRun, slash, jump, doubleJump;

    private BufferedImage[] slime;
    private BufferedImage[] slimeIdle, slimeMove, slimeDeath;

    private BufferedImage backgroundOne, backgroundTwo, backgroundThree, backgroundFour, backgroundFive;
    private BufferedImage tile;

    public Texture() {

        loader = new ImageLoader();

        try {
            hero_sheet = loader.loadImage("/hero-Sheet.png", 's');
            slime_sheet = loader.loadImage("/slime-Sheet.png", 's');
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
        getSlimeTexture();
    }

    private void getHeroTexture() {
        dilucIdle = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            dilucIdle[i] = diluc[i];
        }

        dilucRun = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            dilucRun[i] = diluc[8 + i];
        }

        dilucIdleSword = new BufferedImage[4];
        System.arraycopy(diluc, 38, dilucIdleSword, 0, 4);

        slash = new BufferedImage[3]; //16
        for (int i = 0; i < 3; i++) {
//            slash[i] = diluc[43 + i];
            slash[i] = diluc[48 + i];
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


    private void getSlimeTexture() {
        slimeIdle = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            slimeIdle[i] = slime[i];
        }

        slimeMove = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            slimeMove[i] = slime[4 + i];
        }

        slimeDeath = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            slimeDeath[i] = slime[17 + i];
        }
    }


    public void getFrames() {
//        System.out.println(slime_sheet.getHeight());
//        System.out.println(slime_sheet.getWidth());
//        System.out.println(hero_sheet.getHeight());
//        System.out.println(hero_sheet.getWidth());
        int heroHeight = Diluc.getHeroHeight();
        int heroWidth = Diluc.getHeroWidth();
        int heroRows = hero_sheet.getHeight() / heroHeight;
        int heroCols = hero_sheet.getWidth() / heroWidth;

        diluc = new BufferedImage[heroRows * heroCols];

        int heroIndex = 0;
        for (int y = 0; y < heroRows; y++) {
            for (int x = 0; x < heroCols; x++) {
                diluc[heroIndex++] = hero_sheet.getSubimage(x * heroWidth, y * heroHeight, heroWidth, heroHeight);
            }
        }

        int slimeWidth = Slime.getSlimeWidth();
        int slimeHeight = Slime.getSlimeHeight();
        int slimeRows = slime_sheet.getHeight() / slimeHeight;
        int slimeCols = slime_sheet.getWidth() / slimeWidth;

        slime = new BufferedImage[slimeRows * slimeCols];

        int slimeIndex = 0;
        for (int y = 0; y < slimeRows; y++) {
            for (int x = 0; x < slimeCols; x++) {
                slime[slimeIndex++] = slime_sheet.getSubimage(x * slimeWidth, y * slimeHeight, slimeWidth, slimeHeight);
            }
        }

    }

    public BufferedImage[] getIdle() {
        return dilucIdle;
    }

    public BufferedImage[] getIdleSword() {
        return dilucIdleSword;
    }

    public BufferedImage[] getRun() {
        return dilucRun;
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

    public BufferedImage[] getSlimeIdle() {
        return slimeIdle;
    }

    public BufferedImage[] getSlimeMove() {
        return slimeMove;
    }

    public BufferedImage[] getSlimeDeath() {
        return slimeDeath;
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

    public BufferedImage[] getSlime() {
        return slime;
    }
}
