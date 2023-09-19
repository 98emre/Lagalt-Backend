package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.utilites.enums.Application;

import java.util.Set;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {
    Set<Collaborator> findByProjectAndStatus(Project project, Application status);
}
