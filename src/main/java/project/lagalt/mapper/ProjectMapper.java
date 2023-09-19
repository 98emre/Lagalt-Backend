package project.lagalt.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.lagalt.model.dtos.project.*;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    public abstract Project projectPostDtoToProject(ProjectPostDTO projectPostDTO);

    public abstract Project projectUpdateDtoToProject(ProjectUpdateDTO projectUpdateDTO);


    @Mapping(target = "commentIds", source = "comments")
    @Mapping(target = "collaboratorIds", source = "collaborators")
    @Mapping(target = "userId",source = "user.id")
    public abstract ProjectDTO projectToProjectDTO(Project project);

    public abstract Collection<ProjectDTO> projectsToProjectDTO(Collection<Project> projects);


    Set<Integer> commentsToIds(Set<Comment> comments){
        if(comments == null){
            return  null;
        }

        return comments.stream().map(c-> c.getId()).collect(Collectors.toSet());
    }

    Set<Integer> collaboratorsToIds(Set<Collaborator> collaborators){
        if(collaborators == null){
            return null;
        }

        return collaborators.stream().map(c -> c.getId()).collect(Collectors.toSet());
    }

}
