package tile;

import main.GamePanel;
import utilities.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static utilities.Helper.GetMapData;

public class Layer1 extends Tile {
    GamePanel gamePanel;
    public int widthMap;
    public int heightMap;
    private static BufferedImage map = null;
    private Map<Point, BufferedImage> modifiedTilesLayer1 = new HashMap<>(); // Lưu các tile đã được thay đổi
    private Map<Point, Long> plantingTime = new HashMap<>(); // Thời gian trồng cây
    private Map<Point, Integer> plantGrowing = new HashMap<>();
    private Map<Point, Boolean> planted = new HashMap<>();
    private Map<Point, Boolean> growed = new HashMap<>();


    private static final long PLANTING_DURATION = 2000; // Thời gian trong mili giây

    public Layer1(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getTilesImage();
        map = LoadAndSave.LoadImage("/map/map.png");
        assert map != null;
        widthMap = map.getWidth();
        heightMap = map.getHeight();
        getModifyTilesImage();
    }

    private void getTilesImage() {
        tileImage = LoadAndSave.LoadImage("/tiles/Tilled_Dirt.png");
        assert tileImage != null;
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

    }

    private void getModifyTilesImage() {
        modifyTile = LoadAndSave.LoadImage("/plants/Basic_Plants.png");
        assert modifyTile != null;
        int widthImage = modifyTile.getWidth();
        int heightImage = modifyTile.getHeight();
        int cols = 6;
        int rows = 2;
        int width = widthImage / cols;
        int height = heightImage / rows;
        modifyTileSprite = new BufferedImage[rows * cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int index = y * cols + x;
                modifyTileSprite[index] = modifyTile.getSubimage(x * width, y * height, width, height);
            }
        }
    }

    public static int[][] getLayerData() {
        return GetMapData(map);
    }

    public void planting(int mouseX, int mouseY) {
        // Chuyển tọa độ chuột sang tọa độ thế giới (tính toán theo tile)
        int tileX = mouseX / gamePanel.TILESIZE;
        int tileY = mouseY / gamePanel.TILESIZE;

        if (tileX >= 0 && tileX < widthMap && tileY >= 0 && tileY < heightMap) {

            modifiedTilesLayer1.put(new Point(tileX, tileY), modifyTileSprite[1]);
            plantingTime.put(new Point(tileX, tileY), System.currentTimeMillis());
            plantGrowing.put(new Point(tileX, tileY), 1);
            planted.put(new Point(tileX, tileY), true);
            growed.put(new Point(tileX, tileY), false);

        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        for (Point point : planted.keySet()) {
            long timeElapsed = currentTime - plantingTime.get(point);
            if (timeElapsed >= PLANTING_DURATION) {
                if (planted.get(point)) {
                    int index = plantGrowing.get(point);
                    index += 1;
                    if (index >= 5) {
                        plantingTime.remove(point);
                        plantGrowing.remove(point);
                        planted.put(point,false);
                        growed.put(point,true);
                    } else {
                        if (index < modifyTileSprite.length) {
                            modifiedTilesLayer1.put(point, modifyTileSprite[index]);
                            plantGrowing.put(point, index);
                        }
                    }
                    plantingTime.put(point, currentTime);
                }

            }
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

                    if (modifiedTilesLayer1.containsKey(new Point(x, y))) {
                        // Vẽ tile đã thay đổi
                        graphics2D.drawImage(modifiedTilesLayer1.get(new Point(x, y)), screenX, screenY, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    }
                }
            }
        }
    }
}
