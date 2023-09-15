package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.lagalt.model.dtos.message.MessageDTO;
import project.lagalt.model.dtos.message.MessagePostDTO;
import project.lagalt.model.dtos.message.MessageUpdateDTO;
import project.lagalt.model.entities.Message;
import project.lagalt.model.entities.MessageResponse;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public abstract class MessageMapper {

    public abstract Message messagePostDtoToMessage(MessagePostDTO messagePostDTO);
    public abstract Message messageUpdateDtoToMessage(MessageUpdateDTO messageUpdateDTO);


    @Mapping(target = "senderId", source = "sender_id")
    @Mapping(target = "receiverId", source = "receiver_id")
    @Mapping(target = "responseIds", source = "responses")
    public abstract MessageDTO messageToMessageDto(Message message);

    public abstract Collection<MessageDTO> messagesToMessageDtos(Collection<Message> messages);


    Set<Integer> responsesToIds(Set<MessageResponse> responses){
        if(responses == null){
            return  null;
        }

        return responses.stream().map( r -> r.getId()).collect(Collectors.toSet());
    }
}
