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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MultipleQuestionViewController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    private Button buttonOnlyExit;

    @FXML
    private Button buttonSaveExit;

    @FXML
    private TextField qTitleField;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private Button toTheMenuButton;

    private String questionID;
    private int correctAnswer;

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

    @FXML
    void onButtonOnlyExitClicked(MouseEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Warning, you are leaving the question viewer without saving.\nYour changes will not be saved.\nDo you want to go back to the question filter?\n(All progress will be lost!).", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void onButtonSaveClicked(MouseEvent event) throws IOException, URISyntaxException {

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

        MultipleChoice q = Main.server.getMCQuestion(questionID);
        q.setCorrectChoice(correctAnswer);
        q.setName(questionTitle);

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
            String choices = answerTextArea.getText();
            String[] choicesArray = choices.split("\n");
            for (String string : choicesArray) 
            {
                string = string.trim();
            }
            q.setChoices(choicesArray);
        }
        
        Main.server.addMultipleChoiceQuestion(q);
        Alert alert = new Alert(AlertType.INFORMATION, "Question updated successfully!\nReturning to add questions page.", ButtonType.OK);
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
        Alert alert = new Alert(AlertType.CONFIRMATION, "Warning, you are leaving the question viewer.\nYour changes will not be saved.\nDo you want to go back to the question filter?\n(All progress will be lost!).", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionID = AdminController.tempId;
        try{
            setData(questionID);
        } catch (IOException | URISyntaxException e) {
            System.out.println("warning");
        }
    }

    public void setData(String questionID) throws IOException, URISyntaxException {
        MultipleChoice mc = Main.server.getMCQuestion(questionID);
        correctAnswer = mc.getCorrectIndex();
        Map<String, String> choices = mc.getChoices();
        String choiceField = "";
        for(String choice: choices.values()) {
            choiceField += choice + "\n";
        }
        qTitleField.setText(mc.getName());
        questionTextArea.setText(mc.getStatement());
        answerTextArea.setText(choiceField);
        if(correctAnswer == 0) buttonA.setStyle("-fx-background-color: green");
        if(correctAnswer == 1) buttonB.setStyle("-fx-background-color: green");
        if(correctAnswer == 2) buttonC.setStyle("-fx-background-color: green");
        if(correctAnswer == 3) buttonD.setStyle("-fx-background-color: green");
    }

}
