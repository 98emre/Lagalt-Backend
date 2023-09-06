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
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable int id){
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new CommentNotFoundException(id);
        }

        return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
    }


    @PostMapping("/project/{projectId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer projectId, @RequestBody CommentPostDTO commentPostDTO, @AuthenticationPrincipal Jwt jwt){
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

        return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody CommentUpdateDTO commentUpdateDTO, @PathVariable int id){

        if (commentService.findById(id) == null) {
            throw new CommentNotFoundException(id);
        }

        commentUpdateDTO.setId(id);
        return ResponseEntity.ok(commentService.update(commentMapper.commentUpdateDtoToComment(commentUpdateDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteComment(@PathVariable int id){
        Comment deletedComment = commentService.findById(id);

        if (deletedComment == null) {
            throw new CommentNotFoundException(id);
        }

        commentService.deleteById(id);

        return ResponseEntity.status(200).build();
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
    public ResponseEntity<String> handleProjectNotFoundExceptionn(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
