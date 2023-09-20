package project.lagalt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import project.lagalt.mapper.UserMapper;
import project.lagalt.model.dtos.user.*;
import project.lagalt.model.entities.User;
import project.lagalt.service.UserService;
import org.springframework.http.MediaType;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Value("${keycloak.client-id}")
    private String clientId;

    @MockBean
    private JwtDecoder jwtDecoder;
    @Test
    @WithMockUser(username = "user")
    void test_Get_All_Users() throws Exception {
        User user = new User();
        user.setUsername("Emre");

        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setUsername("Emre");
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);

        mockMvc.perform(get("/api/users/public").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user")
    void test_Get_User_By_Id() throws Exception{
        User user = new User();
        user.setUsername("Emre");

        when(userService.findById(1)).thenReturn(user);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setUsername("Emre");
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);


        mockMvc.perform(get("/api/users/public").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user")
    void test_Get_User_By_Id_Not_Found() throws Exception {
        User user = new User();
        user.setUsername("Emre");

        when(userService.findById(2)).thenReturn(null);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setUsername("Emre");
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);

        mockMvc.perform(get("/api/users/public/2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    void test_Get_User_By_Username_Existing() throws Exception {
        User user = new User();
        user.setUsername("Emre");

        when(userService.findByUsername("Emre")).thenReturn(user);

        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setUsername("Emre");
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(mockUserDTO);

        mockMvc.perform(get("/api/users/public/username/Emre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }

    @Test
    void test_Update_User() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("Emre");
        user.setEmail("emre@live.se");

        when(userService.findById(1)).thenReturn(user);

        UserUpdateDTO mockUserUpdateDTO = new UserUpdateDTO();
        mockUserUpdateDTO.setId(1);
        mockUserUpdateDTO.setUsername("UpdatedMockEmre");
        mockUserUpdateDTO.setEmail("updatedMockEmre@live.se");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("UpdatedEmre");
        updatedUser.setEmail("updateEmre@live.se");

        when(userService.update(any(User.class))).thenReturn(updatedUser);
        when(userMapper.userUpdateToUser(any(UserUpdateDTO.class))).thenReturn(updatedUser);
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(new UserDTO());

        Jwt mockJwtToken = createMockJwtToken("user", Collections.singletonList("USER"));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwtToken);

        mockMvc.perform(patch("/api/users/1/update")
                        .header("Authorization", "Bearer " + mockJwtToken.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void test_Delete_User_By_Id() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("Emre");

        when(userService.findById(1)).thenReturn(user);
        Jwt mockJwtToken = createMockJwtToken("user",Collections.singletonList("USER"));

        when(jwtDecoder.decode(anyString())).thenReturn(mockJwtToken);

        mockMvc.perform(delete("/api/users/1/delete")
                    .header("Authorization","Bearer " + mockJwtToken.getTokenValue())
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void test_Delete_User_By_Id_Not_Found() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("Emre");

        when(userService.findById(1)).thenReturn(null);

        Jwt mockJwtToken = createMockJwtToken("user",Collections.singletonList("USER"));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwtToken);

        mockMvc.perform(delete("/api/users/1/delete")
                        .header("Authorization", "Bearer " + mockJwtToken.getTokenValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void test_Add_User_From_Token() throws Exception {
        Jwt mockJwtToken = createMockJwtToken("user",Collections.singletonList("USER"));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwtToken);
        String bearerToken = "Bearer " + mockJwtToken.getTokenValue();

        User user = new User();
        user.setId(1);
        user.setUsername("Emre");

        doNothing().when(userService).createUserFromToken(anyString());
        when(userService.findByToken(anyString())).thenReturn(user);

        mockMvc.perform(post("/api/users/add-user")
                        .header("Authorization", bearerToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void test_Get_User_By_Token() throws Exception {
        Jwt mockJwtToken = createMockJwtToken("user", Collections.singletonList("USER"));
        when(jwtDecoder.decode(anyString())).thenReturn(mockJwtToken);
        String bearerToken = "Bearer " + mockJwtToken.getTokenValue();

        User user = new User();
        user.setId(1);
        user.setUsername("Emre");

        when(userService.findByToken(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/users/public/token/username")
                        .header("Authorization", bearerToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Jwt createMockJwtToken(String subject, Collection<String> roles) {

        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> resourceAccess = new HashMap<>();
        Map<String, Object> clientResource = new HashMap<>();
        clientResource.put("roles", roles);
        resourceAccess.put(clientId, clientResource);
        claims.put("resource_access", resourceAccess);

        return Jwt.withTokenValue("mock.token.value")
                .header("alg", "none")
                .subject(subject)
                .claims(claims::putAll)
                .build();
    }

}
