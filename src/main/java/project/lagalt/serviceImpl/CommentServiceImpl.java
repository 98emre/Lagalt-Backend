package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Comment;
import project.lagalt.repository.CommentRepository;
import project.lagalt.service.CommentService;

import java.util.Collection;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Comment updateComment = commentRepository.findById(comment.getId()).orElse(null);

        if(comment.getText() != null){
            updateComment.setText(comment.getText());
        }

        if(comment.getDate() != null){
            updateComment.setDate(comment.getDate());
        }

        return commentRepository.save(updateComment);
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
