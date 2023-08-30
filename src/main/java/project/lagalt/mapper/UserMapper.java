package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserPostDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract  class UserMapper {

    public abstract User userPostToUser(UserPostDTO userPostDTO);
    public abstract User userUpdateToUser(UserUpdateDTO userUpdateDTO);

    @Mapping(target = "projectIds", source = "projects")
    @Mapping(target = "collaboratorIds", source = "collaborators")
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
}






