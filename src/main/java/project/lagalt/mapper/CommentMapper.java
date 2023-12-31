package project.lagalt.mapper;


import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import project.lagalt.model.dtos.comment.*;
import project.lagalt.model.entities.Comment;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    public abstract Comment commentPostDtoToComment(CommentPostDTO commentPostDTO);
    public abstract Comment commentUpdateDtoToComment(CommentUpdateDTO commentUpdateDTO);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "userId",source = "user.id")
    public abstract CommentDTO commentToCommentDto(Comment comment);

    public abstract Collection<CommentDTO> commentToCommentDtos(Collection<Comment> comments);

}
