package project.lagalt.utilites.exceptions.collaborator;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CollaboratorCheckOwnerException extends RuntimeException {

    public CollaboratorCheckOwnerException(String username){
        super("Error :  Owner " + username + " can't collaborator in his own project");
    }
}
