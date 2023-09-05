package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.Project;

import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    Set<Project> findAllByTitleIgnoreCaseContaining(String title);
}
