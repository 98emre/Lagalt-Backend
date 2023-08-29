package project.lagalt.utilites.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundExceptions extends RuntimeException{

    public UserNotFoundExceptions(int id){
        super("User with id: " + id + "does not exist");
    }
}
