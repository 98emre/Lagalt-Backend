package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.User;
import project.lagalt.service.CollaboratorService;
import project.lagalt.utilites.exceptions.CommentNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping("api/collaborators")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping
    public ResponseEntity<Collection<Collaborator>> getAllCollaborator(){
        return ResponseEntity.ok(collaboratorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collaborator> getCollaboratorById(@PathVariable int id){
        Collaborator collaborator = collaboratorService.findById(id);

        if (collaborator == null) {
            return null;
        }

        return ResponseEntity.ok(collaborator);
    }

    @PostMapping
    public ResponseEntity<Collaborator> addCollaborator(@RequestBody Collaborator collaborator){
        return ResponseEntity.ok(collaboratorService.add(collaborator));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@RequestBody Collaborator collaborator, @PathVariable int id){

        if (collaboratorService.findById(id) == null) {
            throw new CommentNotFoundException(id);
        }

        collaborator.setId(id);
        return ResponseEntity.ok(collaboratorService.update(collaborator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteCollaborator(@PathVariable int id){
        Collaborator deletedCollaborator= collaboratorService.findById(id);

        if (deletedCollaborator == null) {
            return null;
        }

        collaboratorService.deleteById(id);

        return ResponseEntity.status(200).build();
    }
}
