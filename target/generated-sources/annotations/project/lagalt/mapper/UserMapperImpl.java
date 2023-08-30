package project.lagalt.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.lagalt.model.dtos.user.UserDTO;
import project.lagalt.model.dtos.user.UserPostDTO;
import project.lagalt.model.dtos.user.UserUpdateDTO;
import project.lagalt.model.entities.User;
import project.lagalt.utilites.enums.Skills;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T09:27:49+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Microsoft)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User userPostToUser(UserPostDTO userPostDTO) {
        if ( userPostDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userPostDTO.getId() );
        user.setUsername( userPostDTO.getUsername() );
        user.setPassword( userPostDTO.getPassword() );
        user.setEmail( userPostDTO.getEmail() );
        user.setFullname( userPostDTO.getFullname() );
        Set<Skills> set = userPostDTO.getSkills();
        if ( set != null ) {
            user.setSkills( new HashSet<Skills>( set ) );
        }

        return user;
    }

    @Override
    public User userUpdateToUser(UserUpdateDTO userUpdateDTO) {
        if ( userUpdateDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userUpdateDTO.getId() );
        user.setUsername( userUpdateDTO.getUsername() );
        user.setPassword( userUpdateDTO.getPassword() );
        user.setEmail( userUpdateDTO.getEmail() );
        user.setFullname( userUpdateDTO.getFullname() );
        Set<Skills> set = userUpdateDTO.getSkills();
        if ( set != null ) {
            user.setSkills( new HashSet<Skills>( set ) );
        }

        return user;
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setFullname( user.getFullname() );
        Set<Skills> set = user.getSkills();
        if ( set != null ) {
            userDTO.setSkills( new HashSet<Skills>( set ) );
        }

        return userDTO;
    }

    @Override
    public Collection<UserDTO> usersToUsersDTO(Collection<User> users) {
        if ( users == null ) {
            return null;
        }

        Collection<UserDTO> collection = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            collection.add( userToUserDTO( user ) );
        }

        return collection;
    }
}
