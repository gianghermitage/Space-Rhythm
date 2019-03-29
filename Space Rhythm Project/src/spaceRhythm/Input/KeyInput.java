package spaceRhythm.Input;

import spaceRhythm.Game.GameObjects.GameObject;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {
    private Handler handler;
    private int evadeTime = 3;
    private boolean canEvade = true;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ObjectID.Player) {
                if (key == KeyEvent.VK_W) handler.setUp(true);
                if (key == KeyEvent.VK_S) handler.setDown(true);
                if (key == KeyEvent.VK_A) handler.setLeft(true);
                if (key == KeyEvent.VK_D) handler.setRight(true);
                if (key == KeyEvent.VK_SPACE) {
                    //handler.setEvade(true);
                }
            }
        }
    }

    public void evadeDelay() {
        if (canEvade) {
            handler.setEvade(true);
            //canEvade =false;
            evadeTime--;
            new java.util.Timer().schedule(new java.util.TimerTask() {
                public void run() {
                    if(evadeTime < 3 && evadeTime > 0){
                        evadeTime++;
                    }
                }
                }, 3000         //if 3s non blink then recover 1 blink
            );
            if(evadeTime <= 0) {
                canEvade = false;
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    public void run() {
                        canEvade = true;
                        evadeTime = 3;
                    }
                    }, 10000        //if 3 blink then cool down 10 sec
                );

            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ObjectID.Player) {
                if (key == KeyEvent.VK_W) handler.setUp(false);
                if (key == KeyEvent.VK_S) handler.setDown(false);
                if (key == KeyEvent.VK_A) handler.setLeft(false);
                if (key == KeyEvent.VK_D) handler.setRight(false);
                if (key == KeyEvent.VK_SPACE) {
                    evadeDelay();
//                    /handler.setEvade(true);
                    System.out.println(evadeTime);
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        public void run() {
                            handler.setEvade(false);
                        }}, 150
                    );
                }
            }
        }
    }
}
