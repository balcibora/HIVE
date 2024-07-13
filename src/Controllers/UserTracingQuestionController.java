package Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import Main.Main;
import Questions.TraceCode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserTracingQuestionController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private TraceCode q;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Button buttonSubmitAnswer;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private Label resultLabel;

    @FXML
    private Label questionTitle;

    @FXML
    private Button toTheMenuButton;

    private String questionID;


    @FXML
    void onSubmitQuestionClicked(MouseEvent event) throws IOException, URISyntaxException {
        if (q.checkAnswer(answerTextArea.getText()).equals("Correct")) //correct
        {
            Main.server.solved(LandingPageController.userID, questionID);
            resultLabel.setText("Correct");
            resultLabel.setStyle("-fx-background-color: green");
        }
        else
        {
            resultLabel.setText("Incorrect");
            resultLabel.setStyle("-fx-background-color: red");
            buttonSubmitAnswer.setText("Retry");
        }
    }

    @FXML
    void onToTheMenuClicked(MouseEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Warning, you are leaving the question.\nYour changes will not be saved.\nDo you want to go back to the questions list?\n(All progress will be lost!).", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            root = FXMLLoader.load(getClass().getResource("../ui/User.fxml")); //might break the code.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if (alert.getResult() == ButtonType.CANCEL)
        {
            //do stuff maybe
        }
    }

       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionID = UserController.tempId;
        try {
            setData(UserController.tempId);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setData(String questionID2) throws IOException, URISyntaxException {
        q = Main.server.getTCQuestion(questionID2);
        questionTextArea.setText(q.getStatement());
        questionTitle.setText(q.getName());
    }

}
