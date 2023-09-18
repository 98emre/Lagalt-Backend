package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.CommentMapper;
import project.lagalt.model.dtos.comment.CommentDTO;
import project.lagalt.model.dtos.comment.CommentPostDTO;
import project.lagalt.model.dtos.comment.CommentUpdateDTO;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.CommentService;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.CommentNotFoundException;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper commentMapper, UserService userService, ProjectService projectService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Collection<CommentDTO>> getAllComment(){
        return ResponseEntity.ok(commentMapper.commentToCommentDtos(commentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "id") int id){
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new CommentNotFoundException(id);
        }

        CommentDTO commentDTO = commentMapper.commentToCommentDto(comment);

        return ResponseEntity.ok(commentDTO);
    }


    @PostMapping("/project/{projectId}/add-comment")
    public ResponseEntity<CommentDTO> addComment(@PathVariable(value = "projectId") Integer projectId, @RequestBody CommentPostDTO commentPostDTO, @AuthenticationPrincipal Jwt jwt){
       String username = jwt.getClaim("preferred_username");

       Comment comment = commentMapper.commentPostDtoToComment(commentPostDTO);
       User user = userService.findByUsername(username);

       if(user == null){
           throw new UserNotFoundException(username);
       }

       Project project = projectService.findById(projectId);

       if(project == null){
           throw new ProjectNotFoundException(projectId);
       }

       comment.setProject(project);
       comment.setUser(user);

       commentService.add(comment);
       CommentDTO commentDTO = commentMapper.commentToCommentDto(comment);
       URI location = URI.create("/api/comments/public/" + comment.getId());

        return ResponseEntity.created(location).body(commentDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentUpdateDTO commentUpdateDTO, @PathVariable(value = "id") int id){

        if (commentService.findById(id) == null) {
            throw new CommentNotFoundException(id);
        }

        commentUpdateDTO.setId(id);
        Comment comment = commentService.update(commentMapper.commentUpdateDtoToComment(commentUpdateDTO));
        CommentDTO commentDTO = commentMapper.commentToCommentDto(comment);


        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<User> deleteComment(@PathVariable(value = "id") int id){
        Comment deletedComment = commentService.findById(id);

        if (deletedComment == null) {
            throw new CommentNotFoundException(id);
        }

        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException ex) {
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
