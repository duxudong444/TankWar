package com.dxd;

import java.awt.*;
import java.util.Random;

public class Bot extends Tank {
    public Bot(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    //改变方向的变量
    int moveTime = 0;

    //获取随机方向
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

    //编写移动方法
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
    //编写攻击方法
    public void attack(){
        Point p = getHeadPoint();
        //随机生成数，随机发射子弹
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
