package spaceRhythm.Game.GameObjects;

import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;


public class BulletPattern {
    private Handler handler;
    private GameObject boss;
    private SpriteSheet ss;
    public static int spiralDegree = 0;
    public static int crossState = 0;
    public static int crossCount = 0;
    public BulletPattern (Handler handler, SpriteSheet ss) {
        this.handler = handler;
        this.ss = ss;
    }

    public void setBoss() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Boss)
                this.boss = handler.object.get(i);
        }
    }

    public void Circle() {
        setBoss();
        for (int i = 0; i <= 360; i+=1) {
            int dirX = (int) (boss.getX()+ 20 + (5000 * Math.cos(i * Math.PI/180)));
            int dirY = (int) (boss.getY()+ 32 + (5000 * Math.sin(i * Math.PI/180)));
            handler.addObject(new BulletYellow( (int)(boss.getX()+20),
                    (int)(boss.getY()+32),ObjectID.BulletYellow,handler,dirX,dirY,ss));
        }

    }

    public void Spiral() {
        setBoss();
        for (int i = 0; i < 6; i++) {
            int dirX = (int) (boss.getX()+ 20 + (5000 * Math.cos((spiralDegree + i*15) * Math.PI/180)));
            int dirY = (int) (boss.getY()+ 32 + (5000 * Math.sin((spiralDegree + i*15) * Math.PI/180)));
            handler.addObject(new BulletYellow( (int)(boss.getX()+20),
                    (int)(boss.getY()+32),ObjectID.BulletYellow,handler,dirX,dirY,ss));
        }
        for (int i = 0; i < 6; i++) {
            int dirX = (int) (boss.getX()+ 20 + (5000 * Math.cos((spiralDegree + 180 + i*15) * Math.PI/180)));
            int dirY = (int) (boss.getY()+ 32 + (5000 * Math.sin((spiralDegree + 180 + i*15) * Math.PI/180)));
            handler.addObject(new BulletYellow( (int)(boss.getX()+20),
                    (int)(boss.getY()+32),ObjectID.BulletYellow,handler,dirX,dirY,ss));
        }
        spiralDegree += 15;
    }

    public void Cross() {
        crossCount++;
        setBoss();
        if (crossState == 0) {
            for (int i = 0; i < 360; i+=90) {
                int dirX = (int) (boss.getX()+ 20 + (5000 * Math.cos(i * Math.PI/180)));
                int dirY = (int) (boss.getY()+ 32 + (5000 * Math.sin(i * Math.PI/180)));
                handler.addObject(new BulletYellow( (int)(boss.getX()+20),
                        (int)(boss.getY()+32),ObjectID.BulletYellow,handler,dirX,dirY,ss)); }
            if (crossCount % 4 ==0) crossState = 1;
        }
        else {
            for (int i = 60; i < 360; i+=90) {
                int dirX = (int) (boss.getX()+ 20 + (5000 * Math.cos(i * Math.PI/180)));
                int dirY = (int) (boss.getY()+ 32 + (5000 * Math.sin(i * Math.PI/180)));
                handler.addObject(new BulletYellow( (int)(boss.getX()+20),
                        (int)(boss.getY()+32),ObjectID.BulletYellow,handler,dirX,dirY,ss));
            }
            if (crossCount % 4 ==0) crossState = 0;
        }
        if (crossCount == 4) crossCount = 0;
    }
}
