package spaceRhythm.Game.GameObjects;

import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;

public class Block extends GameObject {
    public Block(int x, int y, ObjectID ID, SpriteSheet ss) {
        super(x, y, ID, ss);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect((int)x, (int)y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);

    }

}
