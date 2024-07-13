package Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TracingQuestionViewController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea answerTextArea;

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

    private TraceCode question;

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
        else if (alert.getResult() == ButtonType.CANCEL)
        {
            //do stuff maybe
        }
    }

    @FXML
    void onButtonSaveClicked(MouseEvent event) throws IOException, URISyntaxException {
        if (qTitleField.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the title of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            question.setName(qTitleField.getText());
        }

        String statement = questionTextArea.getText();

        Map<String, String> idMap = new HashMap<String, String>();
        idMap.put("Java", "JV");
        idMap.put("Python", "PY");
        idMap.put("Easy", "E");
        idMap.put("Medium", "M");
        idMap.put("Hard", "H");
        question.setId(questionID);

        if (statement == null)
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            question.setStatement(statement);
        }

        if (answerTextArea.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content for the answers cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            question.setAnswer(answerTextArea.getText());
        }

        Main.server.addTraceCodeQuestion(question);
        //increment questions added for admin
        System.out.println("added question, I think...");
        Alert alert = new Alert(AlertType.INFORMATION, "Question updated successfully to database!\nReturning to add questions page.", ButtonType.OK);
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
        else if (alert.getResult() == ButtonType.CANCEL)
        {
            //do stuff maybe
        }
    }

    public void setData(String questionID) throws IOException, URISyntaxException {
        question = Main.server.getTCQuestion(questionID);
        qTitleField.setText(question.getName());
        questionTextArea.setText(question.getStatement());
        answerTextArea.setText(question.getAnswer());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        questionID = AdminController.tempId;
        try{
            setData(questionID);
        } catch (IOException | URISyntaxException e) {
            System.out.println("warning");
            //e.printStackTrace();
        }
    }
}