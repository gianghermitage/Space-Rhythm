package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private Animation idle_anim;
    private Handler handler;
    private BufferedImage[] idle_image = new BufferedImage[4];

    public Player(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss) {
        super(x, y, ID, ss);
        this.handler = handler;
        idle_image[0] = ss.grabImage(1, 1, 32, 44);
        idle_image[1] = ss.grabImage(2, 1, 32, 44);
        idle_image[2] = ss.grabImage(3, 1, 32, 44);
        idle_image[3] = ss.grabImage(4, 1, 32, 44);
        idle_anim = new Animation(7, idle_image);
    }

    @Override
    public void tick() {

        if (handler.isUp()) {
            velY = -5;
            if (handler.isEvade()) {
                velY = -10;
            }
        }
        if (handler.isDown()) {
            velY = 5;
            if (handler.isEvade()) {
                velY = 10;
            }
        }
        if (handler.isUp() && handler.isDown()) velY = 0;
        if (!handler.isUp() && !handler.isDown()) velY = 0;

        if (handler.isLeft()) {
            velX = -5;
            if (handler.isEvade()) {
                velX = -10;
            }
        }
        if (handler.isRight()) {
            velX = 5;
            if (handler.isEvade()) {
                velX = 10;
            }
        }
        if (handler.isLeft() && handler.isRight()) velX = 0;
        if (!handler.isLeft() && !handler.isRight()) velX = 0;

        collision();
//       System.out.println("VelX: " + velX);
//       System.out.println("VelY: " + velY);
        x = x + velX;
        y = y + velY;
        idle_anim.runAnimation();


    }

    public boolean checkCollision(int x, int y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = x;
        myRect.y = y;
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
        }

    }

    @Override
    public void render(Graphics g) {
        if (handler.isEvade()) {
//            g.setColor(Color.black);
//            g.fillRect(x, y, 32, 44);
            g.drawImage(idle_image[0],x,y,null);
        } else {
//            g.setColor(Color.white);
//            g.fillRect(x, y, 32, 44);
            if(velX == 0 && velY == 0) g.drawImage(idle_image[0],x,y,null);
            else idle_anim.drawAnimation(g,x,y,0);
        }

        //g.fillRect(x, y, 32, 44);


    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 44);
    }
}
