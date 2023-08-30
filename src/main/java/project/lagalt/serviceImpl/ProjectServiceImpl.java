package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Project;
import project.lagalt.repository.ProjectRepository;
import project.lagalt.service.ProjectService;

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
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project add(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Integer id) {
        projectRepository.deleteById(id);
    }
}
