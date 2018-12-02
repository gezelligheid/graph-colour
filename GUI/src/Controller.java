import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    public void startGame(ActionEvent actionEvent) {
        System.out.println("start game button was clicked");
    }
}