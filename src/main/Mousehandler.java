package main;

import tile.TileManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mousehandler implements MouseListener {
    public boolean leftMousePressed, rightMousePressed;
    GamePanel gamePanel;

    public Mousehandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if (code == MouseEvent.BUTTON1) {
            leftMousePressed = true;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if (code == MouseEvent.BUTTON1) {
            leftMousePressed = false;

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
