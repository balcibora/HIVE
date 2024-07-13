package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VeryLargeScoreboardController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label questionSolvedLabel;

    @FXML
    private Label rankLabel;

    @FXML
    private Label streakLabel;

    public void setData(String rank, String name, String streak, String qSolved) {
        rankLabel.setText(rank);
        nameLabel.setText(name);
        streakLabel.setText(streak);
        questionSolvedLabel.setText(qSolved);
    }

}
