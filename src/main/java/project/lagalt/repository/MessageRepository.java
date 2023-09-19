package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.entities.Message;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    Set<Message> findBySenderId(int senderId);
    Set<Message> findByReceiverId(int senderId);

}
