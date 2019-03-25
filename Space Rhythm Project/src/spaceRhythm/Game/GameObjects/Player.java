package spaceRhythm.Game.GameObjects;

import spaceRhythm.Game.Handler;

import java.awt.*;

public class Player extends GameObject {
    //boolean upStatus, downStatus, leftStatus, rightStatus;
    private Handler handler;

    public Player(int x, int y, ObjectID ID, Handler handler) {
        super(x, y, ID);
        this.handler = handler;
    }

    @Override
    public void tick() {


        //================== movement concept 1 =============================
//        if (handler.isUp()) {
//            if (!downStatus) {
//                velY = -5;
//                upStatus = true;
//            }
//        } else {
//            upStatus = false;
//            if (!handler.isDown()) {
//                velY = 0;
//                downStatus = false;
//            }
//        }
//
//        if (handler.isDown()) {
//            if (!upStatus) {
//                velY = 5;
//                downStatus = true;
//            }
//        } else {
//            downStatus = false;
//            if (!handler.isUp()) {
//                velY = 0;
//                upStatus = false;
//            }
//        }
//        if (handler.isLeft()) {
//            if (!rightStatus) {
//                velX = -5;
//                leftStatus = true;
//            }
//        } else {
//            leftStatus = false;
//            if (!handler.isRight()) {
//                velX = 0;
//                rightStatus = false;
//            }
//        }
//
//        if (handler.isRight()) {
//            if (!leftStatus) {
//                velX = 5;
//                rightStatus = true;
//            }
//
//        } else {
//            rightStatus = false;
//            if (!handler.isLeft()) {
//                velX = 0;
//                leftStatus = false;
//            }
//        }

        //================== movement concept 2 =============================

        if (handler.isUp()) velY = -5;
        if (handler.isDown()) velY = 5;
        if (handler.isUp() && handler.isDown()) velY = 0;
        if (!handler.isUp() && !handler.isDown()) velY = 0;

        if (handler.isLeft()) velX = -5;
        if (handler.isRight()) velX = 5;
        if (handler.isLeft() && handler.isRight()) velX = 0;
        if (!handler.isLeft() && !handler.isRight()) velX = 0;

        collision();
        x = x + velX;
        y = y + velY;

    }

    public boolean checkCollision(int x, int y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = x;
        myRect.y = y;
        if (myRect.intersects(otherRect)) {
            return true;
        }
        return false;
    }

    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ObjectID.Block) {
                if (checkCollision((x+ velX), y, getBounds(), tempObject.getBounds())) {
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
        g.setColor(Color.white);
        g.fillRect(x, y, 32, 44);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 44);
    }
}
