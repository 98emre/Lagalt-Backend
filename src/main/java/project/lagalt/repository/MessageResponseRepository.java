package project.lagalt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.MessageResponse;

@Repository
public interface MessageResponseRepository extends JpaRepository<MessageResponse,Integer> {
}
