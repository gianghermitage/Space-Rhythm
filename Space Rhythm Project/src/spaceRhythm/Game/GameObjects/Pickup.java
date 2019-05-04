package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pickup extends GameObject {
    private Handler handler;
    private Animation sprite_anim;
    private BufferedImage[] sprite = new BufferedImage[4];

    public Pickup(int x, int y, ObjectID ID, Handler handler, SpriteSheet ss) {

        super(x, y, ID, ss);
        this.handler = handler;
        sprite[0] = ss.grabImage(4, 5, 32, 48);
        sprite[1] = ss.grabImage(5, 5, 32, 48);
        sprite[2] = ss.grabImage(6, 5, 32, 48);
        sprite[3] = ss.grabImage(7, 5, 32, 48);
        sprite_anim = new Animation(7, sprite);

    }

    @Override
    public void tick() {

        sprite_anim.runAnimation();
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.green);
////        g.fillRect((int)x,(int)y,16,16);

        sprite_anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);

    }

}
