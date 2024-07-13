package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminScoreboardController {
    
    @FXML
    private Label questionsAddedLabel;

    @FXML
    private Label usernameLabel;

    public void setData(String name, String qSolved) {
        usernameLabel.setText(name);
        questionsAddedLabel.setText(qSolved);
    }

}
