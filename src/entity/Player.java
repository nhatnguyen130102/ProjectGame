package entity;

import main.GamePanel;
import main.Keyhandler;
import main.Mousehandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
        typeAction = "";
    }

    public void getPlayerMovingImage() {
        try {
            playerMovingImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Basic Charakter Spritesheet.png")));
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
            playerActionImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Basic Charakter Actions.png")));
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

        action = mouseH.leftMousePressed && !typeAction.isEmpty();
        if (action) {
            spriteActionCounter++;
            if (spriteActionCounter > 20) {
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
        BufferedImage image;

        int row;
        int rowAction;
        row = switch (direction) {
            case "up" -> 1;
            case "down" -> 0;
            case "left" -> 2;
            case "right" -> 3;
            default -> 0;
        };


        rowAction = switch (typeAction) {
            case "hoe" -> switch (direction) {
                case "down" -> 0;
                case "up" -> 1;
                case "left" -> 2;
                case "right" -> 3;
                default -> 0;
            };
            case "chop" -> switch (direction) {
                case "up" -> 5;
                case "down" -> 4;
                case "left" -> 6;
                case "right" -> 7;
                default -> 0;
            };
            case "water" -> switch (direction) {
                case "up" -> 9;
                case "down" -> 8;
                case "left" -> 10;
                case "right" -> 11;
                default -> 0;
            };
            default -> 0;
        };

        if (!action) {
            image = playerMovingSprite[row][spriteNum];
        } else {
            image = playerActionSprite[rowAction][spriteActionNum];
        }
        graphics2D.drawImage(image, x, y, gamePanel.TILESIZE * 3, gamePanel.TILESIZE * 3, null);
    }
}
