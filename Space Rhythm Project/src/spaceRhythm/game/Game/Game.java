package spaceRhythm.game.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import spaceRhythm.window.Window.*;


public class Game extends Canvas implements Runnable {
	int x = 0;	//test
	int y = 0;
	boolean isGoingLeft = true;

	
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Thread thread;
	
	
	
	public Game(){
		new Window(1280,768,"GameTest",this);
		start();
	}
	
	private synchronized void start() {
		if(isRunning)return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!isRunning)return;
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
		double ns = 1000000000 / fps; //time per tick = maximum frametime in nano second
		double delta = 0;	//
		long timer = 0;
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			timer += now - lastTime;
			lastTime = now;
			
			//if delta >=1 then update and render, by this way image will be render independently from frametime
			while(delta >= 1) {
				tick();
				delta--;
				render();
				frames++;
			}
			//fps counter
			if(timer > 1000000000) {
				timer = 0;
				System.out.println(frames);
				frames = 0;
			}
		}
		stop();
	}
	//update everything
	private void tick() {
		if(isGoingLeft) {
			x++;
		}
		else x--;
		if(x+40 >= 1280) {
			isGoingLeft = false;
		}
		else if(x <= 0) {
			isGoingLeft = true;
		}
	}
	//render everything
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);	//load 3 frames ahead
			return;
		}
		Graphics graphic = bs.getDrawGraphics();
		Graphics background = bs.getDrawGraphics();
		background.setColor(Color.white);
		background.fillRect(0, 0, 1280, 768);
		////////////////Draw thing here lmao///////////////
		if(isGoingLeft == true) {
			graphic.setColor(Color.black);
			graphic.fillRect(x, 10, 40, 40);
		}
		else {
			graphic.setColor(Color.white);
			background.setColor(Color.black);
			background.fillRect(0, 0, 1280, 768);
			graphic.fillRect(x, 10, 40, 40);
		}
		//////////////////////////////////////////////////
		bs.show();
		graphic.dispose();

	}
	
	
	
	public static void main(String args[]) {
		new Game();


	}

}