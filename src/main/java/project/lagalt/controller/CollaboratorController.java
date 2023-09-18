package project.lagalt.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import project.lagalt.utilites.exceptions.CollaboratorAlreadyExistException;
import project.lagalt.utilites.exceptions.CollaboratorCheckOwnerException;
import project.lagalt.utilites.exceptions.CollaboratorNotFoundException;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

@RestController
@RequestMapping(path = "api/collaborators")
@CrossOrigin
public class CollaboratorController {

    private final CollaboratorService collaboratorService;
    private final CollaboratorMapper collaboratorMapper;

    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService, CollaboratorMapper collaboratorMapper,
            UserService userService, ProjectService projectService) {
        this.collaboratorService = collaboratorService;
        this.collaboratorMapper = collaboratorMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("public")
    public ResponseEntity<Collection<CollaboratorDTO>> getAllCollaborator() {
        return ResponseEntity.ok(collaboratorMapper.collaboratorToCollaboratorDtos(collaboratorService.findAll()));
    }

    @GetMapping("public/{id}")
    public ResponseEntity<CollaboratorDTO> getCollaboratorById(@PathVariable(value = "id") int id) {
        Collaborator collaborator = collaboratorService.findById(id);

        if (collaborator == null) {
            throw new CollaboratorNotFoundException(id);
        }

        CollaboratorDTO collaboratorDTO = collaboratorMapper.collaboratorToCollaboratorDto(collaborator);

        return ResponseEntity.ok(collaboratorDTO);
    }

    @PostMapping("/{projectId}/add-collaborator")
    public ResponseEntity<CollaboratorDTO> addCollaborator(@PathVariable(value = "projectId") Integer projectId,
            @RequestBody CollaboratorPostDTO collaboratorPostDTO, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");

        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        Project project = projectService.findById(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(projectId);
        }

        if (project.getUser().getId() == user.getId()) {
            throw new CollaboratorCheckOwnerException(username);
        }

        if (projectService.findCollaboratorExist(projectId, user)) {
            throw new CollaboratorAlreadyExistException(username);
        }

        Collaborator collaborator = collaboratorMapper.collaboratorPostDtoToCollaborator(collaboratorPostDTO);
        collaborator.setProject(project);
        collaborator.setUser(user);
        collaboratorService.add(collaborator);

        CollaboratorDTO collaboratorDTO = collaboratorMapper.collaboratorToCollaboratorDto(collaborator);
        URI location = URI.create("/api/collaborators/public/" + collaborator.getId());

        return ResponseEntity.created(location).body(collaboratorDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CollaboratorDTO> updateCollaborator(@RequestBody CollaboratorUpdateDTO collaboratorUpdateDTO,
            @PathVariable(value = "id") int id) {

        if (collaboratorService.findById(id) == null) {
            throw new CollaboratorNotFoundException(id);
        }

        collaboratorUpdateDTO.setId(id);
        Collaborator collaborator = collaboratorService
                .update(collaboratorMapper.collaboratorUpdateDtoToCollaborator(collaboratorUpdateDTO));
        CollaboratorDTO collaboratorDTO = collaboratorMapper.collaboratorToCollaboratorDto(collaborator);

        return ResponseEntity.ok(collaboratorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Collaborator> deleteCollaborator(@PathVariable(value = "id") int id) {
        Collaborator deletedCollaborator = collaboratorService.findById(id);

        if (deletedCollaborator == null) {
            throw new CollaboratorNotFoundException(id);
        }

        collaboratorService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CollaboratorAlreadyExistException.class)
    public ResponseEntity<String> handleCollaboratorAlreadyExistException(CollaboratorAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CollaboratorCheckOwnerException.class)
    public ResponseEntity<String> handleCollaboratorCheckOwnerException(CollaboratorCheckOwnerException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CollaboratorNotFoundException.class)
    public ResponseEntity<String> handleCollaboratorNotFoundException(CollaboratorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

}
