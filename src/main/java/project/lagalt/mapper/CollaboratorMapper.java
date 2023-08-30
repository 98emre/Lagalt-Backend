package project.lagalt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.collaborator.CollaboratorDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorPostDTO;
import project.lagalt.model.dtos.collaborator.CollaboratorUpdateDTO;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CollaboratorMapper {

    public abstract Collaborator collaboratorPostDtoToCollaborator(CollaboratorPostDTO collaboratorPostDTO);

    public abstract Collaborator collaboratorUpdateDtoToCollaborator(CollaboratorUpdateDTO collaboratorUpdateDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "projectId", source = "project.id")
    public abstract CollaboratorDTO collaboratorToCollaboratorDto(Collaborator collaborator);

    public abstract Collection<CollaboratorDTO> collaboratorToCollaboratorDtos(Collection<Collaborator> collaborators);

}
