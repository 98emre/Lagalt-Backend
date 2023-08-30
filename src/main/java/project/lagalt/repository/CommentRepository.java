package project.lagalt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.lagalt.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
