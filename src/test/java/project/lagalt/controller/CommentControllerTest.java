package project.lagalt.controller;


import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import project.lagalt.mapper.CommentMapper;
import project.lagalt.model.dtos.comment.CommentDTO;
import project.lagalt.model.dtos.comment.CommentPostDTO;
import project.lagalt.model.dtos.comment.CommentUpdateDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.CommentService;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(CommentController.class)
public class CommentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Value("${keycloak.client-id}")
    private String clientId;

    private RequestPostProcessor jwt() {
        Jwt mockJwt = Jwt.withTokenValue("mock.token.value")
                .header("alg", "none")
                .claim("preferred_username", "user")
                .build();
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwt);

        return mockRequest -> {
            mockRequest.addHeader("Authorization", "Bearer mock.token.value");
            return mockRequest;
        };
    }
    
    @Test
    public void test_Get_All_Comments() throws Exception {
        List<CommentDTO> mockComments = new ArrayList<>();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Hello Comment Emre");
        mockComments.add(commentDTO);

        when(commentMapper.commentToCommentDtos(commentService.findAll())).thenReturn(mockComments);

        mockMvc.perform(get("/api/comments/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Get_Comment_By_Id() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Hello Comment Emre");

        when(commentService.findById(1)).thenReturn(new Comment());
        when(commentMapper.commentToCommentDto(any(Comment.class))).thenReturn(commentDTO);

        mockMvc.perform(get("/api/comments/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(jsonPath("$.text").value("Hello Comment Emre"))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Get_Comment_By_Id_Not_Found() throws Exception {
        when(commentService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/api/comments/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Add_Comment() throws Exception{
        Project mockProject = new Project();
        mockProject.setId(1);
        mockProject.setTitle("Hello Project Emre");

        Comment comment = new Comment();
        comment.setText("Hello text");

        Set<Comment> commentSet = new HashSet<>();
        commentSet.add(comment);

        mockProject.setComments(commentSet);

        when(userService.findByUsername("user")).thenReturn(new User());
        when(projectService.findById(1)).thenReturn(mockProject);
        when(commentMapper.commentPostDtoToComment(any(CommentPostDTO.class))).thenReturn(comment);
        when(commentService.add(any(Comment.class))).thenReturn(comment);
        when(commentMapper.commentToCommentDto(any(Comment.class))).thenReturn(new CommentDTO());

        mockMvc.perform(post("/api/comments/project/1/add-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void test_Update_Comment() throws Exception{

        when(commentService.findById(1)).thenReturn(new Comment());
        when(commentMapper.commentUpdateDtoToComment(any(CommentUpdateDTO.class))).thenReturn(new Comment());
        when(commentService.update(any(Comment.class))).thenReturn(new Comment());
        when(commentMapper.commentToCommentDto(any(Comment.class))).thenReturn(new CommentDTO());

        mockMvc.perform(patch("/api/comments/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Delete_Comment() throws Exception {
        when(commentService.findById(1)).thenReturn(new Comment());

        mockMvc.perform(delete("/api/comments/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()))
                .andExpect(status().isNoContent());
    }


}
