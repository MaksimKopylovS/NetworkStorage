package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class FileInfoDTO implements Serializable {

    private static final long serializableVersionUID = 5193392663743561680L;

    private ArrayList<FileInfo> fileInfoArrayList;
    private String userName;

    public FileInfoDTO(ArrayList<FileInfo> fileInfoArrayList){
        this.fileInfoArrayList = fileInfoArrayList;
    }

    public FileInfoDTO(String userName){
        this.userName = userName;
    }

    public FileInfoDTO(ArrayList<FileInfo> fileInfoArrayList, String userName){
        this.fileInfoArrayList = fileInfoArrayList;
        this.userName = userName;
    }

    public ArrayList<FileInfo> getFileInfoArrayList(){
        return fileInfoArrayList;
    }

    public String getUserName() {
        return userName;
    }
}
