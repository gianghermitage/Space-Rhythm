package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Game;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;
import spaceRhythm.UI.GameState;
import spaceRhythm.UI.StateID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends GameObject {
    public static boolean isHit = true;
    int pushBack = 5;
    private Animation run_anim;
    private Animation evade_anim;
    private Handler handler;
    private Game game;
    private GameState gameState;
    private BufferedImage[] run_sprite = new BufferedImage[4];
    private BufferedImage[] evade_sprite = new BufferedImage[4];

    public Player(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss, Game game, GameState gameState) {
        super(x, y, ID, ss);
        this.handler = handler;
        this.game = game;
        this.gameState = gameState;
        run_sprite[0] = ss.grabImage(1, 3, 60, 64);
        run_sprite[1] = ss.grabImage(2, 3, 60, 64);
        run_sprite[2] = ss.grabImage(3, 3, 60, 64);
        run_sprite[3] = ss.grabImage(4, 3, 60, 64);
        run_anim = new Animation(7, run_sprite);

        evade_sprite[0] = ss.grabImage(5, 3, 60, 64);
        evade_sprite[1] = ss.grabImage(6, 3, 60, 64);
        evade_sprite[2] = ss.grabImage(7, 3, 60, 64);
        evade_sprite[3] = ss.grabImage(8, 3, 60, 64);
        evade_anim = new Animation(7, evade_sprite);

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


        x = x + velX;
        y = y + velY;
        run_anim.runAnimation();
        evade_anim.runAnimation();
        collision();
        if (game.hp <= 0) {
            handler.removeObject(this);
            gameState.setID(StateID.GAMEOVER);
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
            if (tempObject.getID() == ObjectID.Boss) {
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds())) {

                    if (x - pushBack * velX > 519 && x - pushBack * velX < 1499) x += -pushBack * velX;
                    else if (x - pushBack * velX < 519) {
                        float distance = 519 - x;
                        x += distance;
                    } else if (x - pushBack * velX > 1499) {
                        float distance = 1499 - x;
                        x += distance;
                    }
                    if (isHit) {
                        //game.hp = game.hp - 10;
                        isHit = false;
                    }
                    new Timer().schedule(new TimerTask() {
                                             public void run() {
                                                 isHit = true;
                                             }
                                         }, 1000
                    );
                }
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds())) {
                    if (y - pushBack * velY > 521 && y - pushBack * velY < 1486) y += -pushBack * velY;
                    else if (y - pushBack * velY < 521) {
                        float distance = 521 - y;
                        y += distance;
                    } else if (y - pushBack * velY > 1486) {
                        float distance = 1486 - y;
                        y += distance;
                    }
                    if (isHit) {
                        //game.hp = game.hp - 10;
                        isHit = false;
                        new Timer().schedule(new TimerTask() {
                                                 public void run() {
                                                     isHit = true;
                                                 }
                                             }, 1000
                        );
                    }

                }
            }
            if (tempObject.getID() == ObjectID.BulletYellow) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (!handler.isEvade()) {
                        game.hp--;
                    }
                }
            }
            if (tempObject.getID() == ObjectID.Block) {
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds())) {
                    x += -velX;
                }

                if (checkCollision(x, (y + velY), getBounds(), tempObject.getBounds())) {
                    y += -velY;

                }
            }
            if (tempObject.getID() == ObjectID.Pickup) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    int recHP = 10;
                    if (Game.hp + recHP <= 100) Game.hp = Game.hp + recHP;
                    else if (Game.hp + recHP > 100) {
                        Game.hp = 100;
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.WHITE);
//        g.fillRect((int)x, (int)y, 38, 54);
        if (handler.isEvade()) {
            evade_anim.drawAnimation(g, x, y, 0);
        } else {

            if (velX == 0 && velY == 0) g.drawImage(run_sprite[1], (int) x, (int) y, null);
            else run_anim.drawAnimation(g, x, y, 0);
        }


    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 38, 54);
    }
}
