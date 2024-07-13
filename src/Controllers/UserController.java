package Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import Main.Main;
import javafx.application.Platform;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class UserController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String tempId;

    @FXML
    private Label addFriendLabel;

    @FXML
    private TextField addUsernameField;

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
    private Button followButton;

    @FXML
    private ScrollPane friendsList;

    @FXML
    private ScrollPane friendsPane;

    @FXML
    private Label highestStreakLabel;

    @FXML
    private ScrollPane largeScoreboard;

    @FXML
    private Button logOutButton;

    @FXML
    private Label mainMenuGreetingText;

    @FXML
    private Label mainMenuPatchNotes;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private ScrollPane questionPane;

    @FXML
    private Label removeFriendLabel;

    @FXML
    private TextField removeUsernameField;

    @FXML
    private ScrollPane scoreboard;

    @FXML
    private Label streakLabel1;
    
    @FXML
    private Label streakLabel2;
    
    @FXML
    private Label streakLabel3;
    
    @FXML
    private Label streakLabel4;
    
    @FXML
    private Label streakLabel5;
    
    @FXML
    private Label streakLabel6;

    @FXML
    private Button unfollowButton;

    @FXML
    private Button userFilterButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ScrollPane veryLargeScoreboard;

    @FXML
    private ChoiceBox<String> viewDiff;

    @FXML
    private ChoiceBox<String> viewLang;

    @FXML
    private ChoiceBox<String> viewQType;

    @FXML
    private AnchorPane viewQuestionPane;

    private ArrayList<Pair<String, Integer> > sortedFriends;

    private String[] viewQuestionTypes = {"All", "Multiple Choice", "Fill the Blank", "Find the Error", "Trace Code", "Write the Code"};
    private String[] viewLangTypes = {"All", "Java", "Python"};
    private String[] viewDiffTypes = {"All", "Easy", "Medium", "Hard"};

    private class Sortbystreak implements Comparator<Pair<String, Integer> > {
        // Method
        // Sorting in ascending order of roll number
        public int compare(Pair<String, Integer> a, Pair<String, Integer> b)
        {
     
            return b.getValue() - a.getValue();
        }
    }

    protected static String MCRoot = "../ui/UserMultipleQuestion.fxml";
    protected static String WCRoot = "../ui/UserWriteTheCode.fxml";
    protected static String FBRoot = "../ui/UserFillTheBlank.fxml";
    protected static String FERoot = "../ui/UserFindTheError.fxml";
    protected static String TCRoot = "../ui/UserTracingCode.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) { //enter the user here, not a new user!!! I guess
        try {
            emailLabel.setText("Email: " + Main.server.getEmail(LandingPageController.userID));
            streakLabel1.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            streakLabel2.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            streakLabel3.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            streakLabel4.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            streakLabel5.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            streakLabel6.setText("Streak: " +  Main.server.getStreak(LandingPageController.userID));
            highestStreakLabel.setText("Highest Streak: " +  Main.server.getHighestStreak(LandingPageController.userID));
            // questionsAddedLabel.setText("Questions added: " + Main.server.getWrittenList(LandingPageController.userID).size());
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
 
        //sorting algorithm for user scoreboard
        ArrayList<Pair<String, Integer> > sortedUsers = new ArrayList<>();
        String[] userList = new String[0];
        try {
            userList = Main.server.getAllUserIDs();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < userList.length; i++) {
            try {
                sortedUsers.add(new Pair<String,Integer>(userList[i], Main.server.getStreak(userList[i])));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(sortedUsers, new Sortbystreak());

        sortedFriends = new ArrayList<>();
        String[] friendList;
        Set<String> friends = new HashSet<>();
        try {
            friends = Main.server.getFriendList(LandingPageController.userID).keySet();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        friendList = new String[friends.size()];
        int j = 0;
        for (String id: friends)
        {
            friendList[j] = id;
            j++;
        }

        for(int i = 0; i < friendList.length; i++) {
            try {
                sortedFriends.add(new Pair<String,Integer>(friendList[i], Main.server.getStreak(friendList[i])));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(sortedFriends, new Sortbystreak());

        viewQType.getItems().addAll(viewQuestionTypes);
        viewLang.getItems().addAll(viewLangTypes);
        viewDiff.getItems().addAll(viewDiffTypes);

        try {
            setScoreboard(sortedUsers);
            setSmallScoreboard(sortedUsers);
            setLargeScoreboard(sortedUsers);
            setFriendList(sortedFriends);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LocalTime currentTime = LocalTime.now();
        int time = currentTime.getHour();
        if (time >= 0 && time <= 4) 
        {
            mainMenuGreetingText.setText("So, you are grinding coding questions... Happy coding.\nRemember to get some sleep.");
        }
        else if (time > 4 && time <= 8) 
        {
            mainMenuGreetingText.setText("Good morning!\nDid you sleep or are you still \ngrinding the questions?");
        }
        else if (time > 8 && time <= 12) 
        {
            mainMenuGreetingText.setText("Good morning!");
        }
        else if (time > 12 && time <= 16) 
        {
            mainMenuGreetingText.setText("Good afternoon!\nKeep up the good work!");
        }
        else if (time > 16 && time <= 20) 
        {
            mainMenuGreetingText.setText("Good evening.");
        }
        else
        {
            mainMenuGreetingText.setText("Good night.\nJust kidding, coders don't sleep.");
        }
    }

    public void setScoreboard(ArrayList<Pair<String, Integer> > sortedUsers) throws IOException, URISyntaxException {
        int column = 0;
        int row = 0;

        GridPane scoreboardGrid = new GridPane();

        for (int i = 0; i < sortedUsers.size(); i++)
        {
            String name = Main.server.getUsername(sortedUsers.get(i).getKey());
            String streak = Main.server.getStreak(sortedUsers.get(i).getKey()) + ""; //might be backwards
            String qSolved = Main.server.getSolvedQuestions(sortedUsers.get(i).getKey()).size() + ""; 
            // AnchorPane adminScoreboard = null; //temp
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/UserLargeScoreboard.fxml"));
            // fxmlLoader.load();
            AnchorPane userScoreboard = fxmlLoader.load();
            UserLargeScoreboardController userLargeScoreboardController = new UserLargeScoreboardController();
            userLargeScoreboardController = fxmlLoader.getController();
            userLargeScoreboardController.setData(name, streak, qSolved);

            scoreboardGrid.add(userScoreboard, column, row++);
        }
        largeScoreboard.setContent(scoreboardGrid);
    }

    public void setSmallScoreboard(ArrayList<Pair<String, Integer> > sortedUsers) throws IOException, URISyntaxException {
        int column = 0;
        int row = 0;

        GridPane scoreboardGrid= new GridPane();

        for (int i = 0; i < sortedUsers.size(); i++)
        {
            String name = Main.server.getUsername(sortedUsers.get(i).getKey());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/UserSmallScoreboard.fxml"));
            AnchorPane userScoreboard = fxmlLoader.load();
            UserSmallScoreboardController userSmallScoreboardController = new UserSmallScoreboardController();
            userSmallScoreboardController = fxmlLoader.getController();
            userSmallScoreboardController.setData(name);

            scoreboardGrid.add(userScoreboard, column, row++);
        }
        scoreboard.setContent(scoreboardGrid);
    }

    public void setLargeScoreboard(ArrayList<Pair<String, Integer> > sortedUsers) throws IOException, URISyntaxException {
        int column = 0;
        int row = 0;

        GridPane scoreboardGrid = new GridPane();

        for (int i = 0; i < sortedUsers.size(); i++)
        {
            int rankInt = i + 1;
            String rank = rankInt + "";
            String name = Main.server.getUsername(sortedUsers.get(i).getKey());
            String streak = Main.server.getStreak(sortedUsers.get(i).getKey()) + ""; //might be backwards
            String qSolved = Main.server.getSolvedQuestions(sortedUsers.get(i).getKey()).size() + ""; 
            // AnchorPane adminScoreboard = null; //temp
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/VeryLargeScoreboard.fxml"));
            // fxmlLoader.load();
            AnchorPane userScoreboard = fxmlLoader.load();
            VeryLargeScoreboardController veryLargeScoreboardController = new VeryLargeScoreboardController();
            veryLargeScoreboardController = fxmlLoader.getController();
            veryLargeScoreboardController.setData(rank, name, streak, qSolved);

            scoreboardGrid.add(userScoreboard, column, row++);
        }
        veryLargeScoreboard.setContent(scoreboardGrid);
    }

    public void setFriendList(ArrayList<Pair<String, Integer> > sortedFriends) throws IOException, URISyntaxException {
        int column = 0;
        int row = 0;

        GridPane friendListGrid = new GridPane();

        for (int i = 0; i < sortedFriends.size(); i++)
        {
            String name = Main.server.getUsername(sortedFriends.get(i).getKey());
            String streak = "" + sortedFriends.get(i).getValue(); //might be backwards
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../ui/FriendList.fxml"));
            AnchorPane friendList = fxmlLoader.load();
            FriendListController friendListController = fxmlLoader.getController();
            friendListController.setData(name, streak);

            friendListGrid.add(friendList, column, row++);
        }
        friendsList.setContent(friendListGrid);
    }

    @FXML
    void onLogoutClicked(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../ui/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onConfirmButton(ActionEvent event) throws IOException, URISyntaxException {
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
            else if (!Main.server.changePassword(LandingPageController.userID, newPasswordField.getText(), oldPasswordField.getText()))
            {
                Alert alert = new Alert(AlertType.ERROR, "Warning, old password is incorrect.", ButtonType.OK);
                alert.showAndWait();
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

    public boolean checkIfMatch(String desiredString, String currentString)
    {
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
            
            // filters the question list
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

        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane newContent;
        if (questionID.substring(0,2).equals("MC"))
        {
            tempId = questionID;
            fxmlLoader.setLocation(getClass().getResource(MCRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("FB"))
        {
            tempId = questionID;
            fxmlLoader.setLocation(getClass().getResource(FBRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("WC"))
        {
            tempId = questionID;
            fxmlLoader.setLocation(getClass().getResource(WCRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("TC"))
        {
            tempId = questionID;
            fxmlLoader.setLocation(getClass().getResource(TCRoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
        else if (questionID.substring(0,2).equals("FE"))
        {
            tempId = questionID;
            fxmlLoader.setLocation(getClass().getResource(FERoot));
            newContent = fxmlLoader.load();
            viewQuestionPane.getChildren().setAll(newContent);
        }
    }

    /* public void changePane(String root) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane newContent;
        loader.setLocation(Main.class.getResource(root));
        newContent = loader.load();
        viewQuestionPane.getChildren().setAll(newContent);
    } */

    @FXML
    void onFollowClicked(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {
        if (!addUsernameField.getText().isBlank())
        {
            try {
                
                // if (Main.server.getFriendList(LandingPageController.userID))
                
                Main.server.addFriend(LandingPageController.userID, addUsernameField.getText());
                Timer myTimer = new Timer();
                
                myTimer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            sortedFriends = new ArrayList<>();
                            String[] friendList;
                            Set<String> friends = new HashSet<>();
                            try {
                                friends = Main.server.getFriendList(LandingPageController.userID).keySet();
                            } catch (URISyntaxException | IOException e) {
                                e.printStackTrace();
                            }
                            friendList = new String[friends.size()];
                            int j = 0;
                            for (String id: friends)
                            {
                                friendList[j] = id;
                                j++;
                            }
            
                            for(int i = 0; i < friendList.length; i++) {
                                try {
                                    sortedFriends.add(new Pair<String,Integer>(friendList[i], Main.server.getStreak(friendList[i])));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(sortedFriends, new Sortbystreak());
                            addUsernameField.clear();
                            try {
                                setFriendList(sortedFriends);
                            } catch (IOException | URISyntaxException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }, 1000);
                addFriendLabel.setText("Friend added!");
                myTimer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            addFriendLabel.setText("");
                        });
                    }
                }, 5000);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "Error, couldn't find friend ID. Couldn't add friend.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) 
                {
                    //do stuff maybe
                }
            }
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, friend ID field cannot be blank.\nPlease fill the field before proceeding.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                //do stuff maybe
            }
        }
        
    }

    @FXML
    void onUnfollowClicked(ActionEvent event) throws IOException, InterruptedException {
        if (!removeUsernameField.getText().isBlank())
        {
            try {
                if(Main.server.removeFriend(LandingPageController.userID, removeUsernameField.getText()).equals("false")) {
                    throw new IOException();
                }
                Timer myTimer = new Timer();
                myTimer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            sortedFriends = new ArrayList<>();
                            String[] friendList;
                            Set<String> friends = new HashSet<>();
                            try {
                                friends = Main.server.getFriendList(LandingPageController.userID).keySet();
                            } catch (URISyntaxException | IOException e) {
                                e.printStackTrace();
                            }
                            friendList = new String[friends.size()];
                            int j = 0;
                            for (String id: friends)
                            {
                                friendList[j] = id;
                                j++;
                            }
            
                            for(int i = 0; i < friendList.length; i++) {
                                try {
                                    sortedFriends.add(new Pair<String,Integer>(friendList[i], Main.server.getStreak(friendList[i])));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(sortedFriends, new Sortbystreak());
                            addUsernameField.clear();
                            try {
                                setFriendList(sortedFriends);
                            } catch (IOException | URISyntaxException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }, 1000);
                removeFriendLabel.setText("Friend removed!");
                removeUsernameField.clear();
                setFriendList(sortedFriends);
                myTimer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            removeFriendLabel.setText("");
                        });
                    }
                }, 5000);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "Error, couldn't find friend ID. Couldn't remove friend.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) 
                {
                    //do stuff maybe
                }
            }
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR, "Error, friend ID field cannot be blank.\nPlease fill the field before proceeding.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                //do stuff maybe
            }
        }
    }

}
