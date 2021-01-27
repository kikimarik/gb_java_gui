package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

import java.io.File;

public class Main extends Application {

    public static final String currentPath = System.getProperty("java.class.path").split(File.pathSeparator)[0];

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMaxWidth(900);
        primaryStage.setMaxHeight(700);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Template Helper");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();

        MainController controller = loader.getController();
        controller.setStage(primaryStage);
    }


    public static void main(String[] args) {

        launch(args);
    }
}
