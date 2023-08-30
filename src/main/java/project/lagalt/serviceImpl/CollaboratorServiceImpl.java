package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.repository.CollaboratorRepository;
import project.lagalt.service.CollaboratorService;

import java.util.Collection;


@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    @Autowired
    public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public Collection<Collaborator> findAll() {
        return collaboratorRepository.findAll();
    }

    @Override
    public Collaborator findById(Integer id) {
        return collaboratorRepository.findById(id).orElse(null);
    }

    @Override
    public Collaborator add(Collaborator collaborator) {
        return collaboratorRepository.save(collaborator);
    }

    @Override
    public Collaborator update(Collaborator collaborator) {
        return collaboratorRepository.save(collaborator);
    }

    @Override
    public void deleteById(Integer id) {
        collaboratorRepository.findById(id).orElse(null);

        collaboratorRepository.deleteById(id);
    }
}
