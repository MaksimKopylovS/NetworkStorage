package networkstorage.service.clientservice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import networkstorage.handler.MessageHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.ArrayList;

public class ClientService extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ClientService.class);
    private static ArrayList<String> listName;
    private static ArrayList<ClientService> clientServiceList;
    private String clientName;

    public ClientService(){
        clientName = "user";
    }

    public static void initClientServiceArrayList(){
        listName = new ArrayList<>();
        clientServiceList = new ArrayList<>();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.log(Level.INFO, "Клиент " +" " + ctx.channel().toString() + " подключился");
        System.out.println("Клиент " +" " + this + " подключился");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        new MessageHandler(ctx, msg, this);
        System.out.println(clientName + " Пишет " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.log(Level.INFO, cause.getLocalizedMessage());
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("Пользовател "+  clientName +" отвалился " + this + "\n" + ctx.channel());
        delClientServiceList(this);
        delNameToListName(clientName);
    }

    public static boolean checName(String name) {
        for (String nick : listName) {
            if (nick.equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static void addNameToListName(String name){
        listName.add(name);
    }

    public static void addClientServiceList(ClientService clientService){
        clientServiceList.add(clientService);
    }

    public static void delNameToListName(String name){
        listName.remove(name);
    }

    public static void delClientServiceList(ClientService clientService){
        clientServiceList.remove(clientService);
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
