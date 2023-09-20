package project.lagalt.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.lagalt.model.entities.Comment;
import project.lagalt.repository.CommentRepository;
import project.lagalt.serviceImpl.CommentServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void test_Add_Comment_Successfully(){
        Comment mockComment = new Comment();
        mockComment.setText("Hello World");

        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        Comment comment = commentService.add(mockComment);
        assertThat(comment).isNotNull();
        assertThat(comment.getText()).isEqualTo("Hello World");
    }

    @Test
    void test_Find_Comment_By_Id() {
        Comment mockComment = new Comment();
        mockComment.setText("Hello World");

        when(commentRepository.findById(1)).thenReturn(Optional.of(mockComment));

        Comment foundComment = commentService.findById(1);
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.getId()).isEqualTo(mockComment.getId());
    }

    @Test
    void test_Find_All_Comments() {
        Comment mockComment = new Comment();
        mockComment.setText("Comment 1");

        Comment mockComment2 = new Comment();
        mockComment2.setText("Comment 2");

        List<Comment> commentSet = Arrays.asList(mockComment, mockComment2);

        when(commentRepository.findAll()).thenReturn(commentSet);

        List<Comment> comments = (List<Comment>) commentService.findAll();

        assertThat(comments).hasSize(2);
        assertThat(comments).contains(mockComment,mockComment2);
    }

    @Test
    void test_Update_Comment(){
        Comment oldMockComment = new Comment();
        oldMockComment.setId(1);
        oldMockComment.setText("Test Hello");

        Comment newMockComment = new Comment();
        newMockComment.setId(1);
        newMockComment.setText("New Test Hello");

        when(commentRepository.findById(1)).thenReturn(Optional.of(oldMockComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(newMockComment);

        Comment updated = commentService.update(newMockComment);

        assertThat(updated).isNotNull();
        assertThat(updated.getText()).isEqualTo("New Test Hello");

    }

    @Test
    void test_Delete_Comment_By_Id(){
        Comment deleteComment = new Comment();
        deleteComment.setText("Hello");

        when(commentRepository.findById(1)).thenReturn(Optional.of(deleteComment));
        commentService.deleteById(1);

        verify(commentRepository,times(1)).deleteById(1);

    }
}
