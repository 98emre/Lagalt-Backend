package project.lagalt.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.User;
import project.lagalt.repository.CollaboratorRepository;
import project.lagalt.serviceImpl.CollaboratorServiceImpl;
import project.lagalt.utilites.enums.Application;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollaboratorServiceTest {

    @InjectMocks
    private CollaboratorServiceImpl collaboratorService;

    @Mock
    private CollaboratorRepository collaboratorRepository;


    @Test
    void test_Add_Collaborator_Successfully(){
        Collaborator mockCollaborator = new Collaborator();
        User user = new User();
        user.setUsername("Emre");
        mockCollaborator.setUser(user);
        mockCollaborator.setStatus(Application.PENDING);

        when(collaboratorRepository.save(any(Collaborator.class))).thenReturn(mockCollaborator);

        Collaborator addCollaborator = collaboratorService.add(mockCollaborator);

        assertThat(addCollaborator).isNotNull();
        assertThat(addCollaborator.getUser().getUsername()).isEqualTo("Emre");
    }

    @Test
    void test_Find_Collaborator_By_Id() {
        Collaborator mockCollaborator = new Collaborator();
        User user = new User();
        user.setUsername("Emre");
        mockCollaborator.setUser(user);
        mockCollaborator.setStatus(Application.PENDING);

        when(collaboratorRepository.findById(1)).thenReturn(Optional.of(mockCollaborator));

        Collaborator foundCollaborator = collaboratorService.findById(1);

        assertThat(foundCollaborator).isNotNull();
        assertThat(foundCollaborator.getUser().getUsername()).isEqualTo("Emre");

    }


    @Test
    void test_Find_All_Collaborators() {
        Collaborator mockCollaborator = new Collaborator();
        User user = new User();
        user.setUsername("Emre");
        mockCollaborator.setUser(user);
        mockCollaborator.setStatus(Application.PENDING);

        Collaborator mockCollaborator2 = new Collaborator();
        User user2 = new User();
        user2.setUsername("Emre2");
        mockCollaborator2.setUser(user2);
        mockCollaborator2.setStatus(Application.PENDING);

        List<Collaborator> mockCollaborators = Arrays.asList(mockCollaborator,mockCollaborator2);

        when(collaboratorRepository.findAll()).thenReturn(mockCollaborators);

        List<Collaborator> collaborators = (List<Collaborator>) collaboratorService.findAll();

        assertThat(collaborators).hasSize(2);
        assertThat(collaborators.get(1).getUser().getUsername()).isEqualTo("Emre2");
    }

    @Test
    void test_Update_Collaborator(){
        Collaborator oldmMockCollaborator = new Collaborator();
        oldmMockCollaborator.setId(1);
        User user = new User();
        user.setUsername("Emre");
        oldmMockCollaborator.setUser(user);
        oldmMockCollaborator.setStatus(Application.PENDING);

        Collaborator newMockCollaborator = new Collaborator();
        newMockCollaborator.setId(1);
        User user2 = new User();
        user2.setUsername("Emre New");
        newMockCollaborator.setUser(user2);
        newMockCollaborator.setStatus(Application.PENDING);


        when(collaboratorRepository.findById(1)).thenReturn(Optional.of(oldmMockCollaborator));
        when(collaboratorRepository.save(any(Collaborator.class))).thenReturn(newMockCollaborator);

        Collaborator updated = collaboratorService.update(newMockCollaborator);

        assertThat(updated).isNotNull();
        assertThat(updated.getUser().getUsername()).isEqualTo("Emre New");

    }


    @Test
    void test_Delete_Collaborator_By_Id(){
        Collaborator deleteCollaborator = new Collaborator();
        User user = new User();
        user.setUsername("Emre");
        deleteCollaborator.setUser(user);
        deleteCollaborator.setStatus(Application.APPROVED);

        when(collaboratorRepository.findById(1)).thenReturn(Optional.of(deleteCollaborator));
        collaboratorService.deleteById(1);

        verify(collaboratorRepository,times(1)).deleteById(1);

    }
}
