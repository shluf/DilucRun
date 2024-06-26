package textures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public BufferedImage loadImage(String path, char type) {
        BufferedImage image = null;

        switch (type) {
            case 's':
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/sprite" + path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 'm':
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/menu" + path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 'l':
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/level" + path)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return image;
    }

    public BufferedImage loadImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets" + path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
