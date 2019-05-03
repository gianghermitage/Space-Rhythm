package spaceRhythm.Game.GameObjects;

import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.util.Random;


public class BulletPattern {
    public static int spiralDegree = 0;
    public static int reversespiralDegree = 0;
    public static int crossState = 0;
    public static int crossCount = 0;
    public static int shotgunState = 0;
    public static int radius = 5000;
    public static int radiusSmall = 20;
    public static int offset = -60;
    private Handler handler;
    private GameObject boss;
    private GameObject player;
    private SpriteSheet ss;
    private Random r = new Random();

    ////////////////initiation
    public BulletPattern(Handler handler, SpriteSheet ss) {
        this.handler = handler;
        this.ss = ss;
    }

    public int getDistance() {
        return (int) (Math.sqrt(Math.pow((boss.getX() + 20) - player.getX(), 2) + Math.pow((boss.getY() + 32) - player.getY(), 2)));
    }

    public int getAngle(){
        if (player.getX() > (boss.getX() +20) && player.getY() > (boss.getY() + 32)) {
            return (int) ((Math.atan(((boss.getY() + 32) - player.getY())/((boss.getX() + 20) - player.getX()))) * 180 / Math.PI);
        }
        if (player.getX() < (boss.getX() +20)) {
            return (int) ((Math.atan(((boss.getY() + 32) - player.getY())/((boss.getX() + 20) - player.getX()))) * 180 / Math.PI + 180);
        }
        if (player.getX() > (boss.getX() +20) && player.getY() < (boss.getY() + 32)) {
            return (int) ((Math.atan(((boss.getY() + 32) - player.getY())/((boss.getX() + 20) - player.getX()))) * 180 / Math.PI);
        }
        else return 0;
    }

    public void addBullet(int dirX, int dirY) {
        handler.addObject(new BulletYellow((int) (boss.getX() + 20),
                (int) (boss.getY() + 32), ObjectID.BulletYellow, handler, dirX, dirY, ss));
    }

    public void addMinionRed(int dirX, int dirY) {
        handler.addObject(new BossMinionRed((int) (boss.getX() + 20),
                (int) (boss.getY() + 32), ObjectID.BossMinionRed, handler, dirX, dirY, ss));
    }

    public void addMinionBlue(int dirX, int dirY) {
        handler.addObject(new BossMinionBlue((int) (boss.getX() + 20),
                (int) (boss.getY() + 32), ObjectID.BossMinionBlue, handler, dirX, dirY, ss));
    }

    public void setBoss() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Boss)
                this.boss = handler.object.get(i);
        }
    }

    public void setPlayer() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Player)
                this.player = handler.object.get(i);
        }
    }

    public int dirX(int degree, int radius) {
        return (int) (boss.getX() + 32 + (radius * Math.cos(degree * Math.PI / 180)));
    }

    public int dirY(int degree, int radius) {
        return (int) (boss.getY() + 45 + (radius * Math.sin(degree * Math.PI / 180)));
    }
    //////////////////////////////////////////

    ///////////////////////////bullet patterns
    public void Circle() {
        setBoss();
        for (int i = 0; i <= 360; i += 10) {
            addBullet(dirX(i, radius), dirY(i, radius));
        }
    }

    public void Spiral() {
        setBoss();
        for (int i = 0; i < 6; i++) {
            addBullet(dirX(spiralDegree + i * 15, radius),
                    dirY(spiralDegree + i * 15, radius));
            addBullet(dirX(spiralDegree + 180 + i * 15, radius),
                    dirY(spiralDegree + 180 + i * 15, radius));
        }
        spiralDegree += 15;
        if (spiralDegree == 360) spiralDegree = 0;
    }

    public void reverseSpiral() {
        setBoss();
        for (int i = 0; i < 6; i++) {
            addBullet(dirX(spiralDegree + i * 15, radius),
                    dirY(spiralDegree + i * 15, radius));
            addBullet(dirX(spiralDegree + 180 + i * 15, radius),
                    dirY(spiralDegree + 180 + i * 15, radius));
        }
        reversespiralDegree -= 15;
        if (spiralDegree == -360) spiralDegree = 0;
    }

    public void Flower() {
        setBoss();
        for (int i = 0; i < 2; i++) {
            addBullet(dirX(spiralDegree + i * 90, radius),
                    dirY(spiralDegree + i * 90, radius));
            addBullet(dirX(spiralDegree + 180 + i * 90, radius),
                    dirY(spiralDegree + 180 + i * 90, radius));
            addBullet(dirX(reversespiralDegree + i * 90, radius),
                    dirY(reversespiralDegree + i * 90, radius));
            addBullet(dirX(reversespiralDegree + 180 + i * 90, radius),
                    dirY(reversespiralDegree + 180 + i * 90, radius));
        }
        reversespiralDegree -= 15;
        spiralDegree += 15;
        if (spiralDegree == 360) spiralDegree = 0;
        if (reversespiralDegree == -360) spiralDegree = 0;
    }

    public void Cross() {
        crossCount++;
        setBoss();
        if (crossState == 0) {
            for (int i = 0; i < 360; i += 90) {
                addBullet(dirX(i, radius), dirY(i, radius));
                if (crossCount % 4 == 0) crossState = 1;
            }
        } else {
            for (int i = 60; i < 360; i += 90) {
                addBullet(dirX(i, radius), dirY(i, radius));
            }
            if (crossCount % 4 == 0) crossState = 0;
        }
        if (crossCount == 4) crossCount = 0;
    }

    public void SpawnRed() {
        setBoss();
        for (int i = 270; i < 360; i += 30) {
            addMinionRed(dirX(i, radiusSmall), dirY(i, radiusSmall));
        }
    }

    public void SpawnBlue() {
        setBoss();
        for (int i = 180; i < 270; i += 30) {
            addMinionBlue(dirX(i, radiusSmall), dirY(i, radiusSmall));
        }
    }

    public void Trace() {
        setBoss();
        setPlayer();
        addBullet((int)player.getX(), (int)player.getY());
    }

    public void superTrace() {
        setBoss();
        setPlayer();
        addBullet(dirX(getAngle() + 20, getDistance()),
                dirY(getAngle() + 20, getDistance()));
        addBullet(dirX(getAngle() - 20, getDistance()),
                dirY(getAngle() - 20, getDistance()));
        addBullet((int)player.getX(), (int)player.getY());
    }

    public void Shotgun() {
        setBoss();
        setPlayer();
        addBullet(dirX(getAngle() + offset, getDistance() + offset),
                dirY(getAngle() + offset,getDistance() + offset));
        if (offset == 60) shotgunState = 1;
        if (offset == -60) shotgunState = 0;
        if (shotgunState == 0) offset+=15;
        if (shotgunState == 1) offset -=15;
    }

    public void Random() {
        setBoss();
        setPlayer();
        addBullet(dirX(r.nextInt(360), getDistance()),
                dirY(r.nextInt(360),getDistance()));
    }
    /////////////////////////////////////////////////////
}
