package spaceRhythm.Game.GameObjects;

import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;


public class BulletBlue extends GameObject {
    private Handler handler;

    public BulletBlue(int x, int y, ObjectID ID, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, ID, ss);
        this.handler = handler;
        calculateVelocity(x, y, mx, my);

    }


    public void calculateVelocity(int fromX, int fromY, int toX, int toY) {
        double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        double speed = 10;
        velY = (int) ((toY - fromY) * speed / distance);
        velX = (int) ((toX - fromX) * speed / distance);

    }


    @Override
    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ObjectID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(this);
                    //System.out.println("Hit");
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x, y, 8, 8);
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }


}
