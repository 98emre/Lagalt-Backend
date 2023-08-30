package project.lagalt.mapper;

import org.mapstruct.Mapper;
import project.lagalt.model.dtos.collaborator.CollaboratorDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorPostDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorUpdateDTO;
import project.lagalt.model.entities.Collaborator;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CollaboratorMapper {

    public abstract Collaborator collaboratorPostDtoToCollaborator(CollaboratorPostDTO collaboratorPostDTO);

    public abstract Collaborator collaboratorUpdateDtoToCollaborator(CollaboratorUpdateDTO collaboratorUpdateDTO);

    public abstract CollaboratorDTO collaboratorToCollaboratorDto(Collaborator collaborator);

    public abstract Collection<CollaboratorDTO> collaboratorToCollaboratorDtos(Collection<Collaborator> collaborators);


}
