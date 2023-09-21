package project.lagalt.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import project.lagalt.mapper.CollaboratorMapper;
import project.lagalt.mapper.ProjectMapper;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.service.CollaboratorService;
import project.lagalt.service.ProjectService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.enums.Application;

import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(value = ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private CollaboratorService collaboratorService;

    @MockBean
    private CollaboratorMapper collaboratorMapper;

    @MockBean
    private ProjectMapper projectMapper;


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
    public void test_Get_All_Project() throws Exception {
        List<ProjectDTO> mockProjects = new ArrayList<>();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle("Hello");

        mockProjects.add(projectDTO);

        when(projectMapper.projectsToProjectDTO(projectService.findAll())).thenReturn(mockProjects);

        mockMvc.perform(get("/api/projects/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Project_By_Id() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle("Hello");


        when(projectService.findById(1)).thenReturn(new Project());
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(projectDTO);

        mockMvc.perform(get("/api/projects/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Project_By_Id_Not_Found() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle("Hello");


        when(projectService.findById(1)).thenReturn(null);
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(projectDTO);

        mockMvc.perform(get("/api/projects/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Add_Project() throws Exception {
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        projectPostDTO.setTitle("Hello Emre");

        when(userService.findByUsername("user")).thenReturn(new User());
        when(projectMapper.projectPostDtoToProject(any(ProjectPostDTO.class))).thenReturn(new Project());
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(new ProjectDTO());

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void test_Find_By_Title() throws Exception {
        List<Project> mockProjects = new ArrayList<>();
        mockProjects.add(new Project());

        when(projectService.findAllByTitle("Hello")).thenReturn(mockProjects);
        when(projectMapper.projectsToProjectDTO(anyList())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/projects/public/search?title=Hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());

    }


    @Test
    public void test_Update_Project() throws Exception {
        ProjectUpdateDTO projectUpdateDTO = new ProjectUpdateDTO();
        projectUpdateDTO.setTitle("Hello Emre Update");

        when(projectService.findById(1)).thenReturn(new Project());
        when(projectMapper.projectUpdateDtoToProject(any(ProjectUpdateDTO.class))).thenReturn(new Project());
        when(projectService.update(any(Project.class))).thenReturn(new Project());
        when(projectMapper.projectToProjectDTO(any(Project.class))).thenReturn(new ProjectDTO());

        mockMvc.perform(patch("/api/projects/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt())
                .content("{ }"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Delete_Project() throws Exception{
        when(projectService.findById(1)).thenReturn(new Project());

        mockMvc.perform(delete("/api/projects/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_Pending_Collaborators() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("user");

        Project mockProject = new Project();
        mockProject.setUser(mockUser);

        when(userService.findByUsername("user")).thenReturn(mockUser);
        when(projectService.findById(1)).thenReturn(mockProject);
        when(projectService.findAllPendingByCollaborator(any(Project.class))).thenReturn(new ArrayList<>());
        when(collaboratorMapper.collaboratorToCollaboratorDtos(anyList())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/projects/1/collaborators/all-pending")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_Approved_Collaborators() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("user");

        Project mockProject = new Project();
        mockProject.setUser(mockUser);

        when(userService.findByUsername("user")).thenReturn(mockUser);
        when(projectService.findById(1)).thenReturn(mockProject);
        when(projectService.findAllPendingByCollaborator(any(Project.class))).thenReturn(new ArrayList<>());
        when(collaboratorMapper.collaboratorToCollaboratorDtos(anyList())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/projects/1/collaborators/all-approved")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }
}
