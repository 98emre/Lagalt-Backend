package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {
}
