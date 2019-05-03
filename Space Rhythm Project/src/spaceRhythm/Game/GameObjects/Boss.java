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
    int timer = 100;

    int timer2 = 5;
    private Animation init_anim;
    private Animation normal_anim;
    private Animation aggro_anim;
    private Handler handler;
    private BufferedImage[] init_sprite = new BufferedImage[3];
    private BufferedImage[] normal_sprite = new BufferedImage[3];
    private BufferedImage[] aggro_sprite = new BufferedImage[3];
    private BufferedImage[] dead_sprite = new BufferedImage[1];

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

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Player)
                player = handler.object.get(i);
        }

        BulletPattern bulletPattern = new BulletPattern(handler, ss);

        //boss follow
        float distX = x - player.getX() - 16;
        float distY = y - player.getY() - 22;
        float distance = (float) Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y - player.getY(), 2));

        velX = (float) ((-1.5 / distance) * distX);
        velY = (float) ((-1.5 / distance) * distY);

        timer--;
//        timer2--;

        if (timer <= 0) {
            bulletPattern.Shotgun();
//            bulletPattern.SpawnRed();
//            bulletPattern.SpawnBlue();
            //bulletPattern.Spiral();
            timer = 5;
        }

//        if (timer2 <= 0) {
//            bulletPattern.Spiral();
//            timer2 = 5;
//        }

        collision();
        init_anim.runAnimation();
        normal_anim.runAnimation();
        aggro_anim.runAnimation();

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
                    //Game.hp--;
                }
                if (checkCollision(x, (y + velY), getBounds(), tempObject.getBounds())) {
                    velY = 0;
                    //Game.hp--;

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
        if(hp > 750) init_anim.drawAnimation(g,x,y,0);
        else if (hp > 500) normal_anim.drawAnimation(g, x, y, 0);
        else aggro_anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 64, 91);
    }
}
