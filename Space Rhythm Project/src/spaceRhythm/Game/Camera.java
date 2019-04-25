package spaceRhythm.Game;

import spaceRhythm.Game.GameObjects.GameObject;

public class Camera {
    private float x;
    private float y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Camera(){}

    public void tick(GameObject object) {

        //Static camera method, player always on center
//        x = object.getX() - 1280 / 2;
//        y = object.getY() - 720 /2 ;

        //Dynamic camera
        x += ((object.getX() - x) - 1280 / 2) * 0.05f;
        y += ((object.getY() - y) - 720 / 2) * 0.05f;

        //center map (optional)
//        if (x <= 385) x = 385;
//        if (x >= 400) x = 400;
//        if (y <= 400) y = 400;
//        if (y >= 1200) y = 1200;

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
