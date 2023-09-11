package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.CollaboratorMapper;
import project.lagalt.mapper.ProjectMapper;
import project.lagalt.model.dtos.collaborator.CollaboratorDTO;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;
import project.lagalt.utilites.exceptions.UserNoAccessToCollabortorException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    private final CollaboratorMapper collaboratorMapper;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, CollaboratorMapper collaboratorMapper, ProjectMapper projectMapper){
        this.projectService = projectService;
        this.userService = userService;
        this.collaboratorMapper = collaboratorMapper;
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

        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);


        return ResponseEntity.ok(projectDTO);
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
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        URI location = URI.create("/api/projects/public/" + project.getId());

        return ResponseEntity.created(location).body(projectDTO);
    }

    @GetMapping("public/search")
    public ResponseEntity<Collection<ProjectDTO>> findByTitle(@RequestParam String title){
        return ResponseEntity.ok(projectMapper.projectsToProjectDTO(projectService.findAllByTitle(title)));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectUpdateDTO projectUpdateDTO, @PathVariable int id){

        if (projectService.findById(id) == null) {
            throw new ProjectNotFoundException(id);
        }

        projectUpdateDTO.setId(id);
        Project project = projectService.update(projectMapper.projectUpdateDtoToProject(projectUpdateDTO));
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        return ResponseEntity.ok(projectDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<User> deleteProject(@PathVariable int id){
        Project deletedProject= projectService.findById(id);

        if (deletedProject == null) {
            throw new ProjectNotFoundException(id);
        }

        projectService.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{projectId}/collaborators/all-pending")
    public ResponseEntity<Collection<CollaboratorDTO>> getPendingCollaborators(@PathVariable Integer projectId,  @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");

        User user = userService.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }

        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(project.getId());
        }

        if(project.getUser().getId() != user.getId()){
            throw new UserNoAccessToCollabortorException(username);
        }

        Collection<Collaborator> pendingCollaborators = projectService.findAllPendingByCollaborator(project);
        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDtos(pendingCollaborators));
    }

    @GetMapping("/{projectId}/collaborators/all-approved")
    public ResponseEntity<Collection<CollaboratorDTO>> getApprovedCollaborators(@PathVariable Integer projectId, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");

        User user = userService.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }

        Project project = projectService.findById(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(project.getId());
        }

        /*if(project.getUser().getId() != user.getId()){
            throw new UserNoAccessToCollabortorException(username);
        }*/
        
        Collection<Collaborator> approvedCollaborators = projectService.findAllApprovedByCollaborator(project);

        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDtos(approvedCollaborators));
    }

    @ExceptionHandler(UserNoAccessToCollabortorException.class)
    public ResponseEntity<String> handleUserNoAccessToCollaboratorException(UserNoAccessToCollabortorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

}
