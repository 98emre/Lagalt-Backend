package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.ProjectService;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Collection<Project>> getAllProject(){
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getUserById(@PathVariable int id){
        Project project = projectService.findById(id);
        if (project == null) {
            throw new ProjectNotFoundException(id);
        }

        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> addUser(@RequestBody Project project){
        return ResponseEntity.ok(projectService.add(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateUser(@RequestBody Project project, @PathVariable int id){

        if (projectService.findById(id) == null) {
            throw new ProjectNotFoundException(id);
        }

        project.setId(id);
        return ResponseEntity.ok(projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        Project deletedProject= projectService.findById(id);

        if (deletedProject == null) {
            throw new ProjectNotFoundException(id);
        }

        projectService.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
