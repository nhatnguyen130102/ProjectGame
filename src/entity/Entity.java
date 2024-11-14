package entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Entity {
    public Rectangle hitBox;
    public Rectangle actionHitBox;
    public  int screenX;
    public  int screenY;
    public int worldX, worldY;
    public int speed;
    public BufferedImage playerMovingImage;
    public BufferedImage[][] playerMovingSprite;
    public BufferedImage playerActionImage;
    public BufferedImage[][] playerActionSprite;
    public int direction;
    public int typeAction;
    public boolean moving = false;
    public boolean action = false;
    public int spriteNum = 0;
    public int spriteCounter = 0;
}
