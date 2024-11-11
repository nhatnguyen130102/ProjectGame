package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager extends Tile {
    GamePanel gamePanel;
    public int widthMap;
    public int heightMap;
    private BufferedImage map;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getTilesImage();
        loadMap("/map/map.png");
    }

    private void getTilesImage() {
        try {
            tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Hills.png")));
            int widthImage = tileImage.getWidth();
            int heightImage = tileImage.getHeight();
            int cols = 11;
            int rows = 7;
            int width = widthImage / cols;
            int height = heightImage / rows;
            tileSprite = new BufferedImage[rows * cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    int index = y * cols + x;
                    tileSprite[index] = tileImage.getSubimage(x * width, y * height, width, height);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMap(String path) {
        try {
            map = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            widthMap = map.getWidth();
            heightMap = map.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {

        int cameraX = gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX;
        int cameraY = gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY;

        for (int y = 0; y < heightMap; y++) {
            for (int x = 0; x < widthMap; x++) {
                Color color = new Color(map.getRGB(x, y));
                int value = color.getGreen();

                if (value > 0 && value <= tileSprite.length) {
                    int worldX = x * gamePanel.TILESIZE;
                    int worldY = y * gamePanel.TILESIZE;
                    int screenX = worldX - cameraX;
                    int screenY = worldY - cameraY;

                    if (screenX + gamePanel.TILESIZE > 0 && screenX < gamePanel.SCREENWIDTH &&
                            screenY + gamePanel.TILESIZE > 0 && screenY < gamePanel.SCREENHEIGHT) {
                        graphics2D.drawImage(tileSprite[value - 1], screenX, screenY, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    }
                }
            }
        }
    }
}
