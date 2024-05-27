package textures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
//    private BufferedImage diluc;

//    public ImageLoader(){
////        this.diluc = loadImage("sprite/hero-Sheet.png");
////        brickAnimation = loadImage("/brick-animation.png");
//    }

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


//    public BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h){
//        if((col == 1 || col == 4) && row == 3){ //koopa
//            return image.getSubimage((col-1)*48, 128, w, h);
//        }
//        return image.getSubimage((col-1)*48, (row-1)*48, w, h);
//    }
//
//    public BufferedImage[] getLeftFrames(){
//        BufferedImage[] leftFrames = new BufferedImage[5];
//        int width = 50, height = 37;
//
//        for(int i = 0; i < 5; i++){
//            leftFrames[i] = diluc.getSubimage(i*width, height, width, height);
//        }
//        return leftFrames;
//    }


}
