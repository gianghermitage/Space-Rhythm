package spaceRhythm.Game;

import spaceRhythm.Game.GameObjects.*;
import spaceRhythm.ImageLoader.BufferedImageLoader;
import spaceRhythm.Input.KeyInput;
import spaceRhythm.Input.MouseInput;
import spaceRhythm.SpriteSheet.SpriteSheet;
import spaceRhythm.UI.GameoverMenu;
import spaceRhythm.UI.GameState;
import spaceRhythm.UI.PauseMenu;
import spaceRhythm.UI.StateID;
import spaceRhythm.Window.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private boolean isRunning = false;
    private Thread thread;
    private GameState gameState;
    private GameoverMenu gameoverMenu;
    private PauseMenu pauseMenu;
    private Handler handler;
    private SpriteSheet ss;
    private BufferedImage bg = null;
    private BufferedImage map = null;
    private BufferedImage sprite_sheet = null;
    private BulletPattern bulletPattern;
    private Camera camera;

    public static int hp;

    public Game() {
        new Window(1280, 720, "GameTest", this);
        start();
        gameoverMenu = new GameoverMenu();
        pauseMenu = new PauseMenu();
        gameState = new GameState();
        gameState.setID(StateID.GAME);
        handler = new Handler();
        camera = new Camera(400,850);
        this.addKeyListener(new KeyInput(handler,gameState,this));
        BufferedImageLoader loader = new BufferedImageLoader();
        map = loader.loadImage("/map.png");
        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        //bg = loader.loadImage("/image.png");
        ss = new SpriteSheet(sprite_sheet);
        bulletPattern = new BulletPattern(handler,ss);
        this.addMouseListener(new MouseInput(handler, camera, ss,gameState,this));
        loadLevel(map);
        hp = 100;

    }

    private synchronized void start() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    public void reload(){
        gameoverMenu = new GameoverMenu();
        gameState = new GameState();
        gameState.setID(StateID.GAME);
        handler = new Handler();
        camera = new Camera(400,850);
        this.addKeyListener(new KeyInput(handler,gameState,this));
        BufferedImageLoader loader = new BufferedImageLoader();
        map = loader.loadImage("/map.png");
        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        //bg = loader.loadImage("/image.png");
        ss = new SpriteSheet(sprite_sheet);
        bulletPattern = new BulletPattern(handler,ss);
        this.addMouseListener(new MouseInput(handler, camera, ss,gameState,this));
        loadLevel(map);
        hp = 100;
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
                //System.out.println(frames);
                frames = 0;
            }
        }
        stop();
    }

    //update everything
    private void tick() {
        if (gameState.getID() == StateID.GAME) {
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
            this.createBufferStrategy(3);    //load 3 frames ahead
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1920, 1080);

        ////////////////Draw things here///////////////
        if (gameState.getID() == StateID.GAME) {
            BufferedImageLoader loader = new BufferedImageLoader();
            BufferedImage cursor = loader.loadImage("/cursor.png");
            Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(1,1), "cursor1");
            setCursor(c);
            g2d.translate(-camera.getX(), -camera.getY());
            handler.render(g);
            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.gray);
            g.fillRect(5,5,200,32);
            g.setColor(Color.green);
            g.fillRect(5,5,hp*2,32);
            g.setColor(Color.black);
            g.drawRect(5,5,200,32);


        } else if (gameState.getID() == StateID.GAMEOVER) {
            setCursor(Cursor.getDefaultCursor());
            g.fillRect(0, 0, 1920, 1080);
            g.drawImage(bg, 0, 0, null);
            gameoverMenu.render(g);

        } else if (gameState.getID() == StateID.PAUSE) {
            setCursor(Cursor.getDefaultCursor());
            g.fillRect(0, 0, 1920, 1080);
            g.drawImage(bg, 0, 0, null);
            pauseMenu.render(g);
        }

        //////////////////////////////////////////////////
        bs.show();
        g.dispose();
    }

    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int iX = 0; iX < w; iX++) {
            for (int iY = 0; iY < h; iY++) {
                int pixel = image.getRGB(iX, iY);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 255) handler.addObject(new Block(iX * 32, iY * 32, ObjectID.Block, ss));
                if (blue == 255) handler.addObject(new Player(iX * 32, iY * 32, ObjectID.Player, handler, ss, this,gameState));
                if (green == 255) handler.addObject(new Boss(iX * 32, iY * 32, ObjectID.Boss, handler,ss));
            }
        }
    }

}