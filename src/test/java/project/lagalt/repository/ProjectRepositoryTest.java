package project.lagalt.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.lagalt.model.entities.Project;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;


    @Test
    void test_Save_Project(){
        Project project = new Project();
        project.setTitle("Emre Project");
        project.setDescriptions("This is Emre story project");
        project.setGitlink("https://github.com/emretest/emre-project");
        project.setCategory(Category.GAME);
        project.setStatus(Status.NOT_STARTED);

        Project savedProject = projectRepository.save(project);


        assertThat(savedProject).isNotNull();
        assertThat(savedProject.getId()).isGreaterThan(0);
        assertThat(savedProject.getTitle()).isEqualTo("Emre Project");
    }

    @Test
    void test_Find_Project_By_Id(){
        Project project = new Project();
        project.setTitle("Emre Project");
        project.setDescriptions("This is Emre story project");
        project.setGitlink("https://github.com/emretest/emre-project");
        project.setCategory(Category.GAME);
        project.setStatus(Status.NOT_STARTED);

        Project savedProject = projectRepository.save(project);

        Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

        assertThat(foundProject).isPresent();
        assertThat(foundProject.get().getId()).isEqualTo(savedProject.getId());

    }

    @Test
    void test_Find_All_Projects(){
        Project project1 = new Project();
        project1.setTitle("Emre Project 1");
        project1.setDescriptions("This is Emre story project 1");
        project1.setGitlink("https://github.com/emretest/emre-project1");
        project1.setCategory(Category.GAME);
        project1.setStatus(Status.NOT_STARTED);
        projectRepository.save(project1);


        Project project2 = new Project();
        project2.setTitle("Emre Project 1");
        project2.setDescriptions("This is Emre story project 2");
        project2.setGitlink("https://github.com/emretest/emre-project2");
        project2.setCategory(Category.FILM);
        project2.setStatus(Status.IN_PROGRESS);
        projectRepository.save(project2);

        List<Project> projects = projectRepository.findAll();

        assertThat(projects).hasSize(2);

    }

    @Test
    void test_Update_Project(){
        Project project = new Project();
        project.setTitle("Emre Project");
        project.setDescriptions("This is Emre story project");
        project.setGitlink("https://github.com/emretest/emre-project");
        project.setCategory(Category.GAME);
        project.setStatus(Status.NOT_STARTED);

        Project savedProject = projectRepository.save(project);
        savedProject.setTitle("Emre New Project");
        projectRepository.save(savedProject);

        Optional<Project> updatedProject = projectRepository.findById(savedProject.getId());

        assertThat(updatedProject).isPresent();
        assertThat(updatedProject.get().getTitle()).isEqualTo("Emre New Project");
    }

    @Test
    void test_Delete_Project_By_Id(){
        Project project = new Project();
        project.setTitle("Emre Project");
        project.setDescriptions("This is Emre story project");
        project.setGitlink("https://github.com/emretest/emre-project");
        project.setCategory(Category.GAME);
        project.setStatus(Status.NOT_STARTED);

        Project savedProject = projectRepository.save(project);
        projectRepository.deleteById(savedProject.getId());
        Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

        assertThat(foundProject).isNotPresent();
    }

    @Test
    void find_All_Project_By_Title_Ignore_Case_Containing(){
        Project project1 = new Project();
        project1.setTitle("Emre Project 1");
        project1.setDescriptions("This is Emre story project 1");
        project1.setGitlink("https://github.com/emretest/emre-project1");
        project1.setCategory(Category.GAME);
        project1.setStatus(Status.NOT_STARTED);
        projectRepository.save(project1);


        Project project2 = new Project();
        project2.setTitle("Emre Project 1");
        project2.setDescriptions("This is Emre story project 2");
        project2.setGitlink("https://github.com/emretest/emre-project2");
        project2.setCategory(Category.FILM);
        project2.setStatus(Status.IN_PROGRESS);
        projectRepository.save(project2);

        Set<Project> projects = projectRepository.findAllByTitleIgnoreCaseContaining("Em");

        assertThat(projects).hasSize(2);

    }


}
