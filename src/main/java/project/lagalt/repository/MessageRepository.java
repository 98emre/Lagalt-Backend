package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
}
