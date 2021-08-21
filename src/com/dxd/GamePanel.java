package com.dxd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    int width = 800;
    int height = 610;

    //ָ��ͼƬ
    Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    //ָ���ʼ������
    int y = 150;
    //��Ϸģʽ 0��Ϸδ��ʼ��1 ����ģʽ 2˫��ģʽ 3��ʾʤ����4��ʾʧ�� 5��ʾ��ͣ
    int state = 0;
    int a = 1;
    //ȷ���з�̹����ӵ�Ƶ��
    int count = 0;
    //�����������
    int enemyCount = 0;
    //����˫����ͼƬ
    Image offScreenImage = null;

    //����ӵ��б�
    ArrayList<Bullet> bulletList = new ArrayList<>();
    //����bot̹���б��Ա���������̹��
    ArrayList<Bot> botList = new ArrayList<>();
    //����һ��removeList,��ʾҪɾ�����ӵ�
    ArrayList<Bullet> removeList = new ArrayList<>();
    //��������б�
    ArrayList<Tank> playerList = new ArrayList<>();
    //����Χǽ�б�
    ArrayList<Wall> wallList = new ArrayList<>();
    //���������б�
    ArrayList<Base> baseList = new ArrayList<>();
    //������ը�б�
    ArrayList<Blast> blastList = new ArrayList<>();

    //PlayOne
    PlayerOne playone = new PlayerOne("images/player1/p1tankU.gif", 125, 510, this,
            "images/player1/p1tankU.gif", "images/player1/p1tankL.gif", "images/player1/p1tankR.gif",
            "images/player1/p1tankD.gif");
    //PlayTwo
    PlayerTwo playTwo = new PlayerTwo("images/player2/p2tankU.gif", 625, 510, this,
            "images/player2/p2tankU.gif", "images/player2/p2tankL.gif", "images/player2/p2tankR.gif",
            "images/player2/p2tankD.gif");
    //��������
    Base baseOne = new Base("images/star.gif", 365, 560, this);
    //����bot̹�˱���
    //Bot bot = new Bot("images/enemy/enemy1U", 500, 110, this,
//            "images/enemy/enemy1U", "images/enemy/enemy1L",
//            "images/enemy/enemy1R","images/enemy/enemy1D");


    //���ڵ���������
    public void launch(){
        setTitle("̹�˴�ս");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setResizable(false);
        setVisible(true);
        //��Ӽ��̼�����
        this.addKeyListener(new GamePanel.KeyMonitor());

        //���Χǽ
        for(int i = 0; i< 14; i++){
            wallList.add(new Wall("images/walls.gif", i*60 ,170, this ));
        }
        wallList.add(new Wall("images/walls.gif", 305 ,560,this ));
        wallList.add(new Wall("images/walls.gif", 305 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 365 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 400 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 400 ,560,this ));
        //��ӻ���
        baseList.add(baseOne);

        //�ػ�
        while (true){
            //��Ϸʤ���ж�
            if(botList.size() == 0 && enemyCount == 10){
                state = 3;
            }
            //��Ϸʧ���ж�,��Ϊһ��ʼ���ǿյģ�����Ҫ���ǰ������
            if((playerList.size() == 0 || baseList.size() == 0) && (state == 1 ||state == 2)){
                state = 4;
            }
            //����bot�������ٶȺ�������������bot�����ﵽ10ʱֹͣ
            if(count%100 == 1 && enemyCount < 10 && (state == 1 || state ==2)) {
                Random rand = new Random();
                int randX = rand.nextInt(800);
                //������ӵط�̹��
                botList.add(new Bot("images/enemy/enemy1U.gif", randX, 110, this,
                        "images/enemy/enemy1U.gif", "images/enemy/enemy1L.gif",
                        "images/enemy/enemy1R.gif", "images/enemy/enemy1D.gif"));
                enemyCount++;
            }
            repaint();
            try{
                Thread.sleep(25);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
//    paint()����
    @Override
    public void paint(Graphics g){
        //�����ʹ���һ����С��ͼƬ
        if(offScreenImage == null){
            offScreenImage = this.createImage(width, height);
        }
        //��ȡ���ͼƬ�Ļ���
        Graphics gImage = offScreenImage.getGraphics();

        //���ñ�����ɫ
        gImage.setColor(Color.lightGray);
        //�����������
        gImage.fillRect(10,10,width,height);
        //�ı仭����ɫ
        gImage.setColor(Color.blue);
        //�ı����ִ�С����ʽ
        gImage.setFont(new Font("����", Font.BOLD, 50));

        //state = 0;��Ϸδ��ʼ,state = 1��2ʱ����Ϸ��ʼ
        if(state == 0) {
            gImage.drawString("ѡ����Ϸģʽ", 220, 100);
            gImage.drawString("����ģʽ", 220, 200);
            gImage.drawString("˫��ģʽ", 220, 300);
            gImage.drawImage(select, 160, y, null);
        }else if(state == 1 || state == 2){
            gImage.setFont(new Font("����", Font.BOLD, 30));
            gImage.setColor(Color.RED);
            gImage.drawString("��������"+botList.size(), 10,70);


            //�������
            for(Tank player:playerList){
                player.paintSelft(gImage);
            }

            for(Bullet bullet:bulletList){
                bullet.paintSelft(gImage);
            }
            //����bot̹��
            for(Bot bot:botList){
                bot.paintSelft(gImage);
            }
            //����Χǽ
            for(Wall wall:wallList){
                wall.paintSelft(gImage);
            }
            //���ƻ���
            for(Base base:baseList){
                base.paintSelft(gImage);
            }
            //���Ʊ�ը
            for(Blast blast:blastList){
                blast.paintSelft(gImage);
            }
            //ɾ���ӵ�
            bulletList.removeAll(removeList);
            //��ӡ�ӵ��б���Ŀ
            //System.out.println(bulletList.size());
            //��ӡ��ը�б����Ŀ(һֱΪ1��
            //System.out.println(blastList.size());

            count++;
        }else if(state == 3){
            gImage.drawString("��Ϸʤ��", 220, 100);
        }else if(state == 4){
            gImage.drawString("��Ϸʧ��", 220, 100);
        }else if(state == 5){
            gImage.drawString("��Ϸ��ͣ", 220, 100);
        }

        //�����������ƺõ�ͼƬ�������Ƶ������Ļ�����
        g.drawImage(offScreenImage, 0, 0, null);



    }
//    ���̼�����
    class KeyMonitor extends KeyAdapter{
//    ���¼���
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_1:
                    a = 1;
                    y = 150;
                    break;
                case KeyEvent.VK_2:
                    a = 2;
                    y = 250;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playone);
                    playone.alive = true;
                    if(a == 2){
                        playerList.add(playTwo);
                        playTwo.alive = true;
                    }
                    break;
                case KeyEvent.VK_P:
                    if(state != 5){
                        a = state;
                        state = 5;
                    }else{
                        state = a;
                        if(a == 0){
                            a = 1;
                        }
                    }
                default:
                    playone.keyPress(e);
                    playTwo.keyPress(e);
                    break;
            }
        }

    @Override
    public void keyReleased(KeyEvent e) {
        playone.keyRelease(e);
        playTwo.keyRelease(e);
    }
}

//    main����
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }

}
