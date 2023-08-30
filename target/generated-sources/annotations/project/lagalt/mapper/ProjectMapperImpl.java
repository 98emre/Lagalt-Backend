package project.lagalt.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.lagalt.model.dtos.project.ProjectDTO;
import project.lagalt.model.dtos.project.ProjectPostDTO;
import project.lagalt.model.dtos.project.ProjectUpdateDTO;
import project.lagalt.model.entities.Project;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T13:30:18+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Microsoft)"
)
@Component
public class ProjectMapperImpl extends ProjectMapper {

    @Override
    public Project projectPostDtoToProject(ProjectPostDTO projectPostDTO) {
        if ( projectPostDTO == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( projectPostDTO.getId() );
        project.setTitle( projectPostDTO.getTitle() );
        project.setDescriptions( projectPostDTO.getDescriptions() );
        project.setGitlink( projectPostDTO.getGitlink() );
        project.setCategory( projectPostDTO.getCategory() );
        project.setStatus( projectPostDTO.getStatus() );

        return project;
    }

    @Override
    public Project projectUpdateDtoToProject(ProjectUpdateDTO projectUpdateDTO) {
        if ( projectUpdateDTO == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( projectUpdateDTO.getId() );
        project.setTitle( projectUpdateDTO.getTitle() );
        project.setDescriptions( projectUpdateDTO.getDescriptions() );
        project.setGitlink( projectUpdateDTO.getGitlink() );
        project.setCategory( projectUpdateDTO.getCategory() );
        project.setStatus( projectUpdateDTO.getStatus() );

        return project;
    }

    @Override
    public ProjectDTO projectToProjectDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId( project.getId() );
        projectDTO.setTitle( project.getTitle() );
        projectDTO.setDescriptions( project.getDescriptions() );
        projectDTO.setGitlink( project.getGitlink() );
        projectDTO.setCategory( project.getCategory() );
        projectDTO.setStatus( project.getStatus() );

        return projectDTO;
    }

    @Override
    public Collection<ProjectDTO> projectsToProjectDTO(Collection<Project> projects) {
        if ( projects == null ) {
            return null;
        }

        Collection<ProjectDTO> collection = new ArrayList<ProjectDTO>( projects.size() );
        for ( Project project : projects ) {
            collection.add( projectToProjectDTO( project ) );
        }

        return collection;
    }
}
