package entity;

import main.GamePanel;
import main.Keyhandler;
import main.Mousehandler;
import utilities.LoadAndSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static utilities.Constants.PlayerConstant.*;
import static utilities.Constants.PlayerConstant.getSpriteAmount;
import static utilities.Helper.CanMoveHere;

public class Player extends Entity {
    GamePanel gamePanel;
    Keyhandler keyH;
    Mousehandler mouseH;
    private int[][] mapData;
    public int actionHBX = worldX;
    public int actionHBY = worldY;

    public Player(GamePanel gamePanel, Keyhandler keyH, Mousehandler mouseH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;
        this.mouseH = mouseH;
        screenX = (gamePanel.SCREENWIDTH / 2) - (gamePanel.TILESIZE / 2);
        screenY = (gamePanel.SCREENHEIGHT / 2) - (gamePanel.TILESIZE / 2);

        setDefaultValues();
        getPlayerMovingImage();
        getPlayerActionImage();
        hitBox = new Rectangle(worldX, worldY, gamePanel.TILESIZE, gamePanel.TILESIZE);
        actionHitBox = new Rectangle(worldX, worldY, gamePanel.TILESIZE / 2, gamePanel.TILESIZE / 2);
    }

    public void loadMapData(int[][] mapData) {
        this.mapData = mapData;
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        speed = 4;
        direction = DOWN;
        typeAction = -1;
    }

    public void getPlayerMovingImage() {

        playerMovingImage = LoadAndSave.LoadImage("/player/Basic Charakter Spritesheet.png");
        // width = height = 48
        assert playerMovingImage != null;
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
        if (keyH.tool != -1) {
            typeAction = keyH.tool;
        }

        action = mouseH.leftMousePressed && typeAction != -1;


        // Nếu đang thực hiện action, không cho phép di chuyển
        if (!action) {
            // Kiểm tra phím di chuyển và chỉ thay đổi `direction` khi có phím di chuyển được nhấn
            boolean directionChanged = false;
            if (keyH.upPressed) {
                direction = UP;
                moving = true;
                directionChanged = true;
            } else if (keyH.downPressed) {
                direction = DOWN;
                moving = true;
                directionChanged = true;
            } else if (keyH.leftPressed) {
                direction = LEFT;
                moving = true;
                directionChanged = true;
            } else if (keyH.rightPressed) {
                direction = RIGHT;
                moving = true;
                directionChanged = true;
            }

            // Nếu không có phím di chuyển nào được nhấn, thì moving = false
            if (!directionChanged) {
                moving = false;
            }

            // Kiểm tra và cập nhật vị trí nếu `moving` là true
            if (moving && CanMoveHere(worldX, worldY, GamePanel.TILESIZE, GamePanel.TILESIZE, mapData, direction)) {
                switch (direction) {
                    case UP -> worldY -= speed;
                    case DOWN -> worldY += speed;
                    case LEFT -> worldX -= speed;
                    case RIGHT -> worldX += speed;
                }
            }
        }

        // Cập nhật sprite cho animation
        if (moving || action) {
            int getSpriteNum = 1;
            if (moving) getSpriteNum = getSpriteAmount(direction);
            if (action) getSpriteNum = getSpriteActionAmount(typeAction, direction);

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum + 1) % getSpriteNum;
                if (action && spriteNum == 1 && typeAction == HOE) {
                    handlerHoe();
                }
                if (action && spriteNum == 1 && typeAction == WATER) {
                    handlerPlanting();
                }
                spriteCounter = 0;


            }
        } else {
            spriteNum = 0;
        }
        int offset = gamePanel.TILESIZE / 2; // khoảng cách offset cho actionHitBox

        switch (direction) {
            case UP -> {
                actionHBX = worldX + offset;
                actionHBY = worldY - offset * 2;
                actionHitBox.x = screenX + offset;
                actionHitBox.y = screenY - offset * 2;
            }
            case DOWN -> {
                actionHBX = worldX + offset / 2;
                actionHBY = worldY + offset * 3;
                actionHitBox.x = screenX + offset / 2;
                actionHitBox.y = screenY + offset * 3;
            }
            case LEFT -> {
                actionHBX = worldX - offset * 2;
                actionHBY = worldY + offset / 2;
                actionHitBox.x = screenX - offset * 2;
                actionHitBox.y = screenY + offset / 2;
            }
            case RIGHT -> {
                actionHBX = worldX + offset * 3;
                actionHBY = worldY + offset;
                actionHitBox.x = screenX + offset * 3;
                actionHitBox.y = screenY + offset;
            }
        }


        hitBox.x = worldX;
        hitBox.y = worldY;
    }

    public void handlerHoe() {
        if (action && typeAction == HOE) {
            gamePanel.getTileManager().interactWithTile(actionHBX, actionHBY);
        }
    }

    public void handlerPlanting() {
        if (action && typeAction == WATER && gamePanel.getTileManager().hasModifiedTile(actionHBX, actionHBY)) {
            gamePanel.getLayer1().planting(actionHBX, actionHBY);
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image;
        int row = 0;
        if (moving) {
            row = getMoving(direction);
            image = playerMovingSprite[row][spriteNum];
        } else if (action) {
            row = getAction(typeAction, direction);
            image = playerActionSprite[row][spriteNum];
        } else {
            image = playerMovingSprite[direction][spriteNum];
        }


        int cameraX = worldX - screenX;
        int cameraY = worldY - screenY;

        int maxCameraX = gamePanel.TILESIZE * 50 - gamePanel.SCREENWIDTH;
        int maxCameraY = gamePanel.TILESIZE * 50 - gamePanel.SCREENHEIGHT;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > maxCameraX) cameraX = maxCameraX;
        if (cameraY > maxCameraY) cameraY = maxCameraY;

        graphics2D.drawImage(image, screenX - gamePanel.TILESIZE, screenY - gamePanel.TILESIZE, gamePanel.TILESIZE * 3, gamePanel.TILESIZE * 3, null);

        graphics2D.setColor(Color.RED); // Màu đỏ đậm cho đường viền
        graphics2D.drawRect(screenX, screenY, hitBox.width, hitBox.height);

        graphics2D.drawRect(actionHitBox.x, actionHitBox.y, actionHitBox.width, actionHitBox.height);

    }

    public int getActionHitBoxX() {
        return actionHitBox.x;
    }

    public int getActionHitBoxY() {
        return actionHitBox.y;
    }

    public Rectangle getActionHitBox() {
        return actionHitBox;
    }
}
