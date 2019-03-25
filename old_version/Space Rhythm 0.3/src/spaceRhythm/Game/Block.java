package spaceRhythm.Game;

import java.awt.*;

public class Block extends GameObject {
    public Block(int x, int y, ObjectID ID) {
        super(x, y, ID);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);

    }

}
