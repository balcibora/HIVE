package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FriendListController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label streakLabel;

    public void setData(String name, String streak) {
        nameLabel.setText(name);
        streakLabel.setText(streak);
    }

}
