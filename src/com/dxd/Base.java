package com.dxd;

import java.awt.*;

public class Base extends GameObject{
    //要给变量进行初始化，表示base的面积是50*50
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
