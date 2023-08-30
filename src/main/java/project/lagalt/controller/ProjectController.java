package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.ProjectService;

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
            return  null;
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
            return  null;
        }
        project.setId(id);
        return ResponseEntity.ok(projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        Project deletedProject= projectService.findById(id);

        if (deletedProject == null) {
            return null;
        }

        projectService.deleteById(id);

        return ResponseEntity.status(200).build();
    }

}
