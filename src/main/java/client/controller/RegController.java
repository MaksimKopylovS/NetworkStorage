package client.controller;

import client.network.ConnectionToServSingleton;
import dto.RegDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegController implements Initializable {

    @FXML
    public TextField textFieldLogin;
    @FXML
    public PasswordField textFieldPass;
    @FXML
    public Label labelRegOk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionButtonSend(ActionEvent event) {
        ConnectionToServSingleton.getConnectionToServer().sendMessage(
                new RegDTO(textFieldLogin.getText(), textFieldPass.getText())
        );
    }
}
