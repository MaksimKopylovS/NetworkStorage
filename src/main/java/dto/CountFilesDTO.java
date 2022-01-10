package dto;

import java.io.Serializable;

public class CountFilesDTO implements Serializable {

    private int count;
    private String fileName;
    private String userName;
    public int getCount(){
        return count;
    }
    public String getFileName(){
        return fileName;
    }

    public CountFilesDTO(){

    }

    public CountFilesDTO(int count, String fileName, String userName){
        this.count = count;
        this.fileName = fileName;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
