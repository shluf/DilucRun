package textures;

import content.enemy.Slime;
import content.hero.Diluc;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {

    private BufferedImage hero_sheet;
    private BufferedImage heroSwordRun_sheet;
    private BufferedImage heroBow_sheet;
    private BufferedImage slime_sheet;
    private BufferedImage chest_sheet;
    private BufferedImage tile_sheet;
    private BufferedImage gate_sheet;
    private BufferedImage gateBack_sheet;
    private BufferedImage coin_sheet;
    private BufferedImage coinPick_sheet;

    private BufferedImage[] diluc;
    private BufferedImage[] dilucIdle, dilucIdleSword, dilucRun, dilucRunSword, dilucBow, dilucInteract, slash, jump, doubleJump;

    private BufferedImage[] slime;
    private BufferedImage[] slimeIdle, slimeMove, slimeDeath, slimeAttack;

    private BufferedImage[] chest;

    private BufferedImage backgroundOne, backgroundTwo, backgroundThree, backgroundFour, backgroundFive;

    private BufferedImage[] tile;

    private BufferedImage[] gate, gateBackground;

    private BufferedImage[] coin, coinPick;

    private BufferedImage[] arrows;

    private Image mainMenu, credits, tutorial;

    public Texture() {

        ImageLoader loader = new ImageLoader();

        try {
            hero_sheet = loader.loadImage("/hero-Sheet.png", 's');
            heroSwordRun_sheet = loader.loadImage("/heroSwordRun-Sheet.png", 's');
            heroBow_sheet = loader.loadImage("/heroBow-Sheet.png", 's');
            slime_sheet = loader.loadImage("/slime-Sheet.png", 's');
            chest_sheet = loader.loadImage("/chest-Sheet.png",'s');
            gate_sheet = loader.loadImage("/gate-Sheet.png",'s');
            gateBack_sheet = loader.loadImage("/gateBack-Sheet.png",'s');
            coin_sheet = loader.loadImage("/coin-Sheet.png",'s');
            coinPick_sheet = loader.loadImage("/coinPick-Sheet.png",'s');
            tile_sheet = loader.loadImage("/jungle-tileset.png");
            backgroundOne = loader.loadImage("/plx-1.png");
            backgroundTwo = loader.loadImage("/plx-2.png");
            backgroundThree = loader.loadImage("/plx-3.png");
            backgroundFour = loader.loadImage("/plx-4.png");
            backgroundFive = loader.loadImage("/plx-5.png");
            mainMenu = loader.loadImage("/main_menu.png", 'm');
            tutorial = loader.loadImage("/tutorial.png", 'm');
            credits = loader.loadImage("/credits.png", 'm');
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFrames();
        getHeroTexture();
        getSlimeTexture();
        getArrowTexture(loader);
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
        for (int i = 0; i < 4; i++) {
            dilucIdleSword[i] = diluc[38 + i];
        }

        dilucRunSword = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            dilucRunSword[i] = diluc[112 + i];
        }

        dilucInteract = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            dilucInteract[i] = diluc[88 + i];
        }

        slash = new BufferedImage[13]; //16
        for (int i = 0; i < 13; i++) {
            slash[i] = diluc[46 + i];
//            slash[i] = diluc[51 + i];
//            slash[i] = diluc[49 + i]; // 2
        }

        dilucBow = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            dilucBow[i] = diluc[119 + i];
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

        slimeAttack = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            slimeAttack[i] = slime[7 + i];
        }
    }

    private void getArrowTexture(ImageLoader loader) {
        arrows = new BufferedImage[4];

        for (int i = 0; i < 4; i++) {
            arrows[i] = loader.loadImage("/arrow/1_" + (i) + ".png",'s');
        }
    }

    public void getFrames() {
        int heroHeight = Diluc.getHeroHeight();
        int heroWidth = Diluc.getHeroWidth();
        int heroRows = hero_sheet.getHeight() / heroHeight;
        int heroCols = hero_sheet.getWidth() / heroWidth;

        int heroSwordRunRows = heroSwordRun_sheet.getHeight() / heroHeight;
        int heroSwordRunCols = heroSwordRun_sheet.getWidth() / heroWidth;

        int heroBowRows = heroBow_sheet.getHeight() / heroHeight;
        int heroBowCols = heroBow_sheet.getWidth() / heroWidth;

        diluc = new BufferedImage[heroRows * heroCols + heroSwordRunRows * heroSwordRunCols + heroBowRows * heroBowCols];   // 77 + 6 + 16

        int heroIndex = 0;
        for (int y = 0; y < heroRows; y++) {
            for (int x = 0; x < heroCols; x++) {
                diluc[heroIndex++] = hero_sheet.getSubimage(x * heroWidth, y * heroHeight, heroWidth, heroHeight);
            }
        }
        for (int y = 0; y < heroSwordRunRows; y++) {
            for (int x = 0; x < heroSwordRunCols; x++) {
                diluc[heroIndex++] = heroSwordRun_sheet.getSubimage(x * heroWidth, y * heroHeight, heroWidth, heroHeight);
            }
        }
        for (int y = 0; y < heroBowRows; y++) {
            for (int x = 0; x < heroBowCols; x++) {
                diluc[heroIndex++] = heroBow_sheet.getSubimage(x * heroWidth, y * heroHeight, heroWidth, heroHeight);
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

        int chestWidth = 16;
        int chestHeight = 16;
        int chestRows = chest_sheet.getHeight() / chestHeight;
        int chestCols = chest_sheet.getWidth() / chestWidth;

        chest = new BufferedImage[chestRows * chestCols];

        int chestIndex = 0;
        for (int y = 0; y < chestRows; y++) {
            for (int x = 0; x < chestCols; x++) {
                chest[chestIndex++] = chest_sheet.getSubimage(x * chestWidth, y * chestHeight, chestWidth, chestHeight);
            }
        }

        int coinWidth = 16;
        int coinHeight = 16;
        int coinRows = coin_sheet.getHeight() / coinHeight;
        int coinCols = 9;

        coin = new BufferedImage[coinRows * coinCols];

        int coinIndex = 0;
        for (int y = 0; y < coinRows; y++) {
            for (int x = 0; x < coinCols; x++) {
                coin[coinIndex++] = coin_sheet.getSubimage(x * coinWidth, y * coinHeight, coinWidth, coinHeight);
            }
        }

        int coinPickWidth = 32;
        int coinPickHeight = 32;
        int coinPickRows = coinPick_sheet.getHeight() / coinPickHeight;
        int coinPickCols = coinPick_sheet.getWidth() / coinPickWidth;

        coinPick = new BufferedImage[coinPickRows * coinPickCols];

        int coinPickIndex = 0;
        for (int y = 0; y < coinPickRows; y++) {
            for (int x = 0; x < coinPickCols; x++) {
                coinPick[coinPickIndex++] = coinPick_sheet.getSubimage(x * coinPickWidth, y * coinPickHeight, coinPickWidth, coinPickHeight);
            }
        }

        tile = new BufferedImage[10];
        tile[9] = tile_sheet.getSubimage(16, 224, 31,31);
        tile[0] = tile_sheet.getSubimage(96, 34, 29,29);
        tile[1] = tile_sheet.getSubimage(305, 210, 29,29);
        tile[2] = tile_sheet.getSubimage(65, 34, 29,29);
        tile[3] = tile_sheet.getSubimage(65, 65, 29,29);
        tile[4] = tile_sheet.getSubimage(480, 208, 14,14);

        gate = new BufferedImage[4];
        gate[0] = gate_sheet.getSubimage(0,0, 153, 153);
        gate[1] = gate_sheet.getSubimage(0,240, 153, 153);
        gate[2] = gate_sheet.getSubimage(0,480, 153, 153);
        gate[3] = gate_sheet.getSubimage(0,720, 153, 153);

        gateBackground = new  BufferedImage[3];
        gateBackground[0] = gateBack_sheet.getSubimage(0, 0, 122, 134);
        gateBackground[1] = gateBack_sheet.getSubimage(240, 0, 122, 134);
        gateBackground[2] = gateBack_sheet.getSubimage(480, 0, 122, 134);
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

    public BufferedImage[] getRunSword() {
        return dilucRunSword;
    }

    public BufferedImage[] getSlash() {
        return slash;
    }

    public BufferedImage[] getBow() {
        return dilucBow;
    }

    public BufferedImage[] getJump() {
        return jump;
    }

    public BufferedImage[] getDilucInteract() {
        return dilucInteract;
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

    public BufferedImage[] getSlimeAttack() {
        return slimeAttack;
    }

    public BufferedImage[] getTileBlock() {
        return tile;
    }

    public BufferedImage[] getChest() {
        return chest;
    }

    public BufferedImage[] getGate() {
        return gate;
    }

    public BufferedImage[] getGateBackground() {
        return gateBackground;
    }

    public BufferedImage[] getCoinPick() {
        return coinPick;
    }

    public BufferedImage[] getCoin() {
        return coin;
    }

    public BufferedImage[] getArrows() {
        return arrows;
    }

    public Image getMainMenu() {
        return mainMenu;
    }

    public Image getCredits() {
        return credits;
    }

    public Image getTutorial() {
        return tutorial;
    }
}
