package dto;

import java.io.Serializable;

public class RegDTO implements Serializable {

    private String userName;
    private String password;

    public RegDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
