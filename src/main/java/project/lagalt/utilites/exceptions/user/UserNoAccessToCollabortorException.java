package project.lagalt.utilites.exceptions.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNoAccessToCollabortorException extends RuntimeException{

    public UserNoAccessToCollabortorException(String username){
        super("Error : User with username: "+ username + " has no right for access of this project collaborator");
    }

}
