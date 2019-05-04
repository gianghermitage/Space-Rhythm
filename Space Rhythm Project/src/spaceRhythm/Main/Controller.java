package spaceRhythm.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import spaceRhythm.Game.Game;
import spaceRhythm.UI.Tutorial;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).

    }

    // When user click on myButton
    // this method will be called.
    public void playGame(ActionEvent event) {
        new Game();
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    public void tutorial(ActionEvent event) {
        new Tutorial();
    }

    public void quitGame(ActionEvent event) {
        System.exit(1);
    }

}