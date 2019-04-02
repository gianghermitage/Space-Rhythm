package spaceRhythm.UI;

import java.awt.*;

public class GameMenu {

    public Rectangle playButton = new Rectangle(150, 520, 100, 50);
    public Rectangle quitButton = new Rectangle(200, 570, 100, 50);

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font titleFont = new Font("Arial", Font.BOLD, 50);
        g.setFont(titleFont);
        g.setColor(Color.white);
        g.drawString("Space Rhythm", 100, 500);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 20, playButton.y + 35);
        g2d.draw(playButton);
        g.drawString("Quit", quitButton.x + 20, quitButton.y + 35);
        g2d.draw(quitButton);

    }
}
