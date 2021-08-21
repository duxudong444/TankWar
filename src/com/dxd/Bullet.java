package com.dxd;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject{
    //���캯��


    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    //���Ƴߴ����ٶ�
    int width = 10;
    int height = 10;
    int speed = 7;
    //˵������
    Direction direction;

    //�̳и���ķ���

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);
        go();
    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, width, height);
    }

    //��д�ƶ��ķ������������ƶ�
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
    //ʹ��direction�жϷ�����ȷ���ƶ�����
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
        moveToBorder();//д�õķ����ǵ÷����ػ����򣬲��ܹ�������ͼ����
    }
    //��д��ײ�������ж��Ƿ����Bot
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
    //��д��ײΧǽ����
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
    //��д��ײ���ط���
    public void hitBase(){
        for(Base base:this.gamePanel.baseList){
            if(base.gerRec().intersects(this.gerRec())){
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    //�ӵ��Ƿ���磬�������ɾ���ӵ���������������Ӱ���ڴ�
    public void moveToBorder(){
        if(x < 0 || x+width > this.gamePanel.getWidth()){
            this.gamePanel.removeList.add(this);
        }
        if(y < 0 || y+height > this.gamePanel.getHeight()){
            this.gamePanel.removeList.add(this);
        }
    }

}
