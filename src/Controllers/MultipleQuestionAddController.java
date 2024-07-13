package Controllers;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Main.Main;

import Questions.MultipleChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MultipleQuestionAddController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Button buttonA;

    @FXML
    private Button buttonAddQuestion;

    @FXML
    private Button buttonB;

    @FXML
    private Button buttonC;

    @FXML
    private Button buttonD;

    @FXML
    private TextField qTitleField;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private Button toTheMenuButton;

    // private AnchorPane addQuestionsPane = AdminController.addQuestionsPane;

    private int correctAnswer;

    private MultipleChoice q;

    @FXML
    void onAddButtonClicked(MouseEvent event) throws IOException, URISyntaxException{
        String language = AdminController.language;
        String difficulty = AdminController.difficulty;
        String questionTitle = "temp"; //temp
        if (qTitleField.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the title of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            questionTitle = qTitleField.getText();
        }

        q = new MultipleChoice(questionTitle, LandingPageController.userID, language, difficulty);
        q.setCorrectChoice(correctAnswer);
        
        Map<String, String> idMap = new HashMap<String, String>();
        idMap.put("Java", "JV");
        idMap.put("Python", "PY");
        idMap.put("Easy", "E");
        idMap.put("Medium", "M");
        idMap.put("Hard", "H");
        q.setId("MC" + idMap.get(language) + idMap.get(difficulty) + Main.server.getTotalQuestionCount());

        String statement = questionTextArea.getText();
        if (statement == null)
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            q.setStatement(statement);
        }

        if (answerTextArea.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content for the choices cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            //might cause error
            String choices = answerTextArea.getText();
            String[] choicesArray = choices.split("\n");
            for (String string : choicesArray) 
            {
                string = string.trim();
            }
            q.setChoices(choicesArray);
        }
        
        Main.server.addMultipleChoiceQuestion(q);
        //increment questions added for admin
        System.out.println("added question, I think...");
        Alert alert = new Alert(AlertType.INFORMATION, "Question added sucessfully to database!\nReturning to add questions page.", ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            //we could use a timer instead of an OK button and the app could just go back to the previous menu after like 5 seconds.
            root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void onToTheMenuClicked(MouseEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Warning, you are leaving the question adder.\nYour question will not be saved.\nDo you want to go back to the main menu?\n(All progress will be lost!).", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
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

    @FXML
    void onAClicked(ActionEvent event) {
        buttonA.setStyle("-fx-background-color: #2A9C16");
        buttonB.setStyle("-fx-background-color: #E5E5E5");
        buttonC.setStyle("-fx-background-color: #E5E5E5");
        buttonD.setStyle("-fx-background-color: #E5E5E5");
        correctAnswer = 0;
    }

    @FXML
    void onBClicked(ActionEvent event) {
        buttonA.setStyle("-fx-background-color: #E5E5E5");
        buttonB.setStyle("-fx-background-color: #2A9C16");
        buttonC.setStyle("-fx-background-color: #E5E5E5");
        buttonD.setStyle("-fx-background-color: #E5E5E5");
        correctAnswer = 1;
    }

    @FXML
    void onCClicked(ActionEvent event) {
        buttonA.setStyle("-fx-background-color: #E5E5E5");
        buttonB.setStyle("-fx-background-color: #E5E5E5");
        buttonC.setStyle("-fx-background-color: #2A9C16");
        buttonD.setStyle("-fx-background-color: #E5E5E5");
        correctAnswer = 2;
    }

    @FXML
    void onDClicked(ActionEvent event) {
        buttonA.setStyle("-fx-background-color: #E5E5E5");
        buttonB.setStyle("-fx-background-color: #E5E5E5");
        buttonC.setStyle("-fx-background-color: #E5E5E5");
        buttonD.setStyle("-fx-background-color: #2A9C16");
        correctAnswer = 3;
    }
}