package com.dxd;

import java.awt.*;

public class Base extends GameObject{
    //Ҫ���������г�ʼ������ʾbase�������50*50
    int length = 50;
    public Base(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelft(Graphics g) {
        g.drawImage(img, x, y, null);

    }

    @Override
    public Rectangle gerRec() {
        return new Rectangle(x, y, length, length);
    }
}
