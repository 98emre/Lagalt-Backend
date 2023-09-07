package project.lagalt.utilites.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CollaboratorAlreadyExistException extends RuntimeException{

    public CollaboratorAlreadyExistException(String username){
        super("Error :  Collaborator " + username + " alredy exists in this project");
    }
}
