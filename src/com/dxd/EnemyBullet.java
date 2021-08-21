package com.dxd;

import java.awt.*;
import java.util.ArrayList;
//这里犯了一个大错，我继承了bullen类的全部方法，因此如果没有对bullet方法对于hit的重写，子弹将对bot也起作用！
public class EnemyBullet extends Bullet{
    public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }

    @Override
    public void paintSelft(Graphics g) {
        super.paintSelft(g);
        go();
        hit();
    }

    @Override
    public Rectangle gerRec() {
        return super.gerRec();
    }
    //编写碰撞方法，判断是否与player相碰
    public void hit(){
        ArrayList<Tank> pList = this.gamePanel.playerList;
        for(Tank player:pList){
            if(player.gerRec().intersects(this.gerRec())){
                if(!this.gamePanel.blastList.isEmpty()) {
                    this.gamePanel.blastList.clear();
                    this.gamePanel.blastList.add(new Blast((player.x - 34), (player.y - 34)));
                }else{
                    this.gamePanel.blastList.add(new Blast((player.x - 34), (player.x - 34)));
                }
                this.gamePanel.playerList.remove(player);
                player.alive = false;
                this.gamePanel.removeList.add(this);
                break;
            }
        }

    }
}
