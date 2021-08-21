package com.dxd;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject{
    //构造函数


    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    //绘制尺寸与速度
    int width = 10;
    int height = 10;
    int speed = 7;
    //说明方向
    Direction direction;

    //继承父类的方法

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        go();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }

    //编写移动的方法上下左右移动
    public void leftWard(){
        x -= speed;
    }
    public void rightWard(){
        x += speed;
    }
    public void upWard(){
        y -= speed;
    }
    public void downWard(){
        y += speed;
    }
    //使用direction判断方向，来确定移动方向
    public void go(){
        switch (direction){
            case LEFT:
                leftWard();
                break;
            case UP:
                upWard();
                break;
            case DOWN:
                downWard();
                break;
            case RIGHT:
                rightWard();
                break;
        }
        hit();
        hitWall();
        hitBase();
        moveToBorder();//写好的方法记得放在重绘区域，才能够绘制在图形上
    }
    //编写碰撞方法，判断是否击中Bot
    public void hit(){
        ArrayList<Bot> botList = this.gamePanel.botList;
        for(Bot bot:botList){
            if(bot.gerRec().intersects(this.gerRec())){
                if(!this.gamePanel.blastList.isEmpty()) {
                    this.gamePanel.blastList.clear();
                    this.gamePanel.blastList.add(new Blast((bot.x - 34), (bot.y - 34)));
                }else{
                    this.gamePanel.blastList.add(new Blast((bot.x - 34), (bot.y - 34)));
                }
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }
    //编写碰撞围墙方法
    public void hitWall(){
        ArrayList<Wall> wallList = this.gamePanel.wallList;
        for(Wall wall:wallList){
            if(wall.gerRec().intersects(this.gerRec())){
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    //编写碰撞基地方法
    public void hitBase(){
        for(Base base:this.gamePanel.baseList){
            if(base.gerRec().intersects(this.gerRec())){
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    //子弹是否出界，如果出界删除子弹，避免无限生成影响内存
    public void moveToBorder(){
        if(x < 0 || x+width > this.gamePanel.getWidth()){
            this.gamePanel.removeList.add(this);
        }
        if(y < 0 || y+height > this.gamePanel.getHeight()){
            this.gamePanel.removeList.add(this);
        }
    }

}
