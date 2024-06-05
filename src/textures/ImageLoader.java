package textures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public BufferedImage loadImage(String path, char type) {
        BufferedImage image = null;

        if (type == 's') {
            try {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/sprite" + path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (type == 'a') {
            try {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets" + path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return image;
    }

}
