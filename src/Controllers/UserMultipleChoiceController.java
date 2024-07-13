package Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import Main.Main;
import Questions.MultipleChoice;
import javafx.event.ActionEvent;
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

public class UserMultipleChoiceController implements Initializable{

    private int answer;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private MultipleChoice q;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Button buttonA;

    @FXML
    private Button buttonB;

    @FXML
    private Button buttonC;

    @FXML
    private Button buttonD;

    @FXML
    private Button buttonSubmitAnswer;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private Label questionTitle;

    @FXML
    private Button toTheMenuButton;

    private String questionID;

    @FXML
    void onAClicked(ActionEvent event) {
        answer = 0;
        buttonA.setStyle("-fx-background-color: #6ffc94;");
        buttonB.setStyle("-fx-background-color: #E5E5E5;");
        buttonC.setStyle("-fx-background-color: #E5E5E5;");
        buttonD.setStyle("-fx-background-color: #E5E5E5;");
    }

    @FXML
    void onBClicked(ActionEvent event) {
        answer = 1;
        buttonB.setStyle("-fx-background-color: #6ffc94;");
        buttonA.setStyle("-fx-background-color: #E5E5E5;");
        buttonC.setStyle("-fx-background-color: #E5E5E5;");
        buttonD.setStyle("-fx-background-color: #E5E5E5;");
    }

    @FXML
    void onCClicked(ActionEvent event) {
        answer = 2;
        buttonC.setStyle("-fx-background-color: #6ffc94;");
        buttonA.setStyle("-fx-background-color: #E5E5E5;");
        buttonB.setStyle("-fx-background-color: #E5E5E5;");
        buttonD.setStyle("-fx-background-color: #E5E5E5;");
    }

    @FXML
    void onDClicked(ActionEvent event) {
        answer = 3;
        buttonD.setStyle("-fx-background-color: #6ffc94;");
        buttonB.setStyle("-fx-background-color: #E5E5E5;");
        buttonC.setStyle("-fx-background-color: #E5E5E5;");
        buttonA.setStyle("-fx-background-color: #E5E5E5;");
    }

    @FXML
    void onSubmitQuestionClicked(MouseEvent event) throws IOException, URISyntaxException {
        int correctIndex = q.getCorrectIndex();
        
        switch (correctIndex) {
            case 0:
                buttonA.setStyle("-fx-background-color: green");
                break;
            case 1:
                buttonB.setStyle("-fx-background-color: green");
                break;
            case 2:
                buttonC.setStyle("-fx-background-color: green");
                break;
            case 3:
                buttonD.setStyle("-fx-background-color: green");
                break;
            default:
                System.out.println("should be dead code. This line should never be printed.");
                break;
        }
        if (correctIndex == answer) //correct
        {
            Main.server.solved(LandingPageController.userID, questionID);
        }
        else
        {
            switch (answer) {
                case 0:
                    buttonA.setStyle("-fx-background-color: red;");
                    break;
                case 1:
                    buttonB.setStyle("-fx-background-color: red;");
                    break;
                case 2:
                    buttonC.setStyle("-fx-background-color: red;");
                    break;
                case 3:
                    buttonD.setStyle("-fx-background-color: red;");
                    break;
                default:
                    System.out.println("should be dead code. This line should never be printed.");
                    break;
            }
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
        q = Main.server.getMCQuestion(questionID2);
        questionTextArea.setText(q.getStatement());
        questionTitle.setText(q.getName());
        Map<String, String> choices = q.getChoices();
        String choiceField = "";
        for(String choice: choices.values()) {
            choiceField += choice + "\n";
        }
        answerTextArea.setText(choiceField);
    }

}
