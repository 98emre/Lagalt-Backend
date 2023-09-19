package project.lagalt.utilites.exceptions.collaborator;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CollaboratorNotFoundException extends RuntimeException{

    public CollaboratorNotFoundException(int id){
        super("Collaborator with id: " + id + " does not exist");
    }
}
