package com.dxd;

import java.awt.*;
import java.util.Random;

public class Bot extends Tank {
    public Bot(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    //�ı䷽��ı���
    int moveTime = 0;

    //��ȡ�������
    public Direction randomDirection(){
        Random rand = new Random();
        int randDirection = rand.nextInt(4);
        switch (randDirection){
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.UP;
            default:
                return Direction.DOWN;

        }
    }

    //��д�ƶ�����
    public void go(){
        attack();
        if(moveTime > 20){
            direction = randomDirection();
            moveTime = 0;
        }else {
            moveTime += 1;
        }
        switch (direction){
            case UP:
                upWard();
                break;
            case DOWN:
                downWard();
                break;
            case LEFT:
                leftWard();
                break;
            case RIGHT:
                rightWard();
                break;
        }
    }
    //��д��������
    public void attack(){
        Point p = getHeadPoint();
        //�������������������ӵ�
        Random rand = new Random();
        int num = rand.nextInt(100);
        if(num < 2){
            this.gamePanel.bulletList.add(new EnemyBullet("images/bullet/bulletGreen.gif", p.x, p.y,
                    gamePanel, direction));
        }
    }


    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        go();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }
}
