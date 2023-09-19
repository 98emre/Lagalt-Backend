package project.lagalt.utilites.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MessageCheckOwnerException extends RuntimeException {
    public MessageCheckOwnerException(String username){
        super("Error :  Owner " + username + " can't send message to him self");
    }
}
