package client.scene;

import client.controller.ControllerStorage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StorageScene extends Application {

    private FXMLLoader loaderStorage;
    private Parent parentStorage;
    private static Scene sceneStorage;
    private static ControllerStorage controllerStorage;
    private static Stage stageStorage;

    public static Stage getStageStorage(){
        return StorageScene.stageStorage;
    }

    public static ControllerStorage getControllerStorage() {
        return StorageScene.controllerStorage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageStorage = new Stage();
        loaderStorage = new FXMLLoader(getClass().getResource("/Client.fxml"));
        parentStorage = (Parent)loaderStorage.load();
        sceneStorage = new Scene(parentStorage,640,480);
        stageStorage.setScene(sceneStorage);
        stageStorage.setTitle("Network Storage");
        controllerStorage = loaderStorage.getController();

        stageStorage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
