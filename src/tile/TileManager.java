package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager extends Tile {
    GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getTilesImage();
    }

    private void getTilesImage() {
        try {
            tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Grass.png")));
            int widthImage = tileImage.getWidth();
            int heightImage = tileImage.getHeight();
            int cols = 11;
            int rows = 7;
            int width = widthImage / cols;
            int height = heightImage / rows;
            tileSprite = new BufferedImage[rows * cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    int index = y * 11 + x;
                    tileSprite[index] = tileImage.getSubimage(x * width, y * height, width, height);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage map;
        try {
            map = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/map/map.png")));
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    Color color = new Color(map.getRGB(x, y));
                    int value = color.getGreen();
                    if (value >= 0 && value < tileSprite.length) {
                        graphics2D.drawImage(tileSprite[value], x * gamePanel.TILESIZE, y * gamePanel.TILESIZE, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
