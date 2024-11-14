package utilities;

import entity.Player;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilities.Constants.PlayerConstant.*;


public class Helper {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] mapData, int direction) {
        switch (direction) {
            case UP -> {
                return IsSolid(x, y - 1, mapData);
            }
            case DOWN -> {
                return IsSolid(x + width - 1 , y + height, mapData) && IsSolid(x + 1, y + height, mapData);
            }
            case RIGHT -> {
                return IsSolid(x + width , y, mapData);
            }
            case LEFT -> {
                return IsSolid(x - 1, y, mapData);
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean IsSolid(float x, float y, int[][] mapData) {
        int maxWidth = mapData[0].length * GamePanel.TILESIZE;
        int maxHeight = mapData.length;
        if (x < 0 || x >= maxWidth * GamePanel.TILESIZE)
            return true;
        if (y < 0 || y >= maxHeight * GamePanel.TILESIZE)
            return true;

        float xIndex = x / GamePanel.TILESIZE;
        float yIndex = y / GamePanel.TILESIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, mapData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] mapData) {
        int maxWidth = mapData[0].length;
        int maxHeight = mapData.length;
        if (xTile < 0 || xTile >= maxWidth) {
            return true;
        }
        if (yTile < 0 || yTile >= maxHeight) {
            return true;
        }
        int value = mapData[yTile][xTile];

        return value != 24 && value != 23 && value != 25 && value != 12 && value != 14;
    }

    public static int[][] GetMapData(BufferedImage img) {
        int[][] mapData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++) {//Row
            for (int i = 0; i < img.getWidth(); i++) {//Col
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                mapData[j][i] = value;
            }
        }
        return mapData;
    }
}
