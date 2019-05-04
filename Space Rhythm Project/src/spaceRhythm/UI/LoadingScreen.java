package spaceRhythm.UI;

import java.awt.*;

public class LoadingScreen {


    public void render(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1920, 1080);
        Graphics2D g2d = (Graphics2D) g;
        Font titleFont = new Font("Arial", Font.BOLD, 50);
        g.setFont(titleFont);
        g.setColor(Color.white);
        g.drawString("LOADING", 100, 500);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);

    }
}