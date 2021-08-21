package com.dxd;

import java.awt.*;
import java.util.ArrayList;

public abstract class Tank extends GameObject {
    //�ߴ�
    public int width = 40;
    public int height = 50;
    //̹���ٶ�
    public int speed = 3;
    //̹�˳�ʼ����
    public Direction direction = Direction.DOWN;
    //�ĸ�����ͼƬ
    public String upImg;
    public String leftImg;
    public String rightImg;
    public String downImg;

    //������ȴʱ��
    private boolean attackCoolDown = true;
    //̹���Ƿ���
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

    //̹���ƶ�����Ƴ���
    public void setImg(String img){
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }
    //̹�˽������������ƶ�
    public void leftWard(){
        //̹���ƶ������ǽ���
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
    //̹�˷����ӵ�
    public void attack(){
        //��ʼ���ӵ�
        //�����ж���ȴ������
        if(attackCoolDown && alive) {
            Point p = this.getHeadPoint();
            Bullet bullet = new Bullet("images/bullet/bulletYellow.gif", p.x, p.y, this.gamePanel, this.direction);
            //�����ƺõ��ӵ����뵽������
            this.gamePanel.bulletList.add(bullet);
            //��ʼ��������ʱ��,���߳̿�ʼ
            new AttackCD().start();
        }
    }
    //�������̣߳����߳�����1000ms
    class AttackCD extends Thread{
        public void run(){
            //������״̬��Ϊfalse
            attackCoolDown = false;
            //����һ��
            try{
                //������ȴʱ��Ϊ1000ms
                long attackCoolDownTime = 1000;
                Thread.sleep(attackCoolDownTime);
            }catch (Exception e){
                e.printStackTrace();
            }
            //����һ��󽫹���״̬��Ϊtrue
            attackCoolDown = true;
            this.stop();
        }
    }


    //����̹��ͷ�����꣬ȷ���ӵ�����λ��
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
    //̹����Χǽ��ײ���
    public boolean meetWall(int x, int y){
        //��ȡΧǽ
        ArrayList<Wall> wallList = this.gamePanel.wallList;
        //��ȡ��һ��̹�˵�λ�þ���
        Rectangle next = new Rectangle(x, y, width, height);
        //����Χǽ
        for(Wall wall:wallList){
            if(next.intersects(wall.gerRec())){
                return true;
            }
        }
        return false;
    }
    //̹����߽���ײ���
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
