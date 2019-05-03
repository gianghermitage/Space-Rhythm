package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;
import spaceRhythm.UI.GameState;
import spaceRhythm.UI.StateID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Boss extends GameObject {
    int hp = 1000;
    int phase = 1;
    int timer = 1800;
    double bossSpeed = 1.5;

    private Animation init_anim;
    private Animation normal_anim;
    private Animation aggro_anim;
    private Handler handler;
    private BufferedImage[] init_sprite = new BufferedImage[3];
    private BufferedImage[] normal_sprite = new BufferedImage[3];
    private BufferedImage[] aggro_sprite = new BufferedImage[3];
    private BufferedImage[] dead_sprite = new BufferedImage[1];
    private BulletPattern bulletPattern;

    private GameObject player;
    private GameState gameState;

    public Boss(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss,GameState gameState) {
        super(x, y, ID, ss);
        this.handler = handler;
        this.gameState = gameState;

        init_sprite[0] = ss.grabImage(7, 1, 64, 95);
        init_sprite[1] = ss.grabImage(8, 1, 64, 95);
        init_sprite[2] = ss.grabImage(9, 1, 64, 95);

        init_anim = new Animation(10, init_sprite);

        normal_sprite[0] = ss.grabImage(1, 1, 64, 95);
        normal_sprite[1] = ss.grabImage(2, 1, 64, 95);
        normal_sprite[2] = ss.grabImage(3, 1, 64, 95);

        normal_anim = new Animation(10, normal_sprite);

        aggro_sprite[0] = ss.grabImage(4, 1, 64, 95);
        aggro_sprite[1] = ss.grabImage(5, 1, 64, 95);
        aggro_sprite[2] = ss.grabImage(6, 1, 64, 95);

        aggro_anim = new Animation(10, aggro_sprite);

        dead_sprite[0] = ss.grabImage(9,3,16,16);
        this.bulletPattern = new BulletPattern(handler, ss);
    }


    @Override
    public void tick() {
        //find player's position
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Player)
                player = handler.object.get(i);
        }

        //phase 1:
        if (hp > 700){
            bossSpeed = 1.5;
            if (timer > 1200) {
                //follow
                x += velX;
                y += velY;
                float distX = x - player.getX() - 16;
                float distY = y - player.getY() - 22;
                float distance = (float) Math.sqrt(Math.pow(x - player.getX(), 2) +
                        Math.pow(y - player.getY(), 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);

                //Trace
                if (timer % 5 == 0) {
                    bulletPattern.Trace();
                }
            }
            if (timer > 600 && timer <= 1200) {
                //follow
                x += velX;
                y += velY;
                float distX = x - player.getX() - 16;
                float distY = y - player.getY() - 22;
                float distance = (float) Math.sqrt(Math.pow(x - player.getX(), 2) +
                        Math.pow(y - player.getY(), 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                //Shotgun
                if (timer % 5 == 0) {
                    bulletPattern.Shotgun();
                }
            }

            if (timer < 600 && timer >= 0) {
                //move to center
                bossSpeed = 2;
                x += velX;
                y += velY;
                float distX = x - 1024;
                float distY = y - 1020;
                float distance = (float) Math.sqrt(Math.pow(x - player.getX(), 2) +
                        Math.pow(y - player.getY(), 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                //Random
                if (timer % 2 == 0) {
                    bulletPattern.Random();
                }
            }
        }

        //phase 2:
        //phase 3:

        //reset timer
        timer--;
        if (timer <= 0) {
            timer = 1800;
        }

        //check collision
        collision();
        init_anim.runAnimation();
        normal_anim.runAnimation();
        aggro_anim.runAnimation();

        //dead
        if (hp <= 0) {

            new Timer().schedule(new TimerTask() {
                                     public void run() {
                                         gameState.setID(StateID.VICTORY);
                                     }
                                 }, 3000
            );
            handler.removeObject(this);

        }
    }

    public boolean checkCollision(float x, float y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = (int) x;
        myRect.y = (int) y;
        return myRect.intersects(otherRect);
    }

    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ObjectID.Block) {
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds())) {
                    x += -velX;
                }
                if (checkCollision(x, (y + velY), getBounds(), tempObject.getBounds())) {
                    y += -velY;

                }
            }
            if (tempObject.getID() == ObjectID.Player) {
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds())) {
                    velX = 0;
                }
                if (checkCollision(x, (y + velY), getBounds(), tempObject.getBounds())) {
                    velY = 0;
                }
            }
            if (tempObject.getID() == ObjectID.BulletRed || tempObject.getID() == ObjectID.BulletBlue) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 1;
                    handler.removeObject(tempObject);
                }
            }
            if (tempObject.getID() == ObjectID.Pickup) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    int recHP = 10;
                    if (hp + recHP <= 100) hp = hp + recHP;
                    else if (hp + recHP > 100) {
                        hp = 100;
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.yellow);
//        g.fillRect((int)x, (int)y, 40,64 );
        if(hp > 700) init_anim.drawAnimation(g,x,y,0);
        else if (hp > 400) normal_anim.drawAnimation(g, x, y, 0);
        else aggro_anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 64, 91);
    }
}
