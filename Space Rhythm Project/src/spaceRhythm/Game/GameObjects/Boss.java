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
    public static int blinkstate = 0;
    int hp = 1000;
    int timer = 1800;
    int delay = 75;
    double bossSpeed = 1.5;
    private float distX;
    private float distY;
    private float distance;
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

    public Boss(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss, GameState gameState) {
        super(x, y, ID, ss);
        distX = 0;
        distY = 0;
        velX = 0;
        velY = 0;
        distance = 0;
        this.handler = handler;
        this.gameState = gameState;
        bulletPattern = new BulletPattern(handler, ss);
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

        dead_sprite[0] = ss.grabImage(9, 3, 16, 16);
        this.bulletPattern = new BulletPattern(handler, ss);


    }

    public int dirX(int degree, int radius) {
        return (int) (1024 + (radius * Math.cos(degree * Math.PI / 180)));
    }

    public int dirY(int degree, int radius) {
        return (int) (1020 + (radius * Math.sin(degree * Math.PI / 180)));
    }


    public void bossBehavior(float plX, float plY) {
        distX = 0;
        distY = 0;
        distance = 0;
        //phase 1: Trace, Shotgun, Random, hp > 750
        if (hp > 750) {
            bossSpeed = 1.5;
            if (timer > 1200) {
                //follow
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Trace
                if (timer % 5 == 0) {
                    bulletPattern.Trace();
                }
            }
            if (timer > 600 && timer <= 1200) {
                //follow
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Shotgun
                if (timer % 5 == 0) {
                    bulletPattern.Shotgun();
                }
                if (timer % 10 == 0) {
                    bulletPattern.Trace();
                }
            }

            if (timer < 600 && timer >= 0) {
                //move to center
                bossSpeed = 2;
                distX = x - 1024;
                distY = y - 1020;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Random
                if (timer % 2 == 0) {
                    bulletPattern.Random();
                }
            }
        }

        //phase 2: Super Trace, Spawn, Cross hp > 500
        else if (hp > 500) {
            bossSpeed = 2;
            if (timer > 1200) {
                //follow
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Super Trace
                if (timer % 5 == 0) {
                    bulletPattern.superTrace();
                }
            }
            if (timer > 600 && timer <= 1200) {
                //follow
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Cross
                if (timer % 5 == 0) {
                    bulletPattern.Cross();
                }
                if (timer % 4 == 0) {
                    bulletPattern.Shotgun();
                }
            }
            if (timer < 600 && timer >= 0) {
                //move to center
                distX = x - 1024;
                distY = y - 1020;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Cross Spawn
                if (timer % 10 == 0) {
                    bulletPattern.Cross();
                }
                if (timer % 50 == 0) {
                    bulletPattern.Circle();
                }
                if (timer % 150 == 0) {
                    bulletPattern.SpawnBlue();
                    bulletPattern.SpawnRed();
                }
            }
        }

        //phase 3: hp > 250
        else if (hp > 250) {
            bossSpeed = 3;
            if (timer > 1200) {
                x += velX;
                y += velY;
                distX = x - 1024;
                distY = y - 1020;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);

                //Spiral & reverse Spiral
                if (timer > 1500 && timer % 5 == 0) {
                    bulletPattern.Shotgun();
                    bulletPattern.Spiral();
                }
                if (timer <= 1500 && timer % 5 == 0) {
                    bulletPattern.Shotgun();
                    bulletPattern.reverseSpiral();
                }
            }
            if (timer > 600 && timer <= 1200) {
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;
                //Flower & circle
                if (timer % 5 == 0) {
                    bulletPattern.Flower();
                }
                if (timer % 20 == 0) {
                    bulletPattern.Circle();
                }
            }
            if (timer < 600 && timer >= 0) {
                if (timer > 300) {
                    if (timer % 20 == 0) {
                        if (blinkstate == 0) {
                            x = dirX(45, 400);
                            y = dirY(45, 400);
                        }
                        if (blinkstate == 1) {
                            x = dirX(135, 400);
                            y = dirY(135, 400);
                        }
                        if (blinkstate == 2) {
                            x = dirX(225, 400);
                            y = dirY(225, 400);
                        }
                        if (blinkstate == 3) {
                            x = dirX(315, 400);
                            y = dirY(315, 400);
                        }
                        blinkstate++;
                        if (blinkstate > 3) blinkstate = 0;
                        bulletPattern.Circle();
                    }
                }
                if (timer <= 300) {
                    //follow
                    distX = x - plX - 16;
                    distY = y - plY - 22;
                    distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                            Math.pow(y - plY, 2));
                    velX = (float) ((-bossSpeed / distance) * distX);
                    velY = (float) ((-bossSpeed / distance) * distY);
                    x += velX;
                    y += velY;
                    //shotgun circle
                    if (timer % 2 == 0) {
                        bulletPattern.Shotgun();
                    }
                    if (timer % 50 == 0) {
                        bulletPattern.Circle();
                    }
                }
            }
        }

        //phase 4: hp > 0 && hp < 250
        else if (hp > 0 && hp < 250) {
            bossSpeed = 4;
            if (timer > 1200) {
                //follow
                distX = x - plX - 16;
                distY = y - plY - 22;
                distance = (float) Math.sqrt(Math.pow(x - plX, 2) +
                        Math.pow(y - plY, 2));
                velX = (float) ((-bossSpeed / distance) * distX);
                velY = (float) ((-bossSpeed / distance) * distY);
                x += velX;
                y += velY;

                //superTrace + minion
                if (timer % 5 == 0) {
                    bulletPattern.superTrace();
                }
                if (timer % 150 == 0) {
                    bulletPattern.SpawnBlue();
                    bulletPattern.SpawnRed();
                }
            }
            if (timer > 600 && timer <= 1200) {
                //circle & shotgun
                bulletPattern.Shotgun();
                if (timer % 20 == 0) {
                    bulletPattern.Circle();
                }
            }
            if (timer < 600 && timer >= 0) {
                if (timer % 120 == 0) {
                    bulletPattern.SpawnRed();
                    bulletPattern.SpawnBlue();
                }
                if (timer % 20 == 0) {
                    if (blinkstate == 0) {
                        x = dirX(45, 400);
                        y = dirY(45, 400);
                    }
                    if (blinkstate == 1) {
                        x = dirX(135, 400);
                        y = dirY(135, 400);
                    }
                    if (blinkstate == 2) {
                        x = dirX(225, 400);
                        y = dirY(225, 400);
                    }
                    if (blinkstate == 3) {
                        x = dirX(315, 400);
                        y = dirY(315, 400);
                    }
                    blinkstate++;
                    if (blinkstate > 3) blinkstate = 0;
                    bulletPattern.Circle();
                }
            }
        }

        //reset timer
        timer--;
        if (timer <= 0) {
            timer = 1800;
        }
    }

    @Override
    public void tick() {
        //find player's position
        float plX;
        float plY;
        int temp = handler.object.size();
        for (int i = 0; i < temp; i++) {
            if (handler.object.get(i).getID() == ObjectID.Player)
                player = handler.object.get(i);
        }
        plX = player.getX();
        plY = player.getY();


        //loading
        if (delay > 0) {
            delay--;
        }
        if (delay == 0) {
            bossBehavior(plX, plY);
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
                    x += -1 * velX;
                }
                if (checkCollision(x, (y + velY), getBounds(), tempObject.getBounds())) {
                    y += -1 * velY;
                }
            }
            if (tempObject.getID() == ObjectID.BulletRed || tempObject.getID() == ObjectID.BulletBlue) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 4;
                    handler.removeObject(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.yellow);
//        g.fillRect((int)x, (int)y, 40,64 );
        if (hp > 750) init_anim.drawAnimation(g, x, y, 0);
        else if (hp > 500) normal_anim.drawAnimation(g, x, y, 0);
        else aggro_anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 64, 91);
    }
}
