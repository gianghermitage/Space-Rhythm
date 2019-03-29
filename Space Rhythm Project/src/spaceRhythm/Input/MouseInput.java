package spaceRhythm.Input;

import spaceRhythm.Game.Camera;
import spaceRhythm.Game.GameObjects.BulletBlue;
import spaceRhythm.Game.GameObjects.BulletRed;
import spaceRhythm.Game.GameObjects.GameObject;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.Handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;
    private int rmbCount = 0;

    public MouseInput(Handler handler, Camera camera) {
        this.handler = handler;
        this.camera = camera;
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());
        if (e.getButton() == MouseEvent.BUTTON3) rmbCount++;
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player) {
                    if (rmbCount % 2 == 0)
                        handler.addObject(new BulletBlue(tempObject.getX() + 16, tempObject.getY() + 24, ObjectID.Bullet, handler, mx, my));
                    else if (rmbCount % 2 != 0)
                        handler.addObject(new BulletRed(tempObject.getX() + 16, tempObject.getY() + 24, ObjectID.Bullet, handler, mx, my));
                }
            }
        }
    }
}
