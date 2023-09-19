package project.lagalt.mapper;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import project.lagalt.model.dtos.user.*;
import project.lagalt.model.entities.*;

@Mapper(componentModel = "spring")
public abstract  class UserMapper {

    public abstract User userPostToUser(UserPostDTO userPostDTO);
    public abstract User userUpdateToUser(UserUpdateDTO userUpdateDTO);

    @Mapping(target = "projectIds", source = "projects")
    @Mapping(target = "collaboratorIds", source = "collaborators")
    @Mapping(target = "commentIds", source = "comments")
    @Mapping(target = "receivedMessageIds", source = "receivedMessages", qualifiedByName = "receiversToIds")
    @Mapping(target = "sentMessageIds", source = "sentMessages", qualifiedByName = "sendersToIds")
    public abstract UserDTO userToUserDTO(User user);

    public abstract Collection<UserDTO> usersToUsersDTO(Collection<User> users);


    Set<Integer> projectsToIds(Set<Project> projects) {
        if (projects == null) {
            return null;
        }
        return projects.stream().map(p->p.getId()).collect(Collectors.toSet());
    }

    Set<Integer> collaboratorsToIds(Set<Collaborator> collaborators) {
        if (collaborators == null) {
            return null;
        }
        return collaborators.stream().map(c-> c.getId()).collect(Collectors.toSet());
    }

    Set<Integer> commentsToIds(Set<Comment> comments){
        if(comments == null){
            return  null;
        }

        return comments.stream().map(c -> c.getId()).collect(Collectors.toSet());
    }

    @Named("receiversToIds")
    Set<Integer> receiversToIds(Set<Message> messages){
        if(messages == null){
            return null;
        }

        return messages.stream().map(m -> m.getId()).collect(Collectors.toSet());
    }
    @Named("sendersToIds")
    Set<Integer> sendersToIds(Set<Message> messages){
        if(messages == null){
            return null;
        }

        return messages.stream().map(m -> m.getId()).collect(Collectors.toSet());
    }
}






