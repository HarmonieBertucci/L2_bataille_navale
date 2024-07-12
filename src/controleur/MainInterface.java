package controleur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * classe main pour executer le code
 */
public class MainInterface extends Application {
    /**
     * main
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {}

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Overlay.fxml"));
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Bataille Navale");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
