package project.lagalt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lagalt.mapper.MessageResponseMapper;
import project.lagalt.model.dtos.messageresponse.MessageResponseDTO;
import project.lagalt.model.dtos.messageresponse.MessageResponsePostDTO;
import project.lagalt.model.dtos.messageresponse.MessageResponseUpdateDTO;
import project.lagalt.model.entities.MessageResponse;
import project.lagalt.model.entities.User;
import project.lagalt.service.MessageResponseService;

import java.util.Collection;


@RestController
@RequestMapping(path = "api/messageresponses")
@CrossOrigin
public class MessageResponseController {

    private final MessageResponseService messageResponseService;
    private final MessageResponseMapper messageResponseMapper;

    public MessageResponseController(MessageResponseService messageResponseService, MessageResponseMapper messageResponseMapper) {
        this.messageResponseService = messageResponseService;
        this.messageResponseMapper = messageResponseMapper;
    }


    @GetMapping
    public ResponseEntity<Collection<MessageResponseDTO>> getAllMessageResponse() {
        return ResponseEntity.ok(messageResponseMapper.messageResponsesToMessageResponseDtos(messageResponseService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> getMessageResponseById(@PathVariable Integer id) {
        MessageResponse messageResponse = messageResponseService.findById(id);
        return ResponseEntity.ok(messageResponseMapper.messageResponseToMessageResponseDto(messageResponse));
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> addMessageResponse(@RequestBody MessageResponsePostDTO messageResponsePostDTO) {
        MessageResponse messageResponse = messageResponseMapper.messageResponsePostDtoToMessageResponse(messageResponsePostDTO);
        MessageResponseDTO messageResponseDTO = messageResponseMapper.messageResponseToMessageResponseDto(messageResponse);

        return ResponseEntity.ok(messageResponseDTO);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<MessageResponseDTO> updateMessageResponse(@RequestBody MessageResponseUpdateDTO messageResponseUpdateDTO,
                                                                    @PathVariable int id) {

        if (messageResponseService.findById(id) == null) {
            return null;
        }

        messageResponseUpdateDTO.setId(id);

        MessageResponse messageResponse = messageResponseService.update(messageResponseMapper.messageResponseUpdateDtoToMessageResponse(messageResponseUpdateDTO));
        MessageResponseDTO messageResponseDTO = messageResponseMapper.messageResponseToMessageResponseDto(messageResponse);


        return ResponseEntity.ok(messageResponseDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<User> deleteMessageResponse(@PathVariable int id) {
        MessageResponse deleteMessageResponse = messageResponseService.findById(id);

        if (deleteMessageResponse == null) {
            return null;
        }

        messageResponseService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
