package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.User;
import project.lagalt.service.CommentService;
import project.lagalt.utilites.exceptions.CommentNotFoundException;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/comments")
public class CommentController {

    private final CommentService commentService;


    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Comment>> getAllComment(){
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int id){
        Comment comment = commentService.findById(id);
        if (comment == null) {
            throw new CommentNotFoundException(id);
        }

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment ){
        return ResponseEntity.ok(commentService.add(comment));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable int id){

        if (commentService.findById(id) == null) {
            throw new CommentNotFoundException(id);
        }

        comment.setId(id);
        return ResponseEntity.ok(commentService.update(comment));
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
