package networkstorage.service.storage;

import dto.FileInfo;
import dto.FileInfoDTO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StorageService {

    private static final Logger LOGGER = Logger.getLogger(StorageService.class);

    public StorageService() {

    }

    public void createUserStorage(String userName) throws IOException {
        if (!Files.exists(Paths.get(userName))) {
            Files.createDirectory(Paths.get(userName));
        }
    }

    public FileInfoDTO readUserStorage(String userName) {
        if (Files.exists(Paths.get(userName))) {
            ArrayList<FileInfo> fileInfos = new ArrayList<>();
            for (File file : new File(userName).listFiles()) {
                fileInfos.add(new FileInfo(Paths.get(file.getPath())));
            }
            return new FileInfoDTO(fileInfos);
        }
        return null;
    }


}
