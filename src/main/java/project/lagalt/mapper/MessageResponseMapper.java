package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.message.MessagePostDTO;
import project.lagalt.model.dtos.message.MessageUpdateDTO;
import project.lagalt.model.dtos.messageresponse.MessageResponseDTO;
import project.lagalt.model.dtos.messageresponse.MessageResponsePostDTO;
import project.lagalt.model.entities.MessageResponse;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class MessageResponseMapper {

    public abstract MessageResponse messageResponsePostDtoToMessageResponse(MessageResponsePostDTO messageResponsePostDTO);

    public abstract MessageResponse messageResponseUpdateDtoToMessageResponse(MessageUpdateDTO messageUpdateDTO);

    @Mapping(target = "responderId", source = "responder.id")
    @Mapping(target = "messageId", source = "message.id")
    public abstract MessageResponseDTO messageResponseToMessageResponseDto(MessageResponse messageResponse);

    public abstract Collection<MessageResponseDTO> messageResponsesToMessageResponseDtos(Collection<MessageResponse> messageResponses);

}
