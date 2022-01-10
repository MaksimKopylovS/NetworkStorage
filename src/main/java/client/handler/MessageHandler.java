package client.handler;

import client.scene.AuthScene;
import client.scene.RegScene;
import client.scene.StorageScene;
import dto.*;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MessageHandler {

    private static String userName;
    private static final String DOWNLOADS = "Downloads";
    private static final String DOWNLOADS_CACH = "DownloadsCash";

    public MessageHandler(ChannelHandlerContext ctx, Object msg) throws IOException {
        authentication(msg);
        errorMessage(msg);
        checkFileInfoDTO(msg);
        handlerFile(msg);
        infoStringHandler(msg);
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MessageHandler.userName = userName;
    }

    public void authentication(Object msg) {
        if (msg instanceof AuthDTO) {
            AuthDTO authDTO = (AuthDTO) msg;
            Platform.runLater(() -> AuthScene.getAuthStage().close());
            Platform.runLater(() -> StorageScene.getStageStorage().show());
            Platform.runLater(() -> StorageScene.getStageStorage().setTitle("Storage " + authDTO.getLogin()));
            setUserName(authDTO.getLogin());
        }
    }

    public void checkFileInfoDTO(Object msg) {
        if (msg instanceof FileInfoDTO) {
            FileInfoDTO fileInfoDTO = (FileInfoDTO) msg;
            Platform.runLater(() -> StorageScene.getControllerStorage().initData(fileInfoDTO));
        }
    }

    public void errorMessage(Object msg) {
        if (msg instanceof ErrorDTO) {
            ErrorDTO errorDTO = (ErrorDTO) msg;
            Platform.runLater(() -> AuthScene.getAuthController().setTextLabelInfo(errorDTO.getError()));
        }
    }

    public void handlerFile(Object msg) throws IOException {
        System.out.println("handlerFile " + msg.getClass());
        if (msg instanceof FileUploudDTO) {
            FileUploudDTO fileUploudDTO = (FileUploudDTO) msg;
            if (!Files.exists(Paths.get(DOWNLOADS_CACH))) {
                Files.createDirectory(Paths.get(DOWNLOADS_CACH));
            }
            Files.write(Paths.get(DOWNLOADS_CACH + "/" + fileUploudDTO.getFileName()), fileUploudDTO.getData(), StandardOpenOption.CREATE);
        }

        if (msg instanceof CountFilesDTO) {
            CountFilesDTO countFilesDTO = (CountFilesDTO) msg;

            if (!Files.exists(Paths.get(DOWNLOADS))) {
                Files.createDirectory(Paths.get(DOWNLOADS));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(DOWNLOADS + "/" + countFilesDTO.getFileName());

            if (Files.exists(Paths.get(DOWNLOADS + "/" + countFilesDTO.getFileName()))) {
                fileOutputStream = new FileOutputStream(DOWNLOADS + "/" + countFilesDTO.getFileName());
            }

            for (int i = 0; i < countFilesDTO.getCount(); i++) {
                System.out.println("Record");
                Files.copy(Paths.get(DOWNLOADS_CACH + "/" +
                        i), fileOutputStream);
            }
            System.out.println("Файл " + countFilesDTO.getFileName() + " Записан");

            for (File file : new File(DOWNLOADS_CACH).listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    public void infoStringHandler(Object msg) {
        if (msg instanceof InfoString) {
            InfoString infoString = (InfoString) msg;
            Platform.runLater(() -> RegScene.getAuthController().labelRegOk.setText(infoString.getMessage()));
        }
    }
}
