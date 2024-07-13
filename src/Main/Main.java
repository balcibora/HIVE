package Main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class Main extends Application {

    public static Server server = new Server();

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../ui/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("H.I.V.E.");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}