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

    //指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    //指针初始纵坐标
    int y = 150;
    //游戏模式 0游戏未开始，1 单人模式 2双人模式 3表示胜利，4表示失败 5表示暂停
    int state = 0;
    int a = 1;
    //确定敌方坦克添加的频率
    int count = 0;
    //计算敌人数量
    int enemyCount = 0;
    //定义双缓存图片
    Image offScreenImage = null;

    //添加子弹列表
    ArrayList<Bullet> bulletList = new ArrayList<>();
    //创建bot坦克列表，以便批量生成坦克
    ArrayList<Bot> botList = new ArrayList<>();
    //创建一个removeList,表示要删除的子弹
    ArrayList<Bullet> removeList = new ArrayList<>();
    //创建玩家列表
    ArrayList<Tank> playerList = new ArrayList<>();
    //创建围墙列表
    ArrayList<Wall> wallList = new ArrayList<>();
    //创建基地列表
    ArrayList<Base> baseList = new ArrayList<>();
    //创建爆炸列表
    ArrayList<Blast> blastList = new ArrayList<>();

    //PlayOne
    PlayerOne playone = new PlayerOne("images/player1/p1tankU.gif", 125, 510, this,
            "images/player1/p1tankU.gif", "images/player1/p1tankL.gif", "images/player1/p1tankR.gif",
            "images/player1/p1tankD.gif");
    //PlayTwo
    PlayerTwo playTwo = new PlayerTwo("images/player2/p2tankU.gif", 625, 510, this,
            "images/player2/p2tankU.gif", "images/player2/p2tankL.gif", "images/player2/p2tankR.gif",
            "images/player2/p2tankD.gif");
    //创建基地
    Base baseOne = new Base("images/star.gif", 365, 560, this);
    //定义bot坦克变量
    //Bot bot = new Bot("images/enemy/enemy1U", 500, 110, this,
//            "images/enemy/enemy1U", "images/enemy/enemy1L",
//            "images/enemy/enemy1R","images/enemy/enemy1D");


    //窗口的启动方法
    public void launch(){
        setTitle("坦克大战");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setResizable(false);
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new GamePanel.KeyMonitor());

        //添加围墙
        for(int i = 0; i< 14; i++){
            wallList.add(new Wall("images/walls.gif", i*60 ,170, this ));
        }
        wallList.add(new Wall("images/walls.gif", 305 ,560,this ));
        wallList.add(new Wall("images/walls.gif", 305 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 365 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 400 ,500,this ));
        wallList.add(new Wall("images/walls.gif", 400 ,560,this ));
        //添加基地
        baseList.add(baseOne);

        //重绘
        while (true){
            //游戏胜利判定
            if(botList.size() == 0 && enemyCount == 10){
                state = 3;
            }
            //游戏失败判定,因为一开始都是空的，所有要添加前提条件
            if((playerList.size() == 0 || baseList.size() == 0) && (state == 1 ||state == 2)){
                state = 4;
            }
            //限制bot的生成速度和生成数量，当bot数量达到10时停止
            if(count%100 == 1 && enemyCount < 10 && (state == 1 || state ==2)) {
                Random rand = new Random();
                int randX = rand.nextInt(800);
                //批量添加地方坦克
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
//    paint()方法
    @Override
    public void paint(Graphics g){
        //创建和窗口一样大小的图片
        if(offScreenImage == null){
            offScreenImage = this.createImage(width, height);
        }
        //获取这个图片的画笔
        Graphics gImage = offScreenImage.getGraphics();

        //设置背景颜色
        gImage.setColor(Color.lightGray);
        //填充整个画布
        gImage.fillRect(10,10,width,height);
        //改变画笔颜色
        gImage.setColor(Color.blue);
        //改变文字大小和样式
        gImage.setFont(new Font("仿宋", Font.BOLD, 50));

        //state = 0;游戏未开始,state = 1或2时，游戏开始
        if(state == 0) {
            gImage.drawString("选择游戏模式", 220, 100);
            gImage.drawString("单人模式", 220, 200);
            gImage.drawString("双人模式", 220, 300);
            gImage.drawImage(select, 160, y, null);
        }else if(state == 1 || state == 2){
            gImage.setFont(new Font("仿宋", Font.BOLD, 30));
            gImage.setColor(Color.RED);
            gImage.drawString("敌人数量"+botList.size(), 10,70);


            //绘制玩家
            for(Tank player:playerList){
                player.paintSelft(gImage);
            }

            for(Bullet bullet:bulletList){
                bullet.paintSelft(gImage);
            }
            //绘制bot坦克
            for(Bot bot:botList){
                bot.paintSelft(gImage);
            }
            //绘制围墙
            for(Wall wall:wallList){
                wall.paintSelft(gImage);
            }
            //绘制基地
            for(Base base:baseList){
                base.paintSelft(gImage);
            }
            //绘制爆炸
            for(Blast blast:blastList){
                blast.paintSelft(gImage);
            }
            //删除子弹
            bulletList.removeAll(removeList);
            //打印子弹列表数目
            //System.out.println(bulletList.size());
            //打印爆炸列表的数目(一直为1）
            //System.out.println(blastList.size());

            count++;
        }else if(state == 3){
            gImage.drawString("游戏胜利", 220, 100);
        }else if(state == 4){
            gImage.drawString("游戏失败", 220, 100);
        }else if(state == 5){
            gImage.drawString("游戏暂停", 220, 100);
        }

        //将缓存区绘制好的图片整个绘制到容器的画布中
        g.drawImage(offScreenImage, 0, 0, null);



    }
//    键盘监视器
    class KeyMonitor extends KeyAdapter{
//    按下键盘
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

//    main方法
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }

}
