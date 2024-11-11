package entity;

import main.GamePanel;
import main.Keyhandler;
import main.Mousehandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    Keyhandler keyH;
    Mousehandler mouseH;

    public Player(GamePanel gamePanel, Keyhandler keyH, Mousehandler mouseH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        this.mouseH = mouseH;
        setDefaultValues();
        getPlayerMovingImage();
        getPlayerActionImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
        typeAction = "hoe";
    }

    public void getPlayerMovingImage() {
        try {
            playerMovingImage = ImageIO.read(getClass().getResourceAsStream("/player/Basic Charakter Spritesheet.png"));
            // width = height = 48
            int widthImage = playerMovingImage.getWidth();
            int heightImage = playerMovingImage.getHeight();
            int cols = 4;
            int rows = 4;
            int width = widthImage / cols;
            int height = heightImage / rows;
            playerMovingSprite = new BufferedImage[rows][cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    playerMovingSprite[y][x] = playerMovingImage.getSubimage(x * width, y * height, width, height);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getPlayerActionImage() {
        try {
            playerActionImage = ImageIO.read(getClass().getResourceAsStream("/player/Basic Charakter Actions.png"));
            // width = height = 48
            int widthImage = playerActionImage.getWidth();
            int heightImage = playerActionImage.getHeight();
            int cols = 2;
            int rows = 12;
            int width = widthImage / cols;
            int height = heightImage / rows;
            playerActionSprite = new BufferedImage[rows][cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    playerActionSprite[y][x] = playerActionImage.getSubimage(x * width, y * height, width, height);
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
        } else {
            moving = false;
        }
        if (keyH.tool != null) {
            typeAction = keyH.tool;
        }

        if (mouseH.leftMousePressed) {
            action = true;
        } else {
            action = false;
        }
        if (action) {
            spriteActionCounter++;
            if (spriteActionCounter > 10) {
                spriteActionNum = (spriteActionNum + 1) % 2;
                spriteActionCounter = 0;
            }
        } else {
            spriteActionNum = 0;
        }
        if (moving) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        int row;
        int rowAction = 0;
        switch (direction) {
            case "up":
                row = 1;
                break;
            case "down":
                row = 0;
                break;
            case "left":
                row = 2;
                break;
            case "right":
                row = 3;
                break;
            default:
                row = 0;
                break;
        }


        switch (typeAction) {
            case "hoe":
                switch (direction) {
                    case "down":
                        rowAction = 0;
                        break;
                    case "up":
                        rowAction = 1;
                        break;
                    case "left":
                        rowAction = 2;
                        break;
                    case "right":
                        rowAction = 3;
                        break;
                    default:
                        rowAction = 0;
                }
                break;

            case "chop":
                switch (direction) {
                    case "up":
                        rowAction = 5;
                        break;
                    case "down":
                        rowAction = 4;
                        break;
                    case "left":
                        rowAction = 6;
                        break;
                    case "right":
                        rowAction = 7;
                        break;
                    default:
                        rowAction = 0;
                }
                break;

            default:
                rowAction = 0;
                break;
        }

        if (!action) {
            image = playerMovingSprite[row][spriteNum];
        } else {
            image = playerActionSprite[rowAction][spriteActionNum];
        }
        graphics2D.drawImage(image, x, y, gamePanel.TILESIZE * 3, gamePanel.TILESIZE * 3, null);
    }
}
