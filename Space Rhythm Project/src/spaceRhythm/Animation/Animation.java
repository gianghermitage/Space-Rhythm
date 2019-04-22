package spaceRhythm.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int frames;
    private int index = 0;
    private int count = 0;
    private BufferedImage[] img;
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage... imgs) {
        frames = imgs.length;
        img = new BufferedImage[imgs.length];
        System.arraycopy(imgs, 0, img, 0, imgs.length);
        this.speed = speed;
    }

    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();
        }
    }

    public void nextFrame() {
        currentImg = img[count];
        count++;
        if (count > frames - 1) count = 0;
    }

    public void drawAnimation(Graphics g, double x, double y, int offset) {
        g.drawImage(currentImg, (int) x - offset, (int) y, null);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}