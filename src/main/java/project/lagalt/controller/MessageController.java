package project.lagalt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.MessageMapper;
import project.lagalt.model.dtos.message.MessageDTO;
import project.lagalt.model.dtos.message.MessagePostDTO;
import project.lagalt.model.dtos.message.MessageUpdateDTO;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.User;
import project.lagalt.service.MessageService;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/messages")
@CrossOrigin
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<MessageDTO>> getAllMessage() {
        return ResponseEntity.ok(messageMapper.messagesToMessageDtos(messageService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Integer id) {
        Message message = messageService.findById(id);
        return ResponseEntity.ok(messageMapper.messageToMessageDto(message));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessagePostDTO messagePostDTO) {
        Message message = messageMapper.messagePostDtoToMessage(messagePostDTO);
        MessageDTO messageDTO = messageMapper.messageToMessageDto(message);

        return ResponseEntity.ok(messageDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<MessageDTO> updateMessage(@RequestBody MessageUpdateDTO messageUpdateDTO,
                                                              @PathVariable int id) {

        if (messageService.findById(id) == null) {
            return null;
        }

        messageUpdateDTO.setId(id);

        Message message = messageService.update(messageMapper.messageUpdateDtoToMessage(messageUpdateDTO));
        MessageDTO messageDTO = messageMapper.messageToMessageDto(message);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteMessage(@PathVariable int id) {
        Message deleteMessage = messageService.findById(id);

        if (deleteMessage == null) {
            return null;
        }

        messageService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
