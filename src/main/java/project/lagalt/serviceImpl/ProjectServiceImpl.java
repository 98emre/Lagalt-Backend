package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Project;
import project.lagalt.repository.ProjectRepository;
import project.lagalt.service.ProjectService;
import project.lagalt.utilites.exceptions.ProjectNotFoundException;

import java.util.Collection;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public Project add(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        Project updatedProject = projectRepository.findById(project.getId()).orElseThrow(() -> new ProjectNotFoundException(project.getId()));

        if(project.getTitle() != null){
            updatedProject.setTitle(project.getTitle());
        }

        if(project.getDescriptions() != null){
            updatedProject.setDescriptions(project.getDescriptions());
        }

        if(project.getGitlink() != null){
            updatedProject.setGitlink(project.getGitlink());
        }

        if(project.getCategory() != null){
            updatedProject.setCategory(project.getCategory());
        }

        if(project.getStatus() != null){
            updatedProject.setStatus(project.getStatus());
        }

        return projectRepository.save(updatedProject);
    }

    @Override
    public void deleteById(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));

        projectRepository.deleteById(id);
    }
}
