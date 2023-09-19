package project.lagalt.utilites.exceptions.project;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(int id){
        super("Project with id: " + id + " does not exist");
    }

}
