package dto;

import java.io.Serializable;

public class ErrorDTO implements Serializable {

    private static final long serializableVersionUID = 5193392663743561680L;

    private String error;

    public ErrorDTO(String error){
        this.error = error;
    }

    public String getError(){
        return error;
    }
}
