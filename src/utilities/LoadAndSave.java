package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LoadAndSave {
    public static BufferedImage LoadImage(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(LoadAndSave.class.getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
