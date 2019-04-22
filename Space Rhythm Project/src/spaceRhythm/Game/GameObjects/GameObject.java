package spaceRhythm.Game.GameObjects;

import spaceRhythm.SpriteSheet.SpriteSheet;

import java.awt.*;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected ObjectID ID;
    protected float velX = 0;
    protected float velY = 0;
    protected SpriteSheet ss;

    public GameObject(float x, float y, ObjectID ID, SpriteSheet ss) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.ss = ss;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

}
