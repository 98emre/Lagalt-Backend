package project.lagalt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import project.lagalt.mapper.CollaboratorMapper;
import project.lagalt.model.dtos.collaborator.*;
import project.lagalt.model.entities.*;
import project.lagalt.service.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(CollaboratorController.class)
public class CollaboratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorService collaboratorService;

    @MockBean
    private CollaboratorMapper collaboratorMapper;

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
    public void test_Get_All_Collaborator() throws Exception {
        List<CollaboratorDTO> mockCollaborator = new ArrayList<>();
        CollaboratorDTO collaboratorDTO = new CollaboratorDTO();
        mockCollaborator.add(collaboratorDTO);

        when(collaboratorMapper.collaboratorToCollaboratorDtos(collaboratorService.findAll())).thenReturn(mockCollaborator);

        mockMvc.perform(get("/api/collaborators/public")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_Collaborator_By_Id() throws Exception {
        CollaboratorDTO collaboratorDTO = new CollaboratorDTO();
        collaboratorDTO.setMotivation("Hello Motivation Emre");

        when(collaboratorService.findById(1)).thenReturn(new Collaborator());
        when(collaboratorMapper.collaboratorToCollaboratorDto(any(Collaborator.class))).thenReturn(collaboratorDTO);

        mockMvc.perform(get("/api/collaborators/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(jsonPath("$.motivation").value("Hello Motivation Emre"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_Collaborator_By_Id_Not_Found() throws Exception {
        CollaboratorDTO collaboratorDTO = new CollaboratorDTO();
        collaboratorDTO.setMotivation("Hello Motivation Emre");

        when(collaboratorService.findById(1)).thenReturn(null);
        when(collaboratorMapper.collaboratorToCollaboratorDto(any(Collaborator.class))).thenReturn(collaboratorDTO);

        mockMvc.perform(get("/api/collaborators/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_Collaborator_By_Id() throws Exception{

        when(collaboratorService.findById(1)).thenReturn(new Collaborator());

        mockMvc.perform(delete("/api/collaborators/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_Add_Collaborator() throws Exception {
        Project mockProject = new Project();
        Collaborator mockCollaborator = new Collaborator();
        User mockUser = new User();
        User mockUserProjectOwner = new User();


        mockUser.setUsername("user");
        mockUser.setId(1);
        mockCollaborator.setUser(mockUser);

        Set<Collaborator> collaborators = new HashSet<>();
        collaborators.add(mockCollaborator);

        mockProject.setUser(mockUserProjectOwner);
        mockProject.setCollaborators(collaborators);

        when(userService.findByUsername("user")).thenReturn(mockUser);
        when(projectService.findById(1)).thenReturn(mockProject);
        when(collaboratorMapper.collaboratorPostDtoToCollaborator(any(CollaboratorPostDTO.class))).thenReturn(mockCollaborator);
        when(collaboratorService.add(any(Collaborator.class))).thenReturn(mockCollaborator);
        when(collaboratorMapper.collaboratorToCollaboratorDto(any(Collaborator.class))).thenReturn(new CollaboratorDTO());

        mockMvc.perform(post("/api/collaborators/1/add-collaborator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void test_Update_Collaborator() throws Exception {

        when(collaboratorService.findById(1)).thenReturn(new Collaborator());
        when(collaboratorMapper.collaboratorUpdateDtoToCollaborator(any(CollaboratorUpdateDTO.class))).thenReturn(new Collaborator());
        when(collaboratorService.update(any(Collaborator.class))).thenReturn(new Collaborator());
        when(collaboratorMapper.collaboratorToCollaboratorDto(any(Collaborator.class))).thenReturn(new CollaboratorDTO());

        mockMvc.perform(patch("/api/collaborators/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{}"))
                .andExpect(status().isOk());

    }

}
