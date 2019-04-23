package spaceRhythm.Input;

import spaceRhythm.Game.Game;
import spaceRhythm.Game.GameObjects.GameObject;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.Handler;
import spaceRhythm.UI.GameState;
import spaceRhythm.UI.StateID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class KeyInput extends KeyAdapter {
    private Handler handler;
    public static boolean restart = false;
    private GameState gameState;
    private int evadeTime = 1;
    private boolean canEvade = true;
    private Game game;

    public KeyInput(Handler handler,GameState gameState,Game game) {
        this.handler = handler;
        this.gameState = gameState;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        if (gameState.getID() == StateID.GAME) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F5) {
                game.reload();
            }
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player) {
                    if (key == KeyEvent.VK_W) handler.setUp(true);
                    if (key == KeyEvent.VK_S) handler.setDown(true);
                    if (key == KeyEvent.VK_A) handler.setLeft(true);
                    if (key == KeyEvent.VK_D) handler.setRight(true);
                    if (key == KeyEvent.VK_P) {
                        gameState.setID(StateID.PAUSE);
                        handler.setUp(false);
                        handler.setRight(false);
                        handler.setLeft(false);
                        handler.setDown(false);
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        evadeDelay();
                        new Timer().schedule(new TimerTask() {
                                                 public void run() {
                                                     handler.setEvade(false);
                                                 }
                                             }, 150
                        );
                    }
                }
            }
        }
    }

    public void evadeDelay() {
        if (canEvade) {
            handler.setEvade(true);
            //canEvade =false;
            evadeTime--;
            if (evadeTime == 0) {
                canEvade = false;
                new Timer().schedule(new TimerTask() {
                                         public void run() {
                                             canEvade = true;
                                             evadeTime = 1;
                                         }
                                     }, 1000
                );

            }
        }

    }

    public void keyReleased(KeyEvent e) {
        if (gameState.getID() == StateID.GAME) {
            int key = e.getKeyCode();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player) {
                    if (key == KeyEvent.VK_W) handler.setUp(false);
                    if (key == KeyEvent.VK_S) handler.setDown(false);
                    if (key == KeyEvent.VK_A) handler.setLeft(false);
                    if (key == KeyEvent.VK_D) handler.setRight(false);
                    if (key == KeyEvent.VK_SPACE) {

                    }
                }
            }
        }
    }
}
