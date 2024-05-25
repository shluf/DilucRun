package textures;

import java.awt.image.BufferedImage;

public class Texture {

    private ImageLoader loader;
    private BufferedImage hero_sheet;

    public BufferedImage[] diluc;

    public BufferedImage idleRight, idleLeft, runRight, runLeft;

    public Texture() {
        diluc = new BufferedImage[4];

        loader = new ImageLoader();

        try {
            hero_sheet = loader.loadImage("/hero-Sheet.png");
            idleRight = loader.loadImage("/hero-Idle.gif");
            idleLeft = loader.loadImage("/hero-Idle-left.gif");
            runRight = loader.loadImage("/hero-Run.gif");
            runLeft = loader.loadImage("/hero-Run-left.gif");
        } catch (Exception e) {
            e.printStackTrace();
        }

        getHeroTexture();
    }

    private void getHeroTexture() {
        int x_off = 1;
        int y_off = 1;
        int width = 37, height = 37;

        for (int i = 0; i < 4; i++) {
            diluc[i] = hero_sheet.getSubimage(x_off, i*(width), y_off, height);
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
}
