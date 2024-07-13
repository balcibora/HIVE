package Controllers;

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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import Main.Main;
import Questions.CodeWriting;

public class UserWriteTheCodeController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private CodeWriting q;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Button buttonSubmitQuestion;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private Label feedbackTitle;

    @FXML
    private Label questionTitle;

    @FXML
    private Label testcaseLabel;

    @FXML
    private Button toTheMenuButton;

    private String questionID;

    private CodeWriting question;

    @FXML
    void onSubmitQuestionClicked(MouseEvent event) throws IOException, URISyntaxException {
        
        if (buttonSubmitQuestion.getText().equals("Retry"))
        {
            buttonSubmitQuestion.setText("Submit Question");
            questionTextArea.setText(question.getStatement());
            answerTextArea.clear();
            answerTextArea.setEditable(true);
            feedbackTitle.setText("");
            answerTextArea.setWrapText(true);
        }
        else
        {
            question = Main.server.getWCQuestion(questionID);
            Boolean isCorrect = true;
            int True = 0, False = 0;
            String userAnswer = answerTextArea.getText(), output;
            output = question.checkAnswer(userAnswer);
            String[] cases = output.split(",");
    
            for(String s: cases){
                if(s.equals("incorrect")){
                    isCorrect = false;
                    False++;
                }else if(s.equals("correct")){
                    True++;
                }else{
                    isCorrect = null;
                }
            }
            // System.out.println("out of " + (True + False) + " testcases, " + True + " of them are correct");
            testcaseLabel.setText(True + " out of " + (True + False) + " testcases passed.");
            questionTextArea.setText(answerTextArea.getText());
            answerTextArea.setEditable(false);
            answerTextArea.setText(question.getGptResponse(userAnswer, isCorrect));
            // answerTextArea.setStyle("-fx-background-color: #FFE878");
            answerTextArea.setWrapText(true);
            feedbackTitle.setText("Advanced AI Feedback");
    
            if (!isCorrect)
            {
                buttonSubmitQuestion.setText("Retry");
            }
            else
            {
                Main.server.solved(LandingPageController.userID, questionID);
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
        q = Main.server.getWCQuestion(questionID2);
        questionTextArea.setText(q.getStatement());
        questionTitle.setText(q.getName());
    }
}
