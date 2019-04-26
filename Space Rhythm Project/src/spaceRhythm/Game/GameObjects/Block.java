package spaceRhythm.Game.GameObjects;

import spaceRhythm.Animation.Animation;
import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private Animation sprite_anim;
    private BufferedImage sprite;

    public Block(int x, int y, ObjectID ID, SpriteSheet ss) {

        super(x, y, ID, ss);
        sprite = ss.grabImage(5, 5, 32, 32);
        sprite_anim = new Animation(7, sprite);

    }

    @Override
    public void tick() {
        sprite_anim.runAnimation();
    }

    @Override
    public void render(Graphics g) {

//        g.setColor(Color.MAGENTA);
//        g.fillRect((int)x, (int)y, 32, 32);
        sprite_anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);

    }

}
