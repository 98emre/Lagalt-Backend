package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserPostDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.User;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract  class UserMapper {

    public abstract User userPostToUser(UserPostDTO userPostDTO);
    public abstract User userUpdateToUser(UserUpdateDTO userUpdateDTO);

    public abstract UserDTO userToUserDTO(User user);

    public abstract Collection<UserDTO> usersToUsersDTO(Collection<User> users);

}
