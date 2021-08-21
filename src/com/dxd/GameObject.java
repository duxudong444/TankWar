package com.dxd;

import java.awt.*;

public abstract class GameObject {
    //ͼƬ
    public Image img;
    //����
    public int x;
    public int y;
    //����
    public GamePanel gamePanel;

    public GameObject(){}
    public GameObject(String img, int x, int y, GamePanel gamePanel){
        this.img = Toolkit.getDefaultToolkit().getImage(img);
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    //���Ʒ���
    public abstract void paintSelft(Graphics g);

    public abstract Rectangle gerRec();



}
