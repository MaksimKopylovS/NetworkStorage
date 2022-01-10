package dto;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploudDTO implements Serializable {
    private static final long serizlizableVersionUID = 5193392663743561680L;

    private String userName;
    private String fileName;
    private byte[] data;

    public String getFileName(){
        return fileName;
    }
    public byte[] getData(){
        return data;
    }
    public FileUploudDTO(Path path, String userName) throws IOException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
