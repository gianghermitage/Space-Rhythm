package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Game;
import spaceRhythm.Game.Handler;
import spaceRhythm.Input.MouseInput;
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
    int immuneTime = 1000;
    private Animation run_anim_blue;
    private Animation evade_anim_blue;
    private Animation hit_anim_blue;
    private Animation run_anim_red;
    private Animation evade_anim_red;
    private Animation hit_anim_red;
    private Handler handler;
    private Game game;
    private GameState gameState;
    private BufferedImage[] run_sprite_red = new BufferedImage[4];
    private BufferedImage[] run_sprite_blue = new BufferedImage[4];

    private BufferedImage[] evade_sprite_red = new BufferedImage[4];
    private BufferedImage[] evade_sprite_blue = new BufferedImage[4];

    private BufferedImage[] hit_sprite_red = new BufferedImage[4];
    private BufferedImage[] hit_sprite_blue = new BufferedImage[4];


    private BufferedImage[] dead_sprite = new BufferedImage[1];

    public Player(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss, Game game, GameState gameState) {
        super(x, y, ID, ss);
        this.handler = handler;
        this.game = game;
        this.gameState = gameState;
        run_sprite_red[0] = ss.grabImage(1, 3, 60, 64);
        run_sprite_red[1] = ss.grabImage(2, 3, 60, 64);
        run_sprite_red[2] = ss.grabImage(3, 3, 60, 64);
        run_sprite_red[3] = ss.grabImage(4, 3, 60, 64);
        run_anim_red = new Animation(7, run_sprite_red);

        evade_sprite_red[0] = ss.grabImage(5, 3, 60, 64);
        evade_sprite_red[1] = ss.grabImage(6, 3, 60, 64);
        evade_sprite_red[2] = ss.grabImage(7, 3, 60, 64);
        evade_sprite_red[3] = ss.grabImage(8, 3, 60, 64);
        evade_anim_red = new Animation(7, evade_sprite_red);

        hit_sprite_red[0] = ss.grabImage(1, 3, 60, 64);
        hit_sprite_red[1] = ss.grabImage(6, 3, 60, 64);
        hit_sprite_red[2] = ss.grabImage(3, 3, 60, 64);
        hit_sprite_red[3] = ss.grabImage(8, 3, 60, 64);
        hit_anim_red = new Animation(7, hit_sprite_red);

        run_sprite_blue[0] = ss.grabImage(1, 4, 60, 64);
        run_sprite_blue[1] = ss.grabImage(2, 4, 60, 64);
        run_sprite_blue[2] = ss.grabImage(3, 4, 60, 64);
        run_sprite_blue[3] = ss.grabImage(4, 4, 60, 64);
        run_anim_blue = new Animation(7, run_sprite_blue);

        evade_sprite_blue[0] = ss.grabImage(5, 4, 60, 64);
        evade_sprite_blue[1] = ss.grabImage(6, 4, 60, 64);
        evade_sprite_blue[2] = ss.grabImage(7, 4, 60, 64);
        evade_sprite_blue[3] = ss.grabImage(8, 4, 60, 64);
        evade_anim_blue = new Animation(7, evade_sprite_blue);

        hit_sprite_blue[0] = ss.grabImage(1, 4, 60, 64);
        hit_sprite_blue[1] = ss.grabImage(6, 4, 60, 64);
        hit_sprite_blue[2] = ss.grabImage(3, 4, 60, 64);
        hit_sprite_blue[3] = ss.grabImage(8, 4, 60, 64);
        hit_anim_blue = new Animation(5, hit_sprite_blue);

        dead_sprite[0] = ss.grabImage(9,3,16,16);

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
        run_anim_red.runAnimation();
        evade_anim_red.runAnimation();
        hit_anim_red.runAnimation();

        run_anim_blue.runAnimation();
        evade_anim_blue.runAnimation();
        hit_anim_blue.runAnimation();

        collision();
        if (game.hp <= 0) {
            Game.gameOver = true;
            new Timer().schedule(new TimerTask() {
                                     public void run() {
                                         gameState.setID(StateID.GAMEOVER);
                                     }
                                 }, 3000
            );

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
                if (checkCollision((x + velX), y, getBounds(), tempObject.getBounds()) ) {

                    if (x - pushBack * velX > 519 && x - pushBack * velX < 1499) x += -pushBack * velX;
                    else if (x - pushBack * velX < 519) {
                        float distance = 519 - x;
                        x += distance;
                    } else if (x - pushBack * velX > 1499) {
                        float distance = 1499 - x;
                        x += distance;
                    }
                    if (isHit) {
                        isHit = false;
                        game.hp = game.hp - 10;
                        new Timer().schedule(new TimerTask() {
                                                 public void run() {
                                                     isHit = true;
                                                 }
                                             }, immuneTime
                        );
                    }
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
                        isHit = false;
                        game.hp = game.hp - 10;
                        new Timer().schedule(new TimerTask() {
                                                 public void run() {
                                                     isHit = true;
                                                 }
                                             }, immuneTime
                        );
                    }
                }
            }
            if (tempObject.getID() == ObjectID.BossMinionBlue) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (!handler.isEvade()) {
                        if (isHit) {
                            game.hp = game.hp - 10;
                            isHit = false;
                            new Timer().schedule(new TimerTask() {
                                                     public void run() {
                                                         isHit = true;
                                                     }
                                                 }, immuneTime
                            );
                        }
                    }
                }
            }

            if (tempObject.getID() == ObjectID.BossMinionRed) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (!handler.isEvade()) {
                        if (isHit) {
                            game.hp = game.hp - 10;
                            isHit = false;
                            new Timer().schedule(new TimerTask() {
                                                     public void run() {
                                                         isHit = true;
                                                     }
                                                 }, immuneTime
                            );
                        }
                    }
                }
            }
            if (tempObject.getID() == ObjectID.BulletYellow) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    if (!handler.isEvade()) {
                        if (isHit) {
                            game.hp = game.hp - 2;
                            isHit = false;
                            new Timer().schedule(new TimerTask() {
                                                     public void run() {
                                                         isHit = true;
                                                     }
                                                 }, immuneTime
                            );
                        }
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
        if(MouseInput.rmb) {
            if(Game.gameOver){
                g.drawImage(dead_sprite[0], (int) x, (int) y, null);
            }
            else if (handler.isEvade()) {
                evade_anim_blue.drawAnimation(g, x, y, 0);
            } else {
                if(!isHit) hit_anim_blue.drawAnimation(g,x,y,0);
                else if (velX == 0 && velY == 0) g.drawImage(run_sprite_blue[1], (int) x, (int) y, null);
                else run_anim_blue.drawAnimation(g, x, y, 0);
            }
        }
        if(!MouseInput.rmb) {
            if (handler.isEvade()) {
                evade_anim_red.drawAnimation(g, x, y, 0);
            } else {
                if(!isHit) hit_anim_red.drawAnimation(g,x,y,0);
                else if (velX == 0 && velY == 0) g.drawImage(run_sprite_red[1], (int) x, (int) y, null);
                else run_anim_red.drawAnimation(g, x, y, 0);
            }
        }


    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 38, 54);
    }
}
