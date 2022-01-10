package dto;

public class ColumnInfo {

    private String type;
    private String path;
    private Long size;
    private String dataTipe;

    public ColumnInfo(String type, String path, Long size, String dataTipe) {
        this.type = type;
        this.path = path;
        this.size = size;
        this.dataTipe = dataTipe;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public Long getSize() {
        return size;
    }

    public String getDataTipe() {
        return dataTipe;
    }
}
