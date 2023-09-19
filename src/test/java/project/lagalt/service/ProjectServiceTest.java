package project.lagalt.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;
import project.lagalt.repository.CollaboratorRepository;
import project.lagalt.repository.ProjectRepository;
import project.lagalt.serviceImpl.ProjectServiceImpl;
import project.lagalt.utilites.enums.Application;
import project.lagalt.utilites.enums.Category;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Test
    void test_Add_Project_Successfully(){
        Project mockProject = new Project();
        mockProject.setTitle("Project Title");
        mockProject.setDescriptions("Hello World");

        when(projectRepository.save(any(Project.class))).thenReturn(mockProject);

        Project addProject = projectService.add(mockProject);

        assertThat(addProject).isNotNull();
        assertThat(addProject.getTitle()).isEqualTo("Project Title");
    }

    @Test
    void test_Find_Project_By_Id() {
        Project mockProject = new Project();
        mockProject.setTitle("Hello World");

        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        Project foundProject = projectService.findById(1);

        assertThat(foundProject).isNotNull();
        assertThat(foundProject.getId()).isEqualTo(mockProject.getId());
    }

    @Test
    void test_Find_All_Projects() {
          Project mockProject = new Project();
          mockProject.setTitle("Hello World 1");

          Project mockProject2 = new Project();
          mockProject2.setTitle("Hello World 2");

          List<Project> projectMocks = new ArrayList<>(Arrays.asList(mockProject,mockProject2));

          when(projectRepository.findAll()).thenReturn(projectMocks);

          List<Project> projects = (List<Project>) projectService.findAll();

          assertThat(projects).hasSize(2);
          assertThat(projects.get(1).getTitle()).isEqualTo("Hello World 2");

    }

    @Test
    void test_Update_Project(){
        Project oldProject = new Project();
        oldProject.setId(1);
        oldProject.setTitle("Hello World Old");
        oldProject.setCategory(Category.MUSIC);

        Project newProject = new Project();
        newProject.setId(1);
        newProject.setTitle("New Hello World");
        newProject.setCategory(Category.GAME);

        when(projectRepository.findById(1)).thenReturn(Optional.of(oldProject));
        when(projectRepository.save(any(Project.class))).thenReturn(newProject);

        Project updatedProject = projectService.update(newProject);

        assertThat(updatedProject.getTitle()).isEqualTo("New Hello World");
        assertThat(updatedProject.getCategory()).isEqualTo(Category.GAME);
    }

    @Test
    void test_Delete_User_By_Id(){
        Project deleteProject = new Project();
        deleteProject.setId(1);

        when(projectRepository.findById(1)).thenReturn(Optional.of(deleteProject));
        projectService.deleteById(1);

        verify(projectRepository,times(1)).deleteById(1);
    }

    @Test
    void test_Find_All_By_Title(){
        Project mockProject = new Project();
        mockProject.setTitle("Hello World 1");

        Project mockProject2 = new Project();
        mockProject2.setTitle("Hello World 2");

        Set<Project> projectMocks = new HashSet<>(Arrays.asList(mockProject,mockProject2));

        when(projectRepository.findAllByTitleIgnoreCaseContaining("Hello")).thenReturn(projectMocks);

        Collection<Project> projects = projectService.findAllByTitle("Hello");

        assertThat(projects).hasSize(2);
        assertThat(projects).contains(mockProject,mockProject2);
    }

    @Test
    void test_Find_All_Pending_By_Collaborator(){
        Collaborator collaborator = new Collaborator();
        User user = new User();
        user.setUsername("Collab 1");
        collaborator.setUser(user);
        collaborator.setStatus(Application.PENDING);

        Collaborator collaborator2 = new Collaborator();
        User user2 = new User();
        user.setUsername("Collab 2");
        collaborator2.setUser(user2);
        collaborator2.setStatus(Application.PENDING);

        Set<Collaborator> collaboratorMocks = new HashSet<>(Arrays.asList(collaborator,collaborator2));


        Project projectMock = new Project();
        projectMock.setTitle("Collab Test");
        projectMock.setCollaborators(collaboratorMocks);

        when(collaboratorRepository.findByProjectAndStatus(projectMock,Application.PENDING)).thenReturn(collaboratorMocks);

        Collection<Collaborator> collabs = projectService.findAllPendingByCollaborator(projectMock);

        assertThat(collabs).hasSize(2);
        assertThat(collabs).contains(collaborator,collaborator2);

    }

    @Test
    void test_Find_All_Approved_By_Collaborator(){
        Collaborator collaborator = new Collaborator();
        User user = new User();
        user.setUsername("Collab 1");
        collaborator.setUser(user);
        collaborator.setStatus(Application.APPROVED);

        Collaborator collaborator2 = new Collaborator();
        User user2 = new User();
        user.setUsername("Collab 2");
        collaborator2.setUser(user2);
        collaborator2.setStatus(Application.APPROVED);

        Set<Collaborator> collaboratorMocks = new HashSet<>(Arrays.asList(collaborator,collaborator2));


        Project projectMock = new Project();
        projectMock.setTitle("Collab Test");
        projectMock.setCollaborators(collaboratorMocks);

        when(collaboratorRepository.findByProjectAndStatus(projectMock,Application.PENDING)).thenReturn(collaboratorMocks);

        Collection<Collaborator> collabs = projectService.findAllPendingByCollaborator(projectMock);

        assertThat(collabs).hasSize(2);
        assertThat(collabs).contains(collaborator,collaborator2);

    }

    @Test
    void test_Find_Collaborator_Exist(){
        Project mockProject = new Project();
        mockProject.setId(1);

        User userMock = new User();
        userMock.setUsername("Emre");

        Collaborator collaborator = new Collaborator();
        collaborator.setProject(mockProject);
        collaborator.setUser(userMock);

        Set<Collaborator> collaborators = new HashSet<>(Arrays.asList(collaborator));

        mockProject.setCollaborators(collaborators);

        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        boolean doesExist = projectService.findCollaboratorExist(mockProject.getId(),userMock);

        assertThat(doesExist).isTrue();
    }

}
