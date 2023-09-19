package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.message.MessageDTO;
import project.lagalt.model.dtos.message.MessagePostDTO;
import project.lagalt.model.dtos.message.MessageUpdateDTO;
import project.lagalt.model.entities.Message;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {


    @Mapping(target = "receiver.id", source = "receiverId")
    public abstract Message messagePostDtoToMessage(MessagePostDTO messagePostDTO);

    @Mapping(target = "receiver.id", source = "receiverId")
    public abstract Message messageUpdateDtoToMessage(MessageUpdateDTO messageUpdateDTO);


    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "receiverId", source = "receiver.id")
    public abstract MessageDTO messageToMessageDto(Message message);

    public abstract Collection<MessageDTO> messagesToMessageDtos(Collection<Message> messages);

}
