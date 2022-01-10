package client.network;

public final class ConnectionToServSingleton {

    private static ConnectionToServer connectionToServer;

    private ConnectionToServSingleton(){
    }

    public static ConnectionToServer getConnectionToServer(){
        if(connectionToServer == null){
            connectionToServer = new ConnectionToServer();
        }
        return connectionToServer;
    }
}
