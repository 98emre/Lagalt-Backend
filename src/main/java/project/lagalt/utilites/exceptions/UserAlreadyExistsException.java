package project.lagalt.utilites.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyExistsException  extends RuntimeException {

    public UserAlreadyExistsException(String username){
        super("User with username: " + username + " already in use");
    }

}
