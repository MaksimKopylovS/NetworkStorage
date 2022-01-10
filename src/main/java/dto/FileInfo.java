package dto;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo implements Serializable {

    private static final long serializableVersionUID = 5193392663743561680L;

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public enum FileType {
        FILE("F"), DIRECTIRY("D");

        private String name;

        FileType(String name) {
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }

    private FileType type;
    private String fileName;
    private LocalDateTime lastModified;
    private String path;
    private long size;

    public FileInfo(Path path){

        try {
            this.fileName = path.getFileName().toString();
            this.path = path.toString();
            this.size = Files.size(path);
            this.type = Files.isDirectory(path) ? FileType.DIRECTIRY : FileType.FILE;
            //Получение времени создания файла
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to create file info from path");
        }
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

}

