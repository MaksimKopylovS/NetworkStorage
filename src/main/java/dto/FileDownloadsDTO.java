package dto;

import java.io.Serializable;

public class FileDownloadsDTO implements Serializable {

    private String userName;
    private String fileName;

    public FileDownloadsDTO(String userName, String fileName) {
        this.userName = userName;
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public String getFileName() {
        return fileName;
    }
}
