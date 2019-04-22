package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boss extends GameObject {

    private Handler handler;
    private BufferedImage[] idle_image = new BufferedImage[4];
    int hp = 100;
    int timer = 5;
    int timer2 = 5;
    private GameObject player;

    public Boss (int x, int y, ObjectID ID, Handler handler, SpriteSheet ss) {
        super(x, y, ID, ss);
        this.handler = handler;
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

//        float distX = x - player.getX() - 16;
//        float distY = y - player.getY() - 22;
//        float distance = (float) Math.sqrt( Math.pow(x - player.getX(),2) +Math.pow(y - player.getY(),2));
//
//        velX = (float) ((-1.0/distance) * distX);
//        velY = (float) ((-1.0/distance) * distY);

        timer--;
//        timer2--;

        if (timer <= 0) {
            bulletPattern.Spiral();
            timer = 5;
        }

//        if (timer2 <= 0) {
//            bulletPattern.Spiral();
//            timer2 = 5;
//        }

        collision();

        if (hp <= 0) handler.removeObject(this);
    }

    public boolean checkCollision(float x, float y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = (int)x;
        myRect.y = (int)y;
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
            if (tempObject.getID() == ObjectID.BulletRed || tempObject.getID() == ObjectID.BulletBlue) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp-=50;
                    handler.removeObject(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, 40,64 );
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 40, 64);
    }
}
