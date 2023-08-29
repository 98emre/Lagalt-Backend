package project.lagalt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.lagalt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
