package com.dxd;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank{

    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean down = false;

    public PlayerOne(String img, int x, int y, GamePanel gamePanel,
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

    //��д�����¼����ֱ�ʱ���¼��̺��ɿ�����
    public void keyPress(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_SPACE:
                attack();
            default:
                break;
        }
    }
    public void keyRelease(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            default:
                break;
        }
    }
    //��д���̶�Ӧ���ƶ�����
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
