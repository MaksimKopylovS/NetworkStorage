package dto;

import java.io.Serializable;

public class AuthDTO implements Serializable {

    private static final long serializableVersionUID = 5193392663743561680L;

    private String login;
    private String password;

    public AuthDTO(String login, String password){
        this.login = login;
        this.password = password;
    }

    public AuthDTO(String login){
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword(){
        return password;
    }

}
