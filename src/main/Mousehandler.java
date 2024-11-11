package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mousehandler implements MouseListener {
    public boolean leftMousePressed, rightMousePressed;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if(code == MouseEvent.BUTTON1){
            leftMousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code == MouseEvent.BUTTON1){
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
