package client.controller;

import client.scene.RegScene;
import client.network.ConnectionToServSingleton;
import dto.AuthDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Label labelInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void actionReg(ActionEvent event) {
        System.out.println("Событие регистрации");
        RegScene.getAuthStage().show();
    }

    @FXML
    public void actionSend(ActionEvent event) {
        ConnectionToServSingleton.getConnectionToServer().sendMessage(new AuthDTO(loginTextField.getText(),passTextField.getText()));
    }

    public void setTextLabelInfo(String str){
        labelInfo.setText(str);
    }

}
