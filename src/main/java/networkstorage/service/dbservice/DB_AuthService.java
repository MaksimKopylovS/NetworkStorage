package networkstorage.service.dbservice;

import java.sql.SQLException;

public interface DB_AuthService {


    void start();
    void stop();

    String getLoginPass(String login, String pass) throws SQLException;
    int registrationBase(String login, String pass) throws SQLException;
    void renameBase(String nameFirst, String nameLast) throws SQLException;
    String getNick(String name) throws SQLException;
}
