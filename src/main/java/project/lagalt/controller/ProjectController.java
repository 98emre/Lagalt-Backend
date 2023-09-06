package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.ProjectMapper;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.CollaboratorService;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    private final CollaboratorService collaboratorService;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, ProjectMapper projectMapper){
        this.projectService = projectService;
        this.userService = userService;
        this.projectMapper = projectMapper;
    }

    @GetMapping("public")
    public ResponseEntity<Collection<ProjectDTO>> getAllProject(){
        return ResponseEntity.ok(projectMapper.projectsToProjectDTO(projectService.findAll()));
    }

    @GetMapping("public/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable int id){
        Project project = projectService.findById(id);
        if (project == null) {
            throw new ProjectNotFoundException(id);
        }

        return ResponseEntity.ok(projectMapper.projectToProjectDTO(project));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectPostDTO projectPostDTO, @AuthenticationPrincipal Jwt jwt){
        String username = jwt.getClaim("preferred_username");

        Project project = projectMapper.projectPostDtoToProject(projectPostDTO);
        User user = userService.findByUsername(username);

        if(user == null){
            throw new UserNotFoundException(username);
        }

        project.setUser(user);
        projectService.add(project);

        return ResponseEntity.ok(projectMapper.projectToProjectDTO(project));
    }

    @GetMapping("public/search")
    public ResponseEntity<Collection<ProjectDTO>> findByTitle(@RequestParam String title){
        return ResponseEntity.ok(projectMapper.projectsToProjectDTO(projectService.findAllByTitle(title)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectUpdateDTO projectUpdateDTO, @PathVariable int id){

        if (projectService.findById(id) == null) {
            throw new ProjectNotFoundException(id);
        }

        projectUpdateDTO.setId(id);
        return ResponseEntity.ok(projectService.update(projectMapper.projectUpdateDtoToProject(projectUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteProject(@PathVariable int id){
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
