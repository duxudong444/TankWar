package com.dxd;

import java.awt.*;
import java.util.ArrayList;

public abstract class Tank extends GameObject {
    //尺寸
    public int width = 40;
    public int height = 50;
    //坦克速度
    public int speed = 3;
    //坦克初始方向
    public Direction direction = Direction.DOWN;
    //四个方向图片
    public String upImg;
    public String leftImg;
    public String rightImg;
    public String downImg;

    //攻击冷却时间
    private boolean attackCoolDown = true;
    //坦克是否存货
    boolean alive;

    public Tank(String img, int x, int y, GamePanel gamePanel,
                String upImg, String leftImg, String rightImg, String downImg){
        super(img, x, y, gamePanel);
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }
    @Override
    public abstract void paintSelft(Graphics g);
    @Override
    public abstract Rectangle gerRec();

    //坦克移动后绘制出来
    public void setImg(String img){
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }
    //坦克进行上下左右移动
    public void leftWard(){
        //坦克移动添加碰墙检测
        if(!meetWall(x-speed, y)&&!moveToBorer(x-speed, y)){
            x -= speed;
        }
        setImg(leftImg);
        direction = Direction.LEFT;
    }
    public void rightWard(){
        if(!meetWall(x+speed, y)&&!moveToBorer(x+speed, y)) {
            x += speed;
        }
        setImg(rightImg);
        direction = Direction.RIGHT;
    }
    public void upWard(){
        if(!meetWall(x, y-speed)&&!moveToBorer(x, y-speed)) {
            y -= speed;
        }
        setImg(upImg);
        direction = Direction.UP;
    }
    public void downWard(){
        if(!meetWall(x, y+speed)&&!moveToBorer(x, y+speed)) {
            y += speed;
        }
        setImg(downImg);
        direction = Direction.DOWN;
    }
    //坦克发射子弹
    public void attack(){
        //初始化子弹
        //首先判断冷却完成与否
        if(attackCoolDown && alive) {
            Point p = this.getHeadPoint();
            Bullet bullet = new Bullet("images/bullet/bulletYellow.gif", p.x, p.y, this.gamePanel, this.direction);
            //将绘制好的子弹加入到链表中
            this.gamePanel.bulletList.add(bullet);
            //开始计算休眠时间,让线程开始
            new AttackCD().start();
        }
    }
    //建立新线程，让线程休眠1000ms
    class AttackCD extends Thread{
        public void run(){
            //将攻击状态设为false
            attackCoolDown = false;
            //休眠一秒
            try{
                //攻击冷却时间为1000ms
                long attackCoolDownTime = 1000;
                Thread.sleep(attackCoolDownTime);
            }catch (Exception e){
                e.printStackTrace();
            }
            //休眠一秒后将攻击状态设为true
            attackCoolDown = true;
            this.stop();
        }
    }


    //计算坦克头部坐标，确定子弹发射位置
    public Point getHeadPoint(){
        switch (direction){
            case UP:
                return new Point(x+width/2, y);
            case DOWN:
                return new Point(x+width/2, y+height);
            case LEFT:
                return new Point(x, y+height/2);
            case RIGHT:
                return new Point(x+width, y+height/2);
            default:
                return null;
        }

    }
    //坦克与围墙碰撞检测
    public boolean meetWall(int x, int y){
        //获取围墙
        ArrayList<Wall> wallList = this.gamePanel.wallList;
        //获取下一步坦克的位置矩形
        Rectangle next = new Rectangle(x, y, width, height);
        //遍历围墙
        for(Wall wall:wallList){
            if(next.intersects(wall.gerRec())){
                return true;
            }
        }
        return false;
    }
    //坦克与边界碰撞检测
    public boolean moveToBorer(int x, int y){
        if(x < 0){
            return true;
        }else if (y < 0){
            return true;
        }else if (x + width > this.gamePanel.getWidth()){
            return true;
        }else if(y + height > this.gamePanel.getHeight()){
            return true;
        }
        return false;
    }

}
