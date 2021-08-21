package com.dxd;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerTwo extends Tank{

    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean down = false;

    public PlayerTwo(String img, int x, int y, GamePanel gamePanel,
                     String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel,upImg, leftImg, rightImg, downImg);
    }

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }

    //编写键盘事件，分别时按下键盘和松开键盘
    public void keyPress(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_K:
                attack();
            default:
                break;
        }
    }
    public void keyRelease(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            default:
                break;
        }
    }
    //编写键盘对应的移动方法
    public void move(){
        if(right){
            rightWard();
        }else if(left){
            leftWard();
        }else if(up){
            upWard();
        }else if(down){
            downWard();
        }
    }


}
