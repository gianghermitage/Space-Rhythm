package spaceRhythm.UI;


import javax.swing.*;

public class Tutorial extends JFrame {

    public Tutorial(){
        super("Tutorial");
        setSize(1280,720);
        ImageIcon icon = new ImageIcon(getClass().getResource("/tutorial.png"));
        add(new JLabel(icon));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
