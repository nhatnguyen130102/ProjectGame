package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public BufferedImage playerMovingImage;
    public BufferedImage[][] playerMovingSprite;
    public BufferedImage playerActionImage;
    public BufferedImage[][] playerActionSprite;
    public String direction;
    public String typeAction;
    public boolean moving = false;
    public boolean action = false;
    public int spriteNum = 0;
    public int spriteCounter = 0;
    public int spriteActionNum = 0;
    public int spriteActionCounter = 0;
}
