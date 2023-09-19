package project.lagalt.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.utilites.enums.Application;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CollaboratorRepositoryTest {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Test
    void test_Save_Collaborator(){
        Collaborator collaborator = new Collaborator();
        collaborator.setStatus(Application.PENDING);
        collaborator.setMotivation("Test motivation");
        collaborator.setUser(null);
        collaborator.setProject(null);

        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);

        assertThat(savedCollaborator).isNotNull();
        assertThat(savedCollaborator.getMotivation()).isEqualTo("Test motivation");
        assertThat(savedCollaborator.getStatus()).isEqualTo(Application.PENDING);
    }

    @Test
    void test_Find_All_Collaborator() {
        Collaborator collaborator = new Collaborator();
        collaborator.setStatus(Application.PENDING);
        collaborator.setMotivation("Test motivation");
        collaborator.setUser(null);
        collaborator.setProject(null);
        collaboratorRepository.save(collaborator);

        Collaborator collaborator2 = new Collaborator();
        collaborator2.setStatus(Application.PENDING);
        collaborator2.setMotivation("Test motivation 2");
        collaborator2.setUser(null);
        collaborator2.setProject(null);
        collaboratorRepository.save(collaborator2);

        List<Collaborator> collaborators = collaboratorRepository.findAll();
        assertThat(collaborators).isNotNull().hasSize(2);
    }

    @Test
    void test_Find_Collaborator_By_Id() {
        Collaborator collaborator = new Collaborator();
        collaborator.setStatus(Application.PENDING);
        collaborator.setMotivation("Test motivation");
        collaborator.setUser(null);
        collaborator.setProject(null);

        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);
        Optional<Collaborator> foundCollaborator = collaboratorRepository.findById(savedCollaborator.getId());

        assertThat(foundCollaborator).isPresent();
        assertThat(foundCollaborator.get().getMotivation()).isEqualTo("Test motivation");
    }

    @Test
    void test_Update_Collaborator(){
        Collaborator collaborator = new Collaborator();
        collaborator.setStatus(Application.PENDING);
        collaborator.setMotivation("Test motivation");
        collaborator.setUser(null);
        collaborator.setProject(null);

        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);
        savedCollaborator.setMotivation("Emre motivation");
        collaboratorRepository.save(savedCollaborator);

        assertThat(savedCollaborator.getMotivation()).isEqualTo("Emre motivation");
    }

    @Test
    void testFindByProjectAndStatus() {
        Collaborator collaborator = new Collaborator();
        collaborator.setStatus(Application.PENDING);
        collaborator.setMotivation("Test motivation");
        collaborator.setUser(null);
        collaborator.setProject(null);
        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);

        Set<Collaborator> collaborators = collaboratorRepository.findByProjectAndStatus(savedCollaborator.getProject(), Application.PENDING);
        assertThat(collaborators).isNotEmpty();
    }
}
