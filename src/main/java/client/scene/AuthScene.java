package client.scene;

import client.controller.AuthController;
import client.network.ConnectionToServSingleton;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AuthScene extends Application {

    private FXMLLoader loaderAuth;
    private Parent parentAuth;
    private Scene sceneAuth;
    private static AuthController authController;
    private static Stage authStage;

    public static Stage getAuthStage(){
        return AuthScene.authStage;
    }

    public static AuthController getAuthController(){
        return AuthScene.authController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConnectionToServSingleton.getConnectionToServer();

        authStage = new Stage();
        loaderAuth = new FXMLLoader(getClass().getResource("/Auth.fxml"));
        parentAuth = (Parent) loaderAuth.load();
        sceneAuth = new Scene(parentAuth, 250, 170);
        authStage.setScene(sceneAuth);
        authStage.setTitle("Авторизация");
        authController = loaderAuth.getController();
        authStage.show();

        authStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
