package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.CollaboratorMapper;
import project.lagalt.model.dtos.collaborator.CollaboratorDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorPostDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorUpdateDTO;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.CollaboratorService;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.CollaboratorNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping("api/collaborators")
@CrossOrigin
public class CollaboratorController {

    private final CollaboratorService collaboratorService;
    private final CollaboratorMapper collaboratorMapper;

    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService, CollaboratorMapper collaboratorMapper, UserService userService, ProjectService projectService) {
        this.collaboratorService = collaboratorService;
        this.collaboratorMapper = collaboratorMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Collection<CollaboratorDTO>> getAllCollaborator(){
        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDtos(collaboratorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> getCollaboratorById(@PathVariable int id){
        Collaborator collaborator = collaboratorService.findById(id);

        if (collaborator == null) {
            throw new CollaboratorNotFoundException(id);
        }

        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDto(collaborator));
    }

    @PostMapping("project/{projectId}")
    public ResponseEntity<CollaboratorDTO> addCollaborator(@PathVariable Integer projectId, @RequestBody CollaboratorPostDTO collaboratorPostDTO, @AuthenticationPrincipal Jwt jwt){
        String username = jwt.getClaim("preferred_username");

        User user = userService.findByUsername(username);
        Project project = projectService.findById(projectId);

        Collaborator collaborator = collaboratorMapper.collaboratorPostDtoToCollaborator(collaboratorPostDTO);
        collaborator.setProject(project);
        collaborator.setUser(user);

        collaboratorService.add(collaborator);

        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDto(collaborator));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@RequestBody CollaboratorUpdateDTO collaboratorUpdateDTO, @PathVariable int id){

        if (collaboratorService.findById(id) == null) {
            throw new CollaboratorNotFoundException(id);
        }

        collaboratorUpdateDTO.setId(id);
        return ResponseEntity.ok(collaboratorService.update(collaboratorMapper.collaboratorUpdateDtoToCollaborator(collaboratorUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteCollaborator(@PathVariable int id){
        Collaborator deletedCollaborator= collaboratorService.findById(id);

        if (deletedCollaborator == null) {
            throw new CollaboratorNotFoundException(id);
        }

        collaboratorService.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @ExceptionHandler(CollaboratorNotFoundException.class)
    public ResponseEntity<String> handleCollaboratorNotFoundException(CollaboratorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
