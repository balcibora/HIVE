package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserLargeScoreboardController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label questionsSolvedLabel;

    @FXML
    private Label streakLabel;

    public void setData(String name, String streak, String qSolved) {
        nameLabel.setText(name);
        streakLabel.setText(streak);
        questionsSolvedLabel.setText(qSolved);
    }

}
