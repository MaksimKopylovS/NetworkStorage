package client.controller;

import client.handler.MessageHandler;
import client.scene.StorageScene;
import client.network.ConnectionToServSingleton;
import dto.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ControllerStorage implements Initializable {

    private ObservableList<ColumnInfo> folderData = FXCollections.observableArrayList();

    @FXML
    private TableView<ColumnInfo> tableViewFile;

    @FXML
    private TableColumn<ColumnInfo, String> fileTypeColumn;

    @FXML
    private TableColumn<ColumnInfo, String> filePathColumn;

    @FXML
    private TableColumn<ColumnInfo, Long> fileSizeColumn;

    @FXML
    private TableColumn<ColumnInfo, String> fileDataColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType().toString()));
        filePathColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPath().toString()));
        fileSizeColumn.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getSize()));
        fileDataColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataTipe().toString()));
        tableViewFile.setItems(folderData);
    }

    public void menuItemActionSendFile(ActionEvent event) throws IOException {
        File file = new FileChooser().showOpenDialog(StorageScene.getStageStorage());
        ConnectionToServSingleton.getConnectionToServer().fileUploud(file);
    }

    public void btnActionExit(ActionEvent event) {

    }

    public void actionButtonDownloads(ActionEvent event) {
        if(tableViewFile.isFocused()) {
            ConnectionToServSingleton.getConnectionToServer().sendMessage(
                    new FileDownloadsDTO(
                            MessageHandler.getUserName(), tableViewFile.getSelectionModel().getSelectedItem().getPath()
                    )
            );
        }
    }

    public void actionButtonUpdatePath(ActionEvent event) {
        ConnectionToServSingleton.getConnectionToServer().sendMessage(new FileInfoDTO(MessageHandler.getUserName()));
    }
    
    public void btnActionDelete(ActionEvent event) {
        if(tableViewFile.isFocused()){
            System.out.println(tableViewFile.getSelectionModel().getSelectedItem().getPath());
            System.out.println(new DeleteDTO( tableViewFile.getSelectionModel().getSelectedItem().getPath(),MessageHandler.getUserName()).getFileName());
            ConnectionToServSingleton.getConnectionToServer().sendMessage(
                    new DeleteDTO(
                            MessageHandler.getUserName(),tableViewFile.getSelectionModel().getSelectedItem().getPath()
                    )
            );

        }

    }

    public void initData(FileInfoDTO fileInfoDTO) {
        folderData.clear();
        for (FileInfo info : fileInfoDTO.getFileInfoArrayList()) {

            folderData.add(new ColumnInfo(info.getType().getName(),
                    info.getPath(),
                    info.getSize(),
                    info.getLastModified().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        }
    }

    public void actionButtonUser(ActionEvent event) {
    }
}
