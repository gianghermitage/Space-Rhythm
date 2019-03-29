package spaceRhythm.Game.GameObjects;

import spaceRhythm.Game.Handler;

import java.awt.*;

public class Player extends GameObject {
    private Handler handler;

    public Player(int x, int y, ObjectID ID, Handler handler) {
        super(x, y, ID);
        this.handler = handler;
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
//        System.out.println("VelX: " + velX);
        //System.out.println("VelY: " + velY);
        x = x + velX;
        y = y + velY;

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
            Color myColour = new Color(255, 255, 255, 20); //transparent
            g.setColor(myColour);
        } else g.setColor(Color.white);

        g.fillRect(x, y, 32, 44);


    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 44);
    }
}
