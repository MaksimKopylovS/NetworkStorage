package client;

import client.scene.AuthScene;
import client.scene.RegScene;
import client.scene.StorageScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationClientStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new AuthScene().start(primaryStage);
        new StorageScene().start(primaryStage);
        new RegScene().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
