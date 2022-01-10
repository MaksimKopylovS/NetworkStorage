package client.scene;

import client.controller.RegController;
import client.network.ConnectionToServSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegScene extends Application {

    private FXMLLoader loaderReg;
    private Parent parentReg;
    private Scene sceneReg;
    private static RegController regController;
    private static Stage regStage;

    public static Stage getAuthStage(){
        return RegScene.regStage;
    }

    public static RegController getAuthController(){
        return RegScene.regController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConnectionToServSingleton.getConnectionToServer();

        regStage = new Stage();
        loaderReg = new FXMLLoader(getClass().getResource("/Reg.fxml"));
        parentReg = (Parent) loaderReg.load();
        sceneReg = new Scene(parentReg, 250, 170);
        regStage.setScene(sceneReg);
        regStage.setTitle("Регистрация");
        regController = loaderReg.getController();
    }
}


