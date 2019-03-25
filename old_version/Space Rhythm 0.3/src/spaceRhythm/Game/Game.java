package spaceRhythm.Game;

import spaceRhythm.ImageLoader.BufferedImageLoader;
import spaceRhythm.Input.KeyInput;
import spaceRhythm.Window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private BufferedImage map = null;
    private Camera camera;


    public Game() {
        new Window(1280, 720, "GameTest", this);
        start();
        handler = new Handler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler));
        BufferedImageLoader loader = new BufferedImageLoader();
        map = loader.loadImage("/map.png");
        loadLevel(map);
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

            }
            render();
            frames++;
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
        handler.tick();
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getID() == ObjectID.Player) {
                camera.tick(handler.object.get(i));
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
        g.fillRect(0, 0, 1280, 720);
        g2d.translate(-camera.getX(), -camera.getY());
        ////////////////Draw thing here lmao///////////////
        handler.render(g);
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
                if (red == 255) handler.addObject(new Block(iX * 32, iY * 32, ObjectID.Block));
                if (blue == 255) handler.addObject(new Player(iX * 32, iY * 32, ObjectID.Player, handler));
            }
        }
    }

}