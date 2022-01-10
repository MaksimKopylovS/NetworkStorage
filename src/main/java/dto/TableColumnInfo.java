package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableColumnInfo implements Serializable {
    private static final long serializableVersionUID = 5193392663743561680L;

    List<String> listType = new ArrayList<>();
    List<String> listPath = new ArrayList<>();
    List<Long> listSize = new ArrayList<>();
    List<String> listDataTime = new ArrayList<>();

    public TableColumnInfo(){

    }

    public TableColumnInfo(List<String> listType, List<String> listPath, List<Long> listSize, List<String> listDataTime){
        this.listType = listType;
        this.listPath = listPath;
        this.listSize = listSize;
        this.listDataTime = listDataTime;
    }

    public List<String> getListType(){
        return listType;
    }

    public List<String> getListPath(){
        return listPath;
    }

    public List<Long> getListSize(){
        return listSize;
    }

    public List<String> getListlistDataTime(){
        return listDataTime;
    }

    public void setListPath(String path){
        listPath.add(path);
    }

}
