package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilities.Constants.PlayerConstant.*;

public class Keyhandler implements KeyListener{

    public boolean upPressed,downPressed, leftPressed, rightPressed;
    public int tool;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_1){
            tool = HOE;
        }
        if(code == KeyEvent.VK_2){
            tool = CHOP;
        }
        if(code == KeyEvent.VK_3){
            tool = WATER;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

    }
}
