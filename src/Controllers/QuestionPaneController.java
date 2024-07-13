package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Main.Main;
import Questions.Question;
// import Controllers.UserController;

public class QuestionPaneController {

    @FXML
    private Label diffLabel;

    @FXML
    private Label langLabel;

    @FXML
    private Label qTypeLabel;

    @FXML
    private Button solveButton;

    @FXML
    private Label titleLabel;

    protected String questionID;

    private AdminController adminController;

    private UserController userController;
    
    @FXML
    void onButtonClicked(MouseEvent event) throws URISyntaxException {

        System.out.println();
        if (Main.server.isAdmin(LandingPageController.userID))
        {
            if (questionID.substring(0,2).equals("MC"))
            {
                try {
                    adminController.changePane(questionID, AdminController.MCViewRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("FB"))
            {
                try {
                    adminController.changePane(questionID, AdminController.FBViewRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("WC"))
            {
                try {
                    adminController.changePane(questionID, AdminController.WCViewRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("FE"))
            {
                try {
                    adminController.changePane(questionID, AdminController.FEViewRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("TC"))
            {
                try {
                    adminController.changePane(questionID, AdminController.TCViewRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            if (questionID.substring(0,2).equals("MC"))
            {
                try {
                    userController.changePane(questionID, UserController.MCRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("FB"))
            {
                try {
                    userController.changePane(questionID, UserController.FBRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("WC"))
            {
                try {
                    userController.changePane(questionID, UserController.WCRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("FE"))
            {
                try {
                    userController.changePane(questionID, UserController.FERoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (questionID.substring(0,2).equals("TC"))
            {
                try {
                    userController.changePane(questionID, UserController.TCRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setData(String aQuestionID, AdminController adminController) throws IOException, URISyntaxException
    {
        
        Question question = null;

        solveButton.setText("View");

        questionID = aQuestionID;
        this.adminController = adminController;

        if (questionID.substring(0,2).equals("MC"))
        {
            question = Main.server.getMCQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("FE"))
        {
            question = Main.server.getFEQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("FB"))
        {
            question = Main.server.getFTBQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("WC"))
        {
            question = Main.server.getWCQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("TC"))
        {
            question = Main.server.getTCQuestion(questionID);
        }

        titleLabel.setText(question.getName());
        diffLabel.setText(question.getDiff());
        langLabel.setText(question.getLanguage());
        qTypeLabel.setText(question.getType());
    }

    public void setData(String aQuestionID, UserController userController) throws IOException, URISyntaxException
    {
        Question question = null;
        
        solveButton.setText("Solve");

        questionID = aQuestionID;
        this.userController = userController;

        if (questionID.substring(0,2).equals("MC"))
        {
            question = Main.server.getMCQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("FE"))
        {
            question = Main.server.getFEQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("FB"))
        {
            question = Main.server.getFTBQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("WC"))
        {
            question = Main.server.getWCQuestion(questionID);
        }
        else if (questionID.substring(0,2).equals("TC"))
        {
            question = Main.server.getTCQuestion(questionID);
        }

        Map<String, String> solvedQuestions = new HashMap<>();
        try {
            solvedQuestions = Main.server.getSolvedQuestions(LandingPageController.userID);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        for (String tracingQuestionID : solvedQuestions.keySet())
        {
            if (tracingQuestionID.equals(questionID))
            {
                solveButton.setStyle("-fx-background-color: #2EBD11");
            }
        }

        titleLabel.setText(question.getName());
        diffLabel.setText(question.getDiff());
        langLabel.setText(question.getLanguage());
        qTypeLabel.setText(question.getType());
    }
}
