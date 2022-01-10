package networkstorage.handler;

import dto.*;
import io.netty.channel.ChannelHandlerContext;
import networkstorage.service.clientservice.ClientService;
import networkstorage.service.dbservice.BaseAuthService;
import networkstorage.service.storage.StorageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

public class MessageHandler {

    private ChannelHandlerContext channel;
    private StorageService storageService;
    private static String fileCacheUser = "ServerFileCache";


    public MessageHandler(ChannelHandlerContext ctx, Object msg, ClientService clientService) throws SQLException, IOException {
        storageService = new StorageService();
        this.channel = ctx;
        authentification(msg,clientService);
        handlerFile(msg);
        updateFileInfo(msg);
        deleteDTOHandler(msg);
        fileDownloadsDTOHandler(msg);
        registration(msg);
    }

    public MessageHandler(ChannelHandlerContext ctx, Object msg) {

    }

    public String checkedAuthDTO(Object o) throws SQLException {
        if(o instanceof AuthDTO){
            AuthDTO authDTO = (AuthDTO) o;
            return BaseAuthService.authLoginPass(authDTO.getLogin(), authDTO.getPassword());
        }
        return null;
    }

    public boolean authentification(Object msg, ClientService clientService) throws SQLException, IOException {
        if(msg instanceof AuthDTO) {
            if (msg != null) {
                String name = checkedAuthDTO(msg);
                if (ClientService.checName(name)) {
                    ClientService.addNameToListName(name);
                    ClientService.addClientServiceList(clientService);
                    clientService.setClientName(name);
                    System.out.println("Пользователь " + name + " Добавлен " + clientService);
                    storageService.createUserStorage(name);
                    sendMessage(new AuthDTO(name));
                    sendMessage(storageService.readUserStorage(name));
                    return true;
                } else {
                    System.out.println("Пользователь " + name + " Уже вошел" + clientService);
                    sendMessage(new ErrorDTO("Учетная запись уже занаята"));
                    return false;
                }
            } else {
                System.out.println("Не верный логин или пароль");
                sendMessage(new ErrorDTO("Не верный логин или пароль"));
                return false;
            }
        }
        return false;
    }

    public void sendMessage(Object o) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(o);
                System.out.println("Отправленно " + o.getClass());
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void handlerFile(Object msg) throws IOException {
        if(msg instanceof FileUploudDTO){
            FileUploudDTO fileUploudDTO = (FileUploudDTO) msg;
            if(!Files.exists(Paths.get(fileCacheUser))){
                Files.createDirectory(Paths.get(fileCacheUser));
            }
            if(!Files.exists(Paths.get(fileCacheUser +"/"+fileUploudDTO.getUserName()))){
                Files.createDirectory(Paths.get(fileCacheUser +"/"+fileUploudDTO.getUserName()));
            }

            FileUploudDTO fileMessage = (FileUploudDTO)msg;
            Files.write(Paths.get(fileCacheUser+"/"+fileUploudDTO.getUserName()+"/"+fileMessage.getFileName()), fileMessage.getData(), StandardOpenOption.CREATE);
        }

        if (msg instanceof CountFilesDTO){
            CountFilesDTO countFilesDTO = (CountFilesDTO) msg;
            System.out.println(countFilesDTO.getCount() + " " + countFilesDTO.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(countFilesDTO.getUserName()+"/" +
                    countFilesDTO.getFileName());
            if (Files.exists(Paths.get(countFilesDTO.getUserName()+"/" +countFilesDTO.getFileName()))){
                fileOutputStream = new FileOutputStream(countFilesDTO.getUserName()+"/" +countFilesDTO.getFileName());
            }
            for (int i = 0; i < countFilesDTO.getCount(); i++){
                System.out.println("Record");
                Files.copy(Paths.get(fileCacheUser + "/" +
                        countFilesDTO.getUserName()+
                        "/"+i), fileOutputStream);
            }
            System.out.println("Файл " + countFilesDTO.getFileName() + " Записан");

            for (File file : new File(fileCacheUser+"/"+ countFilesDTO.getUserName()).listFiles()){
                if(file.isFile()){
                    file.delete();
                }
            }
        }
    }

    public void updateFileInfo(Object msg) throws IOException {
        if(msg instanceof FileInfoDTO){
            FileInfoDTO fileInfoDTO = (FileInfoDTO) msg;
            sendMessage(storageService.readUserStorage(fileInfoDTO.getUserName()));
        }
    }

    public void deleteDTOHandler(Object msg) throws IOException {
        System.out.println(" deleteDTOHandler " + msg.getClass());
        if (msg instanceof DeleteDTO){
            DeleteDTO deleteDTO = (DeleteDTO) msg;
            if (Files.exists(Paths.get(deleteDTO.getFileName()))){
                Files.delete(Paths.get(deleteDTO.getFileName()));
            }else {
                System.out.println("НЕ СУЩЕСТВУЕТ " + Paths.get(deleteDTO.getFileName()));
            }
        }
    }

    public void fileDownloadsDTOHandler(Object msg) throws IOException {
        if(msg instanceof FileDownloadsDTO){
            FileDownloadsDTO fileDownloadsDTO = (FileDownloadsDTO) msg;
            if(Files.exists(Paths.get(fileDownloadsDTO.getFileName()))){
                fileUploud(fileDownloadsDTO);
            }else {
                System.out.println("НЕ СУЩЕСТВУЕТ " + Paths.get(fileDownloadsDTO.getFileName()));
            }
        }
    }

    public void fileUploud(FileDownloadsDTO fileDownloadsDTO) throws IOException {
        int partCount = 0;
        int sizeOfFile = 1024 * 1024 * 10;
        final byte[] buffer = new byte[sizeOfFile];
        final String fileName = Paths.get(fileDownloadsDTO.getFileName()).getFileName().toString();

        try(FileInputStream fis = new FileInputStream(fileDownloadsDTO.getFileName());
            BufferedInputStream bis = new BufferedInputStream(fis)){
            int bytesAmount = 0;

            if(!Files.exists(Paths.get(fileCacheUser))){
                Files.createDirectory(Paths.get(fileCacheUser));
            }

            while ((bytesAmount = bis.read(buffer)) > 0){
                try (FileOutputStream out = new FileOutputStream(fileCacheUser+"/" + partCount)){
                    out.write(buffer, 0, bytesAmount);
                    Path path = Paths.get(fileCacheUser+ "/" + partCount);

                    Object o = new FileUploudDTO(path, client.handler.MessageHandler.getUserName());
                    sendMessage(o);
                    System.out.println("Файл отправлен - " + partCount);
                    partCount++;
                }
            }
            Object o = new CountFilesDTO(partCount, fileName, client.handler.MessageHandler.getUserName());
            System.out.println("Send partCount");
            sendMessage(o);
            for(File myFile : new File(fileCacheUser).listFiles()){
                if(myFile.isFile()){
                    myFile.delete();
                }
            }
        }
    }

    public void registration(Object msg) throws SQLException {
        if (msg instanceof RegDTO){
            RegDTO regDTO = (RegDTO) msg;
            if (new BaseAuthService().registrationBase(regDTO.getUserName(), regDTO.getPassword()) == 1){
                sendMessage(new InfoString("Пользователь "+ regDTO.getUserName() + " успешно зарегестрировался "));
            }
        }
    }
}
