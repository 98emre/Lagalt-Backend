package project.lagalt.mapper;


import org.mapstruct.Mapper;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Project;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    public abstract Project projectPostDtoToProject(ProjectPostDTO projectPostDTO);

    public abstract Project projectUpdateDtoToProject(ProjectUpdateDTO projectUpdateDTO);

    public abstract ProjectDTO projectToProjectDTO(Project project);

    public abstract Collection<ProjectDTO> projectsToProjectDTO(Collection<Project> projects);

}
