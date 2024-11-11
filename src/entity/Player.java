package entity;

import main.GamePanel;
import main.Keyhandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    Keyhandler keyH;

    public Player(GamePanel gamePanel, Keyhandler keyH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/player/walkandidle.png"));
            // width = height = 24
            int widthImage = playerImage.getWidth();
            int heightImage = playerImage.getHeight();
            int cols = 8;
            int rows = 3;
            int width = widthImage / cols;
            int height = heightImage / rows;
            playerSprite = new BufferedImage[rows][cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    playerSprite[y][x] = playerImage.getSubimage(x * width, y * height, width, height);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            y -= speed;
            moving = true;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
            moving = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
            moving = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
            moving = true;
        }
        if (moving) {
            spriteCounter++;
            if (spriteCounter > 8) {
                spriteNum = (spriteNum + 1) % 8;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int row;
        switch (direction) {
            case "up":
                row = 1;
                break;
            case "down":
                row = 2;
                break;
            case "left":
                row = 1;
                break;
            case "right":
                row = 2;
                break;
            default:
                row = 0;
                break;
        }
        image = playerSprite[row][spriteNum];
        graphics2D.drawImage(image, x, y, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
    }
}
