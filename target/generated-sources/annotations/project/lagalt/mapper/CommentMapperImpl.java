package project.lagalt.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.lagalt.model.dtos.comment.CommentDTO;
import project.lagalt.model.dtos.comment.CommentPostDTO;
import project.lagalt.model.dtos.comment.CommentUpdateDTO;
import project.lagalt.model.entities.Comment;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T15:46:05+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Microsoft)"
)
@Component
public class CommentMapperImpl extends CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentPostDTO commentPostDTO) {
        if ( commentPostDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentPostDTO.getId() );
        comment.setText( commentPostDTO.getText() );
        comment.setDate( commentPostDTO.getDate() );

        return comment;
    }

    @Override
    public Comment commentUpdateDtoToComment(CommentUpdateDTO commentUpdateDTO) {
        if ( commentUpdateDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentUpdateDTO.getId() );
        comment.setText( commentUpdateDTO.getText() );
        comment.setDate( commentUpdateDTO.getDate() );

        return comment;
    }

    @Override
    public CommentDTO commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setText( comment.getText() );
        commentDTO.setDate( comment.getDate() );

        return commentDTO;
    }

    @Override
    public Collection<CommentDTO> commentToCommentDtos(Collection<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        Collection<CommentDTO> collection = new ArrayList<CommentDTO>( comments.size() );
        for ( Comment comment : comments ) {
            collection.add( commentToCommentDto( comment ) );
        }

        return collection;
    }
}
