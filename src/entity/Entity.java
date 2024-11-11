package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x,y;
    public int speed;
    public BufferedImage playerImage;
    public BufferedImage[][] playerSprite;
    public String direction;
    public boolean moving = false;
    public int spriteNum = 0;
    public int spriteCounter = 0;
}
