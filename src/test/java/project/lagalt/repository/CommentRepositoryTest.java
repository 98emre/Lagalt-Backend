package project.lagalt.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Test
    void test_Save_Comment(){
        User user = new User();
        user.setFullname("Testing test");
        user.setEmail("testing@gmail.com");
        user.setUsername("testing");
        User savedUser = userRepository.save(user);


        Project project = new Project();
        project.setTitle("Emre Project");
        project.setDescriptions("This is Emre story project");
        project.setGitlink("https://github.com/emretest/emre-project");
        project.setCategory(Category.GAME);
        project.setStatus(Status.NOT_STARTED);
        Project savedProject = projectRepository.save(project);

        Comment comment = new Comment();
        comment.setText("Emre Test comment");
        comment.setUser(savedUser);
        comment.setProject(savedProject);
        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getText()).isEqualTo("Emre Test comment");
        assertThat(savedComment.getUser()).isEqualTo(savedUser);
        assertThat(savedComment.getProject()).isEqualTo(savedProject);
    }

    @Test
    void test_Find_Comment_By_Id(){
        Comment comment = new Comment();
        comment.setText("Emre Test comment");
        comment.setUser(null);
        comment.setProject(null);
        Comment savedComment = commentRepository.save(comment);

        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());

        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getId()).isEqualTo(savedComment.getId());
    }

    @Test
    void test_Find_All_Comments(){
        Comment comment1 = new Comment();
        comment1.setText("Emre Test comment 1");
        comment1.setUser(null);
        comment1.setProject(null);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setText("Emre Test comment 2");
        comment2.setUser(null);
        comment2.setProject(null);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).hasSize(2);
    }

    @Test
    void test_Delete_Comment_By_Id(){
        Comment comment = new Comment();
        comment.setText("Emre Test comment");
        comment.setUser(null);
        comment.setProject(null);
        Comment savedComment = commentRepository.save(comment);
        commentRepository.deleteById(savedComment.getId());

        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());

        assertThat(foundComment).isNotPresent();
    }

    @Test
    void test_Update_Comment(){
        Comment comment = new Comment();
        comment.setText("Emre Test comment");
        comment.setUser(null);
        comment.setProject(null);

        Comment savedComment = commentRepository.save(comment);
        savedComment.setText("Emre Only");
        commentRepository.save(savedComment);

        Optional<Comment> updatedComment = commentRepository.findById(savedComment.getId());

        assertThat(updatedComment).isPresent();
        assertThat(updatedComment.get().getText()).isEqualTo("Emre Only");
    }
}
