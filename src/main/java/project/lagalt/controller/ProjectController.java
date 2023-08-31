package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.ProjectMapper;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.ProjectService;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping(path = "api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper){
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<ProjectDTO>> getAllProject(){
        return ResponseEntity.ok(projectMapper.projectsToProjectDTO(projectService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getUserById(@PathVariable int id){
        Project project = projectService.findById(id);
        if (project == null) {
            throw new ProjectNotFoundException(id);
        }

        return ResponseEntity.ok(projectMapper.projectToProjectDTO(project));
    }

    @PostMapping
    public ResponseEntity<Project> addUser(@RequestBody ProjectPostDTO projectPostDTO ){
        return ResponseEntity.ok(projectService.add(projectMapper.projectPostDtoToProject(projectPostDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateUser(@RequestBody ProjectUpdateDTO projectUpdateDTO, @PathVariable int id){

        if (projectService.findById(id) == null) {
            throw new ProjectNotFoundException(id);
        }

        projectUpdateDTO.setId(id);
        return ResponseEntity.ok(projectService.update(projectMapper.projectUpdateDtoToProject(projectUpdateDTO)));
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
