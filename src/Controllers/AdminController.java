package Controllers;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Main.Main;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * This is the class where we moderate the Admin UI. All question additions, deletions
 * and scene changes are made here. Also, we need to update certain parts of the UI according
 * to the information that we gather from the database, such as:
 * - The information of the logged in Admin to view on Account tab.
 * - Scoreboard information to view on the side.
 * - Filtered/Unfiltered Question list to view on View Questions tab.
 */

public class AdminController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String tempId;

    protected static String language;
    protected static String difficulty;
    protected static String questionTitle;

    @FXML
    private Tab accountTab;

    @FXML
    private AnchorPane addQuestionsPane;

    @FXML
    private Tab addQuestionsTab;

    @FXML
    private Button confirmButton;

    @FXML
    private Label confirmLabel;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private Label dateOfCreationLabel;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Button filterButton;

    @FXML
    private Button logOutButton;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private ScrollPane questionPane;

    @FXML
    private Label questionsAddedLabel1;

    @FXML
    private Label questionsAddedLabel2;

    @FXML
    private Label questionsAddedLabel3;

    @FXML
    private Label questionsAddedLabel4;

    @FXML
    private ScrollPane scoreboard1;

    @FXML
    private ScrollPane scoreboard2;

    @FXML
    private ScrollPane scoreboard3;

    @FXML
    private ChoiceBox<String> selectDiff;

    @FXML
    private ChoiceBox<String> selectLang;

    @FXML
    private ChoiceBox<String> selectQType;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private ChoiceBox<String> viewDiff;

    @FXML
    private ChoiceBox<String> viewLang;

    @FXML
    private ChoiceBox<String> viewQType;

    @FXML
    private AnchorPane viewQuestionPane = new AnchorPane();

    @FXML
    private Tab viewQuestionsTab;

    private ArrayList<Pair<String, Integer> > sortedAdmins;

    /* public AnchorPane getViewQuestionPane() {
        return viewQuestionPane;
    }

    public AdminController getAdminController()
    {
        return this;
    } */

    public TabPane getTabPane() {
        return tabPane;
    }
    public Tab getAccountTab() {
        return accountTab;
    }
    public Tab getAddQuestionsTab() {
        return addQuestionsTab;
    }
    public Tab getViewQuestionsTab() {
        return viewQuestionsTab;
    }

    private String[] addQuestionTypes = {"Multiple Choice", "Fill the Blank", "Find the Error", "Trace Code", "Write the Code"};
    private String[] addLangTypes = {"Java", "Python"};
    private String[] addDiffTypes = {"Easy", "Medium", "Hard"};

    private String[] viewQuestionTypes = {"All", "Multiple Choice", "Fill the Blank", "Find the Error", "Trace Code", "Write the Code"};
    private String[] viewLangTypes = {"All", "Java", "Python"};
    private String[] viewDiffTypes = {"All", "Easy", "Medium", "Hard"};

    private class Sortbycount implements Comparator<Pair<String, Integer> > {
 
        // Method
        // Sorting in ascending order of roll number
        public int compare(Pair<String, Integer> a, Pair<String, Integer> b)
        {
     
            return b.getValue() - a.getValue();
        }
    }
    private String MCAddRoot = "../ui/MultipleQuestionAdd.fxml";
    private String WCAddRoot = "../ui/WriteTheCodeQuestionAdd.fxml";
    private String FBAddRoot = "../ui/FillTheBlankQuestionAdd.fxml";
    private String FEAddRoot = "../ui/FindTheErrorQuestionAdd.fxml";
    private String TCAddRoot = "../ui/TracingQuestionAdd.fxml";

    protected static String MCViewRoot = "../ui/MultipleQuestionView.fxml";
    protected static String WCViewRoot = "../ui/WriteTheCodeQuestionView.fxml";
    protected static String FBViewRoot = "../ui/FillTheBlankQuestionView.fxml";
    protected static String FEViewRoot = "../ui/FindTheErrorQuestionView.fxml";
    protected static String TCViewRoot = "../ui/TracingQuestionView.fxml";

    // protected String questionID;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //enter the user here, not a new user!!! I guess

        // questionTable.getColumns().addAll(titleOfTheQuestionColumn, diffColumn, langColumn, qTypeColumn);
        
        
        // accountTab.getOnSelectionChanged(new EventHand)
        
        
        try {
            questionsAddedLabel1.setText("Questions Added: " + Main.server.getWrittenList(LandingPageController.userID).size());
            questionsAddedLabel2.setText("Questions Added: " + Main.server.getWrittenList(LandingPageController.userID).size());
            questionsAddedLabel3.setText("Questions Added: " + Main.server.getWrittenList(LandingPageController.userID).size());
            questionsAddedLabel4.setText("Questions Added: " + Main.server.getWrittenList(LandingPageController.userID).size());
            emailLabel.setText("Email: " + Main.server.getEmail(LandingPageController.userID));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        try {
            usernameLabel.setText("Username: " + Main.server.getUsername(LandingPageController.userID));
            // Below, we get a runtime error, I guess we return null from dateOfCreation, but the program works, so...
            dateOfCreationLabel.setText("Date of Creation: " + Main.server.getDateOfCreation(LandingPageController.userID));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
 
        sortedAdmins = new ArrayList<>();
        String[] adminList = new String[0];
        try {
            adminList = Main.server.getAllAdminIDs();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < adminList.length; i++) {
            try {
                sortedAdmins.add(new Pair<String,Integer>(Main.server.getUsername(adminList[i]), Main.server.getNumberOfWrittenQuestions(adminList[i])));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(sortedAdmins, new Sortbycount());

        selectQType.getItems().addAll(addQuestionTypes);
        selectLang.getItems().addAll(addLangTypes);
        selectDiff.getItems().addAll(addDiffTypes);

        viewQType.getItems().addAll(viewQuestionTypes);
        viewLang.getItems().addAll(viewLangTypes);
        viewDiff.getItems().addAll(viewDiffTypes);

        try {
            setScoreboards(sortedAdmins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScoreboards (ArrayList<Pair<String, Integer> > sortedAdmins) throws IOException {
        int column = 0;
        int row = 0;

        GridPane scoreboardGrid1 = new GridPane();
        GridPane scoreboardGrid2 = new GridPane();
        GridPane scoreboardGrid3 = new GridPane();

        for (int i = 0; i < sortedAdmins.size(); i++)
        {
            String name = sortedAdmins.get(i).getKey();
            String qSolved = sortedAdmins.get(i).getValue() + "";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/AdminScoreboard.fxml"));
            AnchorPane adminScoreboard = fxmlLoader.load();
            AdminScoreboardController adminScoreboardController = fxmlLoader.getController();
            adminScoreboardController.setData(name, qSolved);
            scoreboardGrid1.add(adminScoreboard, column, row);
            
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/AdminScoreboard.fxml"));
            adminScoreboard = fxmlLoader.load();
            adminScoreboardController = fxmlLoader.getController();
            adminScoreboardController.setData(name, qSolved);
            scoreboardGrid2.add(adminScoreboard, column, row);

            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/AdminScoreboard.fxml"));
            adminScoreboard = fxmlLoader.load();
            adminScoreboardController = fxmlLoader.getController();
            adminScoreboardController.setData(name, qSolved);
            scoreboardGrid3.add(adminScoreboard, column, row++);
        }
        scoreboard1.setContent(scoreboardGrid1);
        scoreboard2.setContent(scoreboardGrid2);
        scoreboard3.setContent(scoreboardGrid3);
    }

    @FXML
    void onToTheEditorClicked(MouseEvent event) {

        System.out.println();
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane newContent;
        
            //we could separate these into different conditions to give more specific error messages but I didn't do that right now.
            if (selectLang.getValue() == null || selectDiff.getValue() == null || selectQType.getValue() == null)
            {
                Alert alert = new Alert(AlertType.ERROR, "Warning, all fields must be filled in. \nPlease fill the required fields fully.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    //do stuff maybe
                }
            }
            else
            {
                switch (selectQType.getValue()) {
                    case "Multiple Choice":
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue();
                        loader.setLocation(Main.class.getResource(MCAddRoot));
                        newContent = loader.load();
                        addQuestionsPane.getChildren().setAll(newContent);
                        break;
                    case "Write the Code":
                        /* root = FXMLLoader.load(getClass().getResource("../ui/WriteTheCodeQuestionAdd.fxml"));
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue(); */
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue();
                        loader.setLocation(Main.class.getResource(WCAddRoot));
                        newContent = loader.load();
                        addQuestionsPane.getChildren().setAll(newContent);
                        break;
                    case "Fill the Blank":
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue();
                        loader.setLocation(Main.class.getResource(FBAddRoot));
                        newContent = loader.load();
                        addQuestionsPane.getChildren().setAll(newContent);
                        break;
                    case "Find the Error":
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue();
                        loader.setLocation(Main.class.getResource(FEAddRoot));
                        newContent = loader.load();
                        addQuestionsPane.getChildren().setAll(newContent);
                        break;
                    case "Trace Code":
                        language = selectLang.getValue();
                        difficulty = selectDiff.getValue();
                        loader.setLocation(Main.class.getResource(TCAddRoot));
                        newContent = loader.load();
                        addQuestionsPane.getChildren().setAll(newContent);
                        break;
                }
    
            }
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., show an error message)
            e.printStackTrace();
        }

    }

    @FXML
    void onLogoutClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../ui/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onConfirmButton(ActionEvent event)throws IOException, URISyntaxException {
        
        if(newPasswordField.getText().isBlank())
        {
            Alert alert = new Alert(AlertType.ERROR, "Warning, password field cannot be left blank. \nPlease fill the required fields fully.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        }
        else if (!newPasswordField.getText().equals(confirmNewPasswordField.getText()))
        {
            Alert alert = new Alert(AlertType.ERROR, "Warning, password does not match confirm password. \nPlease enter passwords correctly.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        }
        else 
        {
            if (checkIfPasswordValid()) 
            {
                //checks the contents of the password
            }
            else if (!Main.server.changePassword(LandingPageController.userID, newPasswordField.getText(), oldPasswordField.getText())) //if old password is not correct
            {
                Alert alert = new Alert(AlertType.ERROR, "Warning, old password is incorrect.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    //do stuff maybe
                }
            }
            else
            {
                Timer myTimer = new Timer();
                confirmLabel.setText("Password successfully changed!");
                oldPasswordField.clear();
                newPasswordField.clear();
                confirmNewPasswordField.clear();
                myTimer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            confirmLabel.setText("");
                        });
                    }
                }, 5000);
            }
        }
    }

    @FXML
    void onDeleteAccountClicked(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Warning, this action is irreversible.\nAre you sure you want to delete your H.I.V.E. account?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Account");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try 
            {
                Main.server.deleteUser(LandingPageController.userID);
                root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } 
            catch (IOException | URISyntaxException e) 
            {
                System.out.println("Error, couldn't delete account. \n" + e.getMessage());
                e.printStackTrace();
            }
        }
        else if (alert.getResult() == ButtonType.NO)
        {
            alert.close();
        }
    }

    @FXML
    void onTabSelected(ActionEvent event) {
        try {
            questionsAddedLabel1.setText("Questions Added: " + Main.server.getNumberOfWrittenQuestions(LandingPageController.userID));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        try {
            usernameLabel.setText("Username: " + Main.server.getUsername(LandingPageController.userID));
            // Below, we get a runtime error, I guess we return null from dateOfCreation, but the program works, so...
            dateOfCreationLabel.setText("Date of Creation: " + Main.server.getDateOfCreation(LandingPageController.userID));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        ArrayList<Pair<String, Integer> > sortedAdmins = new ArrayList<>();
        String[] adminList = new String[0];
        try {
            adminList = Main.server.getAllAdminIDs();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < adminList.length; i++) {
            try {
                sortedAdmins.add(new Pair<String,Integer>(Main.server.getUsername(adminList[i]), Main.server.getNumberOfWrittenQuestions(adminList[i])));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(sortedAdmins, new Sortbycount());
        try {
            questionsAddedLabel1.setText("Questions Added: " + Main.server.getNumberOfWrittenQuestions(LandingPageController.userID));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onFilterClicked(ActionEvent event) throws IOException, URISyntaxException{
        
        if (viewQType.getValue() == null || viewLang.getValue() == null || viewDiff.getValue() == null)
        {
            Alert alert = new Alert(AlertType.ERROR, "Warning, all of the fields must be filled.\nPlease fill all fields before filtering.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        }
        else
        {
            String[] allQuestionIDs = Main.server.getAllQuestionIDs();
            String type = viewQType.getValue();
            String lang = viewLang.getValue();
            String diff = viewDiff.getValue();
            ArrayList<String> filteredQuestions = new ArrayList<String>();

            Map<String, String> idMap = new HashMap<String, String>();
            idMap.put("JV", "Java");
            idMap.put("PY", "Python");
            idMap.put("E", "Easy");
            idMap.put("M", "Medium");
            idMap.put("H", "Hard");
            idMap.put("All", "All");
            idMap.put("FB", "Fill the Blank");
            idMap.put("MC", "Multiple Choice");
            idMap.put("FE", "Find the Error");
            idMap.put("WC", "Write the Code");
            idMap.put("TC", "Trace Code");
            
            //filters the question list
            for (int i = 0; i < allQuestionIDs.length; i++)
            {
                String currentID = allQuestionIDs[i];
                String currentType = currentID.substring(0, 2);
                String currentLang = currentID.substring(2, 4);
                String currentDiff = currentID.substring(4, 5);

                if (checkIfMatch(type, idMap.get(currentType)) && checkIfMatch(lang, idMap.get(currentLang)) && checkIfMatch(diff, idMap.get(currentDiff)))
                {
                    filteredQuestions.add(allQuestionIDs[i]);
                }
            }
            if (filteredQuestions.isEmpty()) 
            {
                Alert alert = new Alert(AlertType.ERROR, "No such questions exist within database.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    //do stuff maybe
                }
            }
            System.out.println();
            // questionPaneController.setData()
            // Button viewButton;
            int column = 0;
            int row = 0;
            
            GridPane questionGrid = new GridPane();

            for (int i = 0; i < filteredQuestions.size(); i++)
            {
                String currentID = filteredQuestions.get(i);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../ui/AdminQuestionPane.fxml"));
                AnchorPane questionContainer = fxmlLoader.load();
                QuestionPaneController questionPaneController = fxmlLoader.getController();
                questionPaneController.setData(currentID, this);

                questionGrid.add(questionContainer, column, row++);
            }
            questionPane.setContent(questionGrid);
        }
    }

    public void changePane(String questionID, String root) throws IOException, URISyntaxException
    {
        tempId = questionID;

        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane newContent;
        // fxmlLoader.setLocation(getClass().getResource(FBViewRoot));
        // AnchorPane questionContainer = fxmlLoader.load();

        if (questionID.substring(0,2).equals("MC"))
        {
            fxmlLoader.setLocation(getClass().getResource(MCViewRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("FB"))
        {
            fxmlLoader.setLocation(getClass().getResource(FBViewRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("WC"))
        {
            fxmlLoader.setLocation(getClass().getResource(WCViewRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("TC"))
        {
            fxmlLoader.setLocation(getClass().getResource(TCViewRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("FE"))
        {
            fxmlLoader.setLocation(getClass().getResource(FEViewRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
    }

    public boolean checkIfMatch(String desiredString, String currentString) {
        boolean output = false;
        if (desiredString.equals("All"))
        {
            output = true;
        }
        else if (currentString.equals(desiredString))
        {
            output = true;
        }
        return output;
    }

    private boolean checkIfPasswordValid() {
        boolean passwordNotValid = false;
        String password = newPasswordField.getText();
        String errorMsg = "Warning, the following should be fixed: ";
        if (password.length() < 8)
        {
            passwordNotValid = true;
            errorMsg += "\n•Password must contain at least 8 characters.";
        }
        else if (password.length() > 20)
        {
            passwordNotValid = true;
            errorMsg += "\n•Password must contain at most 20 characters.";
        }
        
        boolean[] conditions = new boolean[3]; //1st is for capital char, 2nd is for lower char, 3rd is for numbers, could add 4th for special chars.
        for (int i = 0; i < password.length(); i++)
        {
            if ((password.charAt(i) > 64) && (password.charAt(i) < 91))
            {
                conditions[0] = true;
            }
            else if ((password.charAt(i) > 96) && (password.charAt(i) < 123))
            {
                conditions[1] = true;
            }
            else if ((password.charAt(i) > 47) && (password.charAt(i) < 58))
            {
                conditions[2] = true;
            }
        }

        if (!conditions[0])
        {
            passwordNotValid = true;
            errorMsg += "\n•Password must contain at least one uppercase character.";
        }
        if (!conditions[1])
        {
            passwordNotValid = true;
            errorMsg += "\n•Password must contain at least one lowercase character.";
        }
        if (!conditions[2])
        {
            passwordNotValid = true;
            errorMsg += "\n•Password must contain at least one number \n character between 0-9.";
        }

        if (passwordNotValid)
        {
            passwordNotValid = true;
            Alert alert = new Alert(AlertType.ERROR, errorMsg, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        }
        else
        {
            passwordNotValid = false;
        }
        return passwordNotValid; //since we want to return true for the if condition to run.
    }

    /* public static void setQuestionsAddedLabel(String update)
    {
        questionsAddedLabel.setText(update);
    } */
}