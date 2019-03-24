package spaceRhythm.Game;

import spaceRhythm.Input.KeyInput;
import spaceRhythm.Window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;

    public Game() {
        new Window(1280, 768, "GameTest", this);
        start();
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        handler.addObject(new Player(100,100,ObjectID.Player,handler));

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
    }

    //render everything
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);    //load 3 frames ahead
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 1280, 768);
        ////////////////Draw thing here lmao///////////////
        handler.render(g);
        //////////////////////////////////////////////////
        bs.show();
        g.dispose();
    }

}