package map;

import java.awt.image.BufferedImage;

import static utilities.Helper.GetMapData;

public class Map {
    private int[][] mapData;
    private BufferedImage img;

    public Map(BufferedImage img) {
        this.img = img;
        createMapData();
    }

    private void createMapData() {
        mapData = GetMapData(img);
    }

    public int[][] getMapData() {
        return mapData;
    }
}
