package Controllers;
import java.io.IOException;
import java.net.URISyntaxException;

import Accounts.User;
import Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LandingPageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    protected static String userID;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField newUsernameField;

    @FXML
    private Button signUpButton;
    

    /**
     * Works in the Login scene, used to enter the program
     * @param event
     * @throws IOException 
     */
    @FXML
    void onLoginClicked(MouseEvent event) throws IOException {
        boolean userIsAdmin = false;
        boolean accountExists = true;
        //should probably check to see if the strings are not blank

        try 
        {
            accountExists = Main.server.usernameAlreadyExists(usernameField.getText());

            if(accountExists)
            {
                try {
                    userID = Main.server.login(usernameField.getText(), passwordField.getText());
                    userIsAdmin = Main.server.isAdmin(userID);
                    if(userIsAdmin)
                    {
                        root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else //forward to other landing page
                    {
                        // System.out.println("welcome to user page.");
                        root = FXMLLoader.load(getClass().getResource("../ui/User.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR, "Warning, the given password do not match the account.\nPlease try again.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff maybe
                    }
                }
            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR, "Warning, the given account could not be found.\nPlease try again.", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    //do stuff maybe
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("some sort of login error.");
            Alert alert = new Alert(AlertType.ERROR, "Warning, the given account (username) could not be found.\nPlease try again.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                //do stuff maybe
            }
        }
        /* root = FXMLLoader.load(getClass().getResource("../ui/Admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); */
    }

    /**
     * Works in the Signup scene, used to create an Account
     * @param event
     * @throws IOException 
     */
    @FXML
    void onSignUpClicked(MouseEvent event) throws IOException {
        // Will check whether the account is created in the correct way.
        if (true)
        {
            signUpToLogin(event);
        }
    }

    /**
     * When the Sign up button is clicked, scene is changed to Signup.
     * @param event
     * @throws IOException
     */
    @FXML
    void loginToSignUp(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../ui/Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When the Login button is clicked, scene is changed to Login.
     * @param event
     * @throws IOException
     */
    @FXML
    void signUpToLogin(MouseEvent event) throws IOException {
        if(newPasswordField.getText().isBlank())
        {
            Alert alert = new Alert(AlertType.ERROR, "Warning, password field cannot be left blank. \nPlease fill the required fields fully.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        }
        else if(newUsernameField.getText().isBlank())
        {
            Alert alert = new Alert(AlertType.ERROR, "Warning, username field cannot be left blank. \nPlease fill the required fields fully.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff maybe
            }
        } 
        else
        {
            try {
                if(Main.server.usernameAlreadyExists(newUsernameField.getText()))
                {
                    Alert alert = new Alert(AlertType.ERROR, "Warning, username is taken. \nPlease choose another username.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff maybe
                    }
                }
                else if(emailField.getText().isBlank())
                {
                    Alert alert = new Alert(AlertType.ERROR, "Warning, email field cannot be left blank. \nPlease fill the required fields fully.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff maybe
                    }
                }
                else if(!emailField.getText().contains("@"))
                {
                    Alert alert = new Alert(AlertType.ERROR, "Warning, email address invalid. Must contain '@' character.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff maybe
                    }
                }
                else if (!newPasswordField.getText().equals(confirmPasswordField.getText()))
                {
                    Alert alert = new Alert(AlertType.ERROR, "Warning, password does not match confirm password. \nPlease enter passwords correctly.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff maybe
                    }
                }
                else if (checkIfPasswordValid()) 
                {
                    //check the contents of the password
                }
                else
                {
                    User user = new User(newUsernameField.getText(), emailField.getText(), false, newPasswordField.getText());
                    user.setId("1");
                    try 
                    {
                        //add to database
                        user.setId("U" + Main.server.getTotalAccountCount());
                        System.out.println(user.toString());
                        Main.server.addUser(user);
                    } 
                    catch (IOException e) 
                    {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    } 
                    catch (URISyntaxException e) 
                    {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    } 

                    root = FXMLLoader.load(getClass().getResource("../ui/Login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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

}
