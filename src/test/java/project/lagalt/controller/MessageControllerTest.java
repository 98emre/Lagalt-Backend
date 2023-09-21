package project.lagalt.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import project.lagalt.mapper.MessageMapper;
import project.lagalt.model.dtos.message.*;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.User;
import project.lagalt.service.MessageService;
import project.lagalt.service.UserService;

import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @MockBean
    private MessageMapper messageMapper;

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
    public void test_Get_All_Messages() throws Exception {
        List<MessageDTO> mockMessageDTOs = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        mockMessageDTOs.add(messageDTO);

        when(messageMapper.messagesToMessageDtos(messageService.findAll())).thenReturn(mockMessageDTOs);

        mockMvc.perform(get("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Get_Message_By_id() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText("Hello Message");

        when(messageService.findById(1)).thenReturn(new Message());
        when(messageMapper.messageToMessageDto(any(Message.class))).thenReturn(messageDTO);

        mockMvc.perform(get("/api/messages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Get_Message_By_Id_Not_Found() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText("Hello Message");

        when(messageService.findById(1)).thenReturn(null);
        when(messageMapper.messageToMessageDto(any(Message.class))).thenReturn(messageDTO);


        mockMvc.perform(get("/api/collaborators/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_Message_By_id() throws Exception {
        when(messageService.findById(1)).thenReturn(new Message());

        mockMvc.perform(delete("/api/messages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isNoContent());

    }

    @Test
    public void test_Add_Message() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("emre");

        User mockUser2 = new User();
        mockUser2.setId(2);
        mockUser2.setUsername("emre2");

        Message message = new Message();
        message.setId(1);
        message.setTitle("Emres");
        message.setText("Hello Emres our Don");
        message.setReceiver(mockUser);
        message.setSender(mockUser2);

        when(userService.findByUsername("user")).thenReturn(mockUser);
        when(userService.findById(2)).thenReturn(mockUser2);
        when(messageMapper.messagePostDtoToMessage(any(MessagePostDTO.class))).thenReturn(message);
        when(messageService.add(any(Message.class))).thenReturn(new Message());
        when(messageMapper.messageToMessageDto(any(Message.class))).thenReturn(new MessageDTO());

        String requestBody = "{\"senderId\":1, \"receiverId\":2,\"title\":\"Hello Emres\" ,\"text\":\"Hello Emres our Don\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content(requestBody))
                .andExpect(status().isCreated());


    }

    @Test
    public void test_Update_Message() throws Exception {
        Message message = new Message();
        message.setId(1);
        message.setTitle("Emre Updated Title Emre");
        message.setText("Updated Emre Message Text");

        when(messageService.findById(1)).thenReturn(message);
        when(messageMapper.messageUpdateDtoToMessage(any(MessageUpdateDTO.class))).thenReturn(message);
        when(messageService.update(any(Message.class))).thenReturn(message);
        when(messageMapper.messageToMessageDto(any(Message.class))).thenReturn(new MessageDTO());


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/messages/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt())
                        .content("{ }"))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Get_All_Sent_Messages_By_User() throws Exception {
        List<MessageDTO> mockMessageDTOs = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText("Sent Message Emre");
        mockMessageDTOs.add(messageDTO);

        when(messageService.getMessagesSentByUser(1)).thenReturn(new HashSet<>());
        when(messageMapper.messagesToMessageDtos(anyList())).thenReturn(mockMessageDTOs);

        mockMvc.perform(get("/api/messages/1/sent-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_All_Received_Messages_By_User() throws Exception {
        List<MessageDTO> mockMessageDTOs = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText("Received Message Emre");
        mockMessageDTOs.add(messageDTO);

        when(messageService.getMessagesReceiverByUser(1)).thenReturn(new HashSet<>());
        when(messageMapper.messagesToMessageDtos(anyList())).thenReturn(mockMessageDTOs);

        mockMvc.perform(get("/api/messages/1/received-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk());
    }

}
