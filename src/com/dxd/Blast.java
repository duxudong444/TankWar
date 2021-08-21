package com.dxd;

import java.awt.*;

public class Blast extends GameObject {

        static Image[] image = new Image[8];
        //��¼��ը˳��
        int sequence = 0;
        //��ӱ�ըͼƬ
        static {
            for (int i = 0; i < 8; i++) {
                image[i] = Toolkit.getDefaultToolkit().getImage("images/blast/blast" +(i + 1)+".gif");
            }
        }

    public Blast(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    public Blast(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paintSelft(Graphics g) {
        if(sequence < 8){
             g.drawImage(image[sequence], x, y,null);
             sequence++;
            }

    }

    @Override
    public Rectangle gerRec() {
        return null;
    }
}
