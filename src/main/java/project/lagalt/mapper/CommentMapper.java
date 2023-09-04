package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.comment.CommentDTO;
import project.lagalt.model.dtos.comment.CommentPostDTO;
import project.lagalt.model.dtos.comment.CommentUpdateDTO;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    public abstract Comment commentPostDtoToComment(CommentPostDTO commentPostDTO);
    public abstract Comment commentUpdateDtoToComment(CommentUpdateDTO commentUpdateDTO);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "userIds",source = "users")
    public abstract CommentDTO commentToCommentDto(Comment comment);

    public abstract Collection<CommentDTO> commentToCommentDtos(Collection<Comment> comments);

    Set<Integer> usersToIds(Set<User> users){
        if(users == null){
            return null;
        }

        return users.stream().map(u -> u.getId()).collect(Collectors.toSet());
    }

}
