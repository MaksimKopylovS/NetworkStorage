package dto;

import java.io.Serializable;

public class InfoString implements Serializable {

    private String message;


    public InfoString(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
