package networkstorage.service.dbservice;

import networkstorage.database.DB_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAuthService implements DB_AuthService {

    private String login;
    private String pass;
    private static ResultSet resultSet;

    @Override
    public void start() {
        System.out.println("Сервис Аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис Аутентификации остановлен");
    }

    @Override
    public String getLoginPass(String login, String pass) throws SQLException {
        return null;
    }

    @Override
    public int registrationBase(String login, String pass) throws SQLException {
        this.login = login;
        this.pass = pass;

        int addBase = DB_Connect.getInstance()
                .connection
                .createStatement()
                .executeUpdate(
                        "INSERT INTO" +
                                " `net_storage`.`user`(`name`,`password`)" +
                                " VALUE " +
                                "('"+ this.login + "', '"+ this.pass + "');");
        return addBase;
    }

    public static String authLoginPass(String login, String pass) throws SQLException {
        resultSet = DB_Connect.getInstance().connection.createStatement().executeQuery("SELECT * FROM `user`;");
        String strLogin;
        String strPass;
        while (resultSet.next()){
            strLogin = resultSet.getString("name");
            strPass = resultSet.getString("password");
            if(strLogin.equals(login) && strPass.equals(pass)){
                return strLogin;
            }
        }
        return null;
    }

    @Override
    public void renameBase(String nameFirst, String nameLast) throws SQLException {

    }

    @Override
    public String getNick(String name) throws SQLException {
        return null;
    }
}
