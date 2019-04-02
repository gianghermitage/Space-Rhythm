package spaceRhythm.Game;

import spaceRhythm.Game.GameObjects.GameObject;

public class Camera {
    private float x;
    private float y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object) {

        //Static camera method, player always on center
//        x = object.getX() - 1280 / 2;
//        y = object.getY() - 720 /2 ;

        //Dynamic camera
        x += ((object.getX() - x) - 1280 / 2) * 0.05f;
        y += ((object.getY() - y) - 720 / 2) * 0.05f;

//        //center map (optional)
//        if (x <= 0) x = 0;
//        if (x >= 770) x = 770;
//        if (y <= 0) y = 0;
//        if (y >= 1350) y = 1350;

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
}
