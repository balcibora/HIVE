package Controllers;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Main.Main;
import Questions.CodeWriting;
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

public class WriteTheCodeQuestionAddController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button buttonAddQuestion;

    @FXML
    private TextField qTitleField;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private TextArea testcaseArea;

    @FXML
    private Button toTheMenuButton;

    @FXML
    void addCaseClicked(MouseEvent event) {

    }

    @FXML
    void onAddButtonClicked(MouseEvent event) throws IOException, URISyntaxException {
        //TODO: something idk
        String language = AdminController.language;
        String difficulty = AdminController.difficulty;
        String questionTitle = "temp"; //temp
        if (qTitleField.getText() == null)
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the title of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            questionTitle = qTitleField.getText();
        }

        CodeWriting q = new CodeWriting(questionTitle, LandingPageController.userID, language, difficulty);

        String statement = questionTextArea.getText();

        Map<String, String> idMap = new HashMap<String, String>();
        idMap.put("Java", "JV");
        idMap.put("Python", "PY");
        idMap.put("Easy", "E");
        idMap.put("Medium", "M");
        idMap.put("Hard", "H");
        q.setId("WC" + idMap.get(language) + idMap.get(difficulty) + Main.server.getTotalQuestionCount());

        if (statement.isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, the content of the question cannot be left blank. \nPlease fill in the required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            q.setStatement(statement);
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
                    q.addTestcase(tCase[0], tCase[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(q.getTestCases());Main.server.addCodeWritingQuestion(q);
            System.out.println("added question, I think...");
            Alert alert = new Alert(AlertType.INFORMATION, "Question added sucessfully to database!\nReturning to add questions page.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
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

}
