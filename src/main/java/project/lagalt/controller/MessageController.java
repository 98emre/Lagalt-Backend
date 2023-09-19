package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.MessageMapper;
import project.lagalt.model.dtos.message.MessageDTO;
import project.lagalt.model.dtos.message.MessagePostDTO;
import project.lagalt.model.dtos.message.MessageUpdateDTO;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.User;
import project.lagalt.service.MessageService;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/messages")
@CrossOrigin
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<MessageDTO>> getAllMessage() {
        return ResponseEntity.ok(messageMapper.messagesToMessageDtos(messageService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable(value = "id") Integer id) {
        Message message = messageService.findById(id);
        return ResponseEntity.ok(messageMapper.messageToMessageDto(message));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessagePostDTO messagePostDTO,  @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        Integer receiverId = messagePostDTO.getReceiverId();

        User sender = userService.findByUsername(username);
        User receiver = userService.findById(receiverId);

        if(sender == null){
            throw new UserNotFoundException(sender.getId());
        }

        if(receiver == null){
            throw new UserNotFoundException(receiver.getId());
        }

        if(sender.getId() == receiver.getId()){
            throw new MessageCheckOwnerException(username);
        }

        if(messagePostDTO.getTitle().trim().length() == 0  || messagePostDTO.getText().trim().length() == 0){
            throw new MessageEmptyContextException();
        }

        Message message = messageMapper.messagePostDtoToMessage(messagePostDTO);
        message.setSender(sender);
        message.setReceiver(receiver);
        messageService.add(message);

        MessageDTO messageDTO = messageMapper.messageToMessageDto(message);
        System.out.println("message " + messageDTO);


        return ResponseEntity.ok(messageDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<MessageDTO> updateMessage(@RequestBody MessageUpdateDTO messageUpdateDTO,
                                                              @PathVariable(value = "id") int id) {

        if (messageService.findById(id) == null) {
            throw new MessageNotFoundException(id);
        }

        messageUpdateDTO.setId(id);

        Message message = messageService.update(messageMapper.messageUpdateDtoToMessage(messageUpdateDTO));
        MessageDTO messageDTO = messageMapper.messageToMessageDto(message);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteMessage(@PathVariable(value = "id") int id) {
        Message deleteMessage = messageService.findById(id);

        if (deleteMessage == null) {
            throw new MessageNotFoundException(id);
        }

        messageService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/sent-messages")
    public ResponseEntity<Collection<MessageDTO>> getAllSentMessage(@PathVariable(value = "id") int id){
        return ResponseEntity.ok(messageMapper.messagesToMessageDtos(messageService.getMessagesSentByUser(id)));
    }

    @GetMapping("/{id}/received-messages")
    public ResponseEntity<Collection<MessageDTO>> getAllReceivedMessage(@PathVariable(value = "id") int id){
        return ResponseEntity.ok(messageMapper.messagesToMessageDtos(messageService.getMessagesReceiverByUser(id)));
    }


    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFoundException(MessageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MessageCheckOwnerException.class)
    public ResponseEntity<String> handleMessageCheckOwnerException(MessageCheckOwnerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MessageEmptyContextException.class)
    public ResponseEntity<String> handleMessageEmptyContextException(MessageEmptyContextException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
