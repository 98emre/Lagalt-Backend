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
import project.lagalt.model.entities.User;
import project.lagalt.service.CommentService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.CommentNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper commentMapper, UserService userService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("public")
    public ResponseEntity<Collection<CommentDTO>> getAllComment(){
        return ResponseEntity.ok(commentMapper.commentToCommentDtos(commentService.findAll()));
    }

    @GetMapping("public/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable int id){
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new CommentNotFoundException(id);
        }

        return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Comment> addCommentToProject(@PathVariable Integer projectId, @RequestBody CommentPostDTO commentPostDTO, @AuthenticationPrincipal Jwt jwt){
        String username = jwt.getClaim("preferred_username");
        Comment comment = commentMapper.commentPostDtoToComment(commentPostDTO);
        Comment savedComment = commentService.addCommentToProject(projectId, comment,username);

        return ResponseEntity.ok(savedComment);
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
}
