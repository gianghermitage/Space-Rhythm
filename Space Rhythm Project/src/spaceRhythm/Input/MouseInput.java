package spaceRhythm.Input;

import spaceRhythm.Game.Camera;
import spaceRhythm.Game.Game;
import spaceRhythm.Game.GameObjects.BulletBlue;
import spaceRhythm.Game.GameObjects.BulletRed;
import spaceRhythm.Game.GameObjects.GameObject;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.Handler;
import spaceRhythm.SpriteSheet.SpriteSheet;
import spaceRhythm.UI.GameState;
import spaceRhythm.UI.StateID;
import static java.lang.System.out;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;
    private GameState gameState;
    private int rmbCount = 0;
    private SpriteSheet ss;
    private Game game;

    public MouseInput(Handler handler, Camera camera, SpriteSheet ss,GameState gameState,Game game) {
        this.handler = handler;
        this.camera = camera;
        this.ss = ss;
        this.gameState = gameState;
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        if (gameState.getID() == StateID.GAME) {
            super.mousePressed(e);
            int mx = (int) (e.getX() + camera.getX());
            int my = (int) (e.getY() + camera.getY());
            if (e.getButton() == MouseEvent.BUTTON3) rmbCount++;
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (int i = 0; i < handler.object.size(); i++) {
                    GameObject tempObject = handler.object.get(i);
                    if (tempObject.getID() == ObjectID.Player) {
                        if (rmbCount % 2 == 0)
                            handler.addObject(new BulletBlue((int)(tempObject.getX() + 16), (int)(tempObject.getY() + 24), ObjectID.BulletBlue, handler, mx, my, ss));
                        else if (rmbCount % 2 != 0)
                            handler.addObject(new BulletRed((int)(tempObject.getX() + 16), (int)(tempObject.getY() + 24), ObjectID.BulletRed, handler, mx, my, ss));
                    }
                }
            }
        }

        if (gameState.getID() == StateID.MENU) {

            /*
            public  Rectangle playButton = new Rectangle(150,520,100,50);
            public  Rectangle quitButton = new Rectangle(200,570,100,50);
            *
            * */
            int mx = e.getX();
            int my = e.getY();
            if (mx >= 150 && mx <= 250) {
                if (my >= 520 && my <= 570) {
                    //press play
                    game.reload();
                }
            }
            if (mx >= 200 && mx <= 300) {
                if (my >= 570 && my <= 620) {
                    //press quit
                    System.exit(1);
                }
            }

        }


    }
}
