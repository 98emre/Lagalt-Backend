package project.lagalt.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.lagalt.model.dtos.collaborator.CollaboratorDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorPostDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorUpdateDTO;
import project.lagalt.model.entities.Collaborator;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T15:46:05+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Microsoft)"
)
@Component
public class CollaboratorMapperImpl extends CollaboratorMapper {

    @Override
    public Collaborator collaboratorPostDtoToCollaborator(CollaboratorPostDTO collaboratorPostDTO) {
        if ( collaboratorPostDTO == null ) {
            return null;
        }

        Collaborator collaborator = new Collaborator();

        collaborator.setId( collaboratorPostDTO.getId() );
        collaborator.setStatus( collaboratorPostDTO.isStatus() );
        collaborator.setRequestDate( collaboratorPostDTO.getRequestDate() );
        collaborator.setApprovalDate( collaboratorPostDTO.getApprovalDate() );

        return collaborator;
    }

    @Override
    public Collaborator collaboratorUpdateDtoToCollaborator(CollaboratorUpdateDTO collaboratorUpdateDTO) {
        if ( collaboratorUpdateDTO == null ) {
            return null;
        }

        Collaborator collaborator = new Collaborator();

        collaborator.setId( collaboratorUpdateDTO.getId() );
        collaborator.setStatus( collaboratorUpdateDTO.isStatus() );
        collaborator.setRequestDate( collaboratorUpdateDTO.getRequestDate() );
        collaborator.setApprovalDate( collaboratorUpdateDTO.getApprovalDate() );

        return collaborator;
    }

    @Override
    public CollaboratorDTO collaboratorToCollaboratorDto(Collaborator collaborator) {
        if ( collaborator == null ) {
            return null;
        }

        CollaboratorDTO collaboratorDTO = new CollaboratorDTO();

        collaboratorDTO.setId( collaborator.getId() );
        collaboratorDTO.setStatus( collaborator.isStatus() );
        collaboratorDTO.setRequestDate( collaborator.getRequestDate() );
        collaboratorDTO.setApprovalDate( collaborator.getApprovalDate() );

        return collaboratorDTO;
    }

    @Override
    public Collection<CollaboratorDTO> collaboratorToCollaboratorDtos(Collection<Collaborator> collaborators) {
        if ( collaborators == null ) {
            return null;
        }

        Collection<CollaboratorDTO> collection = new ArrayList<CollaboratorDTO>( collaborators.size() );
        for ( Collaborator collaborator : collaborators ) {
            collection.add( collaboratorToCollaboratorDto( collaborator ) );
        }

        return collection;
    }
}
