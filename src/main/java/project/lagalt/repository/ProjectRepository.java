package project.lagalt.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import project.lagalt.model.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Set<Project> findAllByTitleIgnoreCaseContaining(String title);
}
