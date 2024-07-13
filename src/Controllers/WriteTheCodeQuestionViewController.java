package Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Main.Main;
import Questions.CodeWriting;

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

public class WriteTheCodeQuestionViewController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button buttonOnlyExit;

    @FXML
    private Button buttonSaveExit;

    @FXML
    private TextField qTitleField;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private TextArea testcaseArea;

    @FXML
    private Button toTheMenuButton;

    private String questionID;
    
    private CodeWriting question;

    //TODO: initialize and add the statements and stuff from the question to the current fields.

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
        if (qTitleField.getText() == null)
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

        if (statement.isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            question.setStatement(statement);
        }

        if (testcaseArea.getText().isBlank())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the test cases cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            String testCases = testcaseArea.getText();
            String[] testCaseList = testCases.split(",\n");
            for (String testCase : testCaseList)
            {
                String[] tCase = testCase.split(":");
                try {
                    question.addTestcase(tCase[0], tCase[1]);
                } catch (Exception e) {
                    // do absolutely nothing, the admin can cry.
                }
            }
            System.out.println(question.writeTestCases());
        }
        
        Main.server.addCodeWritingQuestion(question);
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

    public void setData(String questionID2) throws IOException, URISyntaxException {
        question = Main.server.getWCQuestion(questionID2);
        qTitleField.setText(question.getName());
        questionTextArea.setText(question.getStatement());
        System.out.println(question.writeTestCases());
        testcaseArea.setText(question.writeTestCases());
        System.out.println("******");
        System.out.println(question.getAnswer());
        System.out.println("*********");

        //TODO: Use the newly acquired questionID and set the required labels and fields accordingly.
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
