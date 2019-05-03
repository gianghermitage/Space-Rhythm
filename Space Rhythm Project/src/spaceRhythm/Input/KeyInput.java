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
    public static boolean restart = false;
    private Handler handler;
    private GameState gameState;
    private int evadeTime = 1;
    private boolean canEvade = true;
    private Game game;

    public KeyInput(Handler handler, GameState gameState, Game game) {
        this.handler = handler;
        this.gameState = gameState;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        if (gameState.getID() == StateID.GAME) {
            int key = e.getKeyCode();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player && !Game.gameOver) {
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
                    if (key == KeyEvent.VK_F1) {
                        Game.hp = 1000;         //cheat code
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (gameState.getID() == StateID.GAME) {
            int key = e.getKeyCode();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getID() == ObjectID.Player ) {
                    if (key == KeyEvent.VK_W) handler.setUp(false);
                    if (key == KeyEvent.VK_S) handler.setDown(false);
                    if (key == KeyEvent.VK_A) handler.setLeft(false);
                    if (key == KeyEvent.VK_D) handler.setRight(false);
                    if (key == KeyEvent.VK_SPACE) {
                        if (canEvade) {
                            canEvade = false;
                            handler.setEvade(true);
                            new Timer().schedule(new TimerTask() {
                                                     public void run() {
                                                         canEvade = true;

                                                     }
                                                 }, 400
                            );
                            new Timer().schedule(new TimerTask() {
                                                     public void run() {
                                                         handler.setEvade(false);
                                                     }
                                                 }, 200
                            );
                        }

                    }

                }
            }
        }
    }
}
