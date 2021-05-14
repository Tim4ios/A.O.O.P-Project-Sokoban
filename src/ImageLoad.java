import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoad {
    public static BufferedImage image(String path){
        try {
            return ImageIO.read(ImageLoad.class.getResource(path));
        }catch (IOException e){
            e.printStackTrace();
            //We don't want to run if fail
            System.exit(1);
        }
        return null;
    }
}
