package networkstorage;

import networkstorage.server.NettyServer;

import java.sql.SQLException;

public class ApplicationStartServer {

    public static void main(String [] args) throws SQLException {
        new NettyServer().start();
    }

}
