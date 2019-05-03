package spaceRhythm.Window;

import spaceRhythm.Game.Game;
import spaceRhythm.UI.Tutorial;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window(int width, int height, String title, Game game) {

        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(1920, 1080));
        frame.setMinimumSize(new Dimension(width, height));

        frame.add(game);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
