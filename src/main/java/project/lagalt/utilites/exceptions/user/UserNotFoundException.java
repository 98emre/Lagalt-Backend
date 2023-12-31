package project.lagalt.utilites.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id){
        super("User with id: " + id + " does not exist");
    }

    public UserNotFoundException(String username){
        super("User with username: " + username + " does not exist");
    }
}
