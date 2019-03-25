package spaceRhythm.Input;

import spaceRhythm.Game.Camera;
import spaceRhythm.Game.GameObjects.Bullet;
import spaceRhythm.Game.GameObjects.GameObject;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.Handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;


    public MouseInput(Handler handler, Camera camera){
        this.handler = handler;
        this.camera = camera;
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getID() == ObjectID.Player){
                handler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ObjectID.Bullet, handler, mx, my));
            }
        }
    }
}
