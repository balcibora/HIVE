package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserSmallScoreboardController {

    @FXML
    private Label nameLabel;

    public void setData(String name) {
        nameLabel.setText(name);
    }

}