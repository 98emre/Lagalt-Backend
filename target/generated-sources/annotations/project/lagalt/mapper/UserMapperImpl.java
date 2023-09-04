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
    date = "2023-09-04T15:27:06+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
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
        user.setDescription( userPostDTO.getDescription() );
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
        user.setDescription( userUpdateDTO.getDescription() );
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

        userDTO.setProjectIds( projectsToIds( user.getProjects() ) );
        userDTO.setCollaboratorIds( collaboratorsToIds( user.getCollaborators() ) );
        userDTO.setCommentIds( commentsToIds( user.getComments() ) );
        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setDescription( user.getDescription() );
        userDTO.setFullname( user.getFullname() );
        Set<Skills> set3 = user.getSkills();
        if ( set3 != null ) {
            userDTO.setSkills( new HashSet<Skills>( set3 ) );
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
