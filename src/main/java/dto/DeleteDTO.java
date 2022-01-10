package dto;

import java.io.Serializable;

public class DeleteDTO implements Serializable {

    private String userName;
    private String fileName;

    public DeleteDTO(String userName, String fileName) {
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
