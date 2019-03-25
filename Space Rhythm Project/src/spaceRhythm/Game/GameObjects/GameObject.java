package spaceRhythm.Game.GameObjects;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected ObjectID ID;
    protected int velX = 0;
    protected int velY = 0;

    public GameObject(int x, int y, ObjectID ID) {
        this.ID = ID;
        this.x = x;
        this.y = y;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public ObjectID getID() {
        return ID;
    }

    public void setID(ObjectID ID) {
        this.ID = ID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

}
