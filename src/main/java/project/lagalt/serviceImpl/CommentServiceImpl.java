package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.repository.CommentRepository;
import project.lagalt.repository.ProjectRepository;
import project.lagalt.repository.UserRepository;
import project.lagalt.service.CommentService;
import project.lagalt.utilites.exceptions.CommentNotFoundException;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;
import project.lagalt.utilites.exceptions.UserNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private  final ProjectRepository projectRepository;
    private  final UserRepository userRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ProjectRepository projectRepository, UserRepository userRepositorys) {
        this.commentRepository = commentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepositorys;
    }

    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Comment updateComment = commentRepository.findById(comment.getId()).orElseThrow(() -> new CommentNotFoundException(comment.getId()));

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
        commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        commentRepository.deleteById(id);
    }

    @Override
    public Comment addCommentToProject(int projectId, Comment comment, String username) {
      /*  Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        comment.setProject(project);



        return commentRepository.save(comment);
     */

        return  null;
    }

}
