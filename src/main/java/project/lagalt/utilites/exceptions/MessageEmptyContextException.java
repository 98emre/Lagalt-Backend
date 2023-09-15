package project.lagalt.utilites.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MessageEmptyContextException extends RuntimeException {

    public MessageEmptyContextException(){
        super("You can't have empty title or text message");
    }
}
