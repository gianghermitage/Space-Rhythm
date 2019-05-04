package spaceRhythm.Game;

import spaceRhythm.Audio.Sound;
import spaceRhythm.Game.GameObjects.Block;
import spaceRhythm.Game.GameObjects.Boss;
import spaceRhythm.Game.GameObjects.ObjectID;
import spaceRhythm.Game.GameObjects.Player;
import spaceRhythm.ImageLoader.BufferedImageLoader;
import spaceRhythm.Input.KeyInput;
import spaceRhythm.Input.MouseInput;
import spaceRhythm.SpriteSheet.SpriteSheet;
import spaceRhythm.UI.*;
import spaceRhythm.Window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;


public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static int hp;
    public static boolean gameOver = false;
    private boolean isRunning = false;
    private Sound music = new Sound("spain");
    private Thread thread;
    private GameState gameState;
    private GameoverMenu gameoverMenu;
    private GameWonMenu gameWonMenu;
    private PauseMenu pauseMenu;
    private LoadingScreen loadingScreen;
    private Handler handler;
    private SpriteSheet ss;
    private BufferedImage map = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    private Camera camera;

    public Game() {
        new Window(1280, 720, "Space Rhythm", this);
        start();
        initGame();
    }

    public void initGame() {
        gameOver = false;
        hp = 100;
        handler = new Handler();
        camera = new Camera(400, 850);
        gameState = new GameState();
        this.addKeyListener(new KeyInput(handler, gameState));
        BufferedImageLoader loader = new BufferedImageLoader();
        map = loader.loadImage("/map.png");
        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        ss = new SpriteSheet(sprite_sheet);
        floor = ss.grabImage(4, 6, 32, 32);
        loadLevel(map);
        BufferedImage cursor = loader.loadImage("/cursor.png");
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(1, 1), "cursor1");
        setCursor(c);
        this.addMouseListener(new MouseInput(handler, camera, ss, gameState));
        loadingScreen = new LoadingScreen();
        gameoverMenu = new GameoverMenu();
        pauseMenu = new PauseMenu();
        gameWonMenu = new GameWonMenu();
        gameState.setID(StateID.LOADING);
        new Timer().schedule(new TimerTask() {
                                 public void run() {
                                     gameState.setID(StateID.GAME);
                                 }
                             }, 500
        );
    }


    private synchronized void start() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }


    private synchronized void stop() {
        if (!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double fps = 60.0;  //fps
        double ns = 1000000000 / fps; //time per tick = maximum frame time in nano second
        double delta = 0;    //
        long timer = 0;
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            timer += now - lastTime;
            lastTime = now;

            //if delta >=1 then update and render, by this way image will be render independently from frame time
            while (delta >= 1) {
                tick();
                delta--;
                render();
                frames++;

            }
            //fps counter
            if (timer > 1000000000) {
                timer = 0;
//                System.out.println(frames);
                frames = 0;
            }
        }
        stop();
    }

    //update everything
    private void tick() {
        if (gameState.getID() == StateID.GAME || gameState.getID() == StateID.LOADING ) {
            handler.tick();
            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getID() == ObjectID.Player) {
                    camera.tick(handler.object.get(i));
                }
            }
        }

    }

    //render everything
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(4);    //load 3 frames ahead
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.fillRect(0, 0, 1920, 1080);
        //g.setColor(Color.black);
        ////////////////Draw things here///////////////
        if (gameState.getID() == StateID.GAME ) {
            for (int xx = 0; xx < 30 * 72; xx += 32) {
                for (int yy = 0; yy < 30 * 72; yy += 32) {
                    g.drawImage(floor, xx, yy, null);
                }
            }

            g2d.translate(-camera.getX(), -camera.getY());
            handler.render(g);
            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.green);
            g.fillRect(5, 5, hp * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);


        } else if (gameState.getID() == StateID.LOADING) {
            g.fillRect(0, 0, 1920, 1080);
            loadingScreen.render(g);

        } else if (gameState.getID() == StateID.GAMEOVER) {
            g.fillRect(0, 0, 1920, 1080);
            gameoverMenu.render(g);

        } else if (gameState.getID() == StateID.VICTORY) {
            g.fillRect(0, 0, 1920, 1080);
            gameWonMenu.render(g);

        } else if (gameState.getID() == StateID.PAUSE) {
            g.fillRect(0, 0, 1920, 1080);
            pauseMenu.render(g);
        }

        //////////////////////////////////////////////////
        bs.show();
        g.dispose();
    }

    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
//
        handler.addObject(new Player(1017, 1200,
                ObjectID.Player, handler, ss, this, gameState));

        for (int iX = 0; iX < w; iX++) {
            for (int iY = 0; iY < h; iY++) {
                int pixel = image.getRGB(iX, iY);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 255) handler.addObject(new Block(iX * 32, iY * 32, ObjectID.Block, ss));
                if (green == 255) handler.addObject(new Boss(iX * 32, iY * 32, ObjectID.Boss, handler, ss, gameState));
            }
        }
    }

}