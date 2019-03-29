package spaceRhythm.Game;

import spaceRhythm.Game.GameObjects.GameObject;

import java.awt.*;
import java.util.ArrayList;


public class Handler {
    public final ArrayList<GameObject> object = new ArrayList<>();
    public long startTime;
    private boolean up = false, down = false, left = false, right = false;
    private boolean evade = false;

    public boolean isEvade() {
        return evade;
    }

    public void setEvade(boolean evade) {
        this.evade = evade;
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject tempObject) {
        object.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {
        object.remove(tempObject);
    }

}
