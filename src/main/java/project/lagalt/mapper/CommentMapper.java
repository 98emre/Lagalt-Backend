package project.lagalt.mapper;


import org.mapstruct.Mapper;
import project.lagalt.model.dtos.comment.CommentDTO;
import project.lagalt.model.dtos.comment.CommentPostDTO;
import project.lagalt.model.dtos.comment.CommentUpdateDTO;
import project.lagalt.model.entities.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    public abstract Comment commentPostDtoToComment(CommentPostDTO commentPostDTO);
    public abstract Comment commentUpdateDtoToComment(CommentUpdateDTO commentUpdateDTO);

    public abstract CommentDTO commentToCommentDto(Comment comment);

    public abstract Collection<CommentDTO> commentDtosToComment(Collection<Comment> comments);
}
