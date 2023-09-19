package project.lagalt.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.lagalt.model.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave(){
        User user = new User();
        user.setFullname("Testing test");
        user.setEmail("testing@gmail.com");
        user.setUsername("testing");

        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testing");
    }

    @Test
    void testFindByUsername(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("Emre");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Emre");
    }

    @Test
    void testExistByUsername(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        userRepository.save(user);

        boolean exist = userRepository.existsByUsername("Emre");

        assertThat(exist).isTrue();

    }

    @Test
    void testExistByEmail(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        userRepository.save(user);

        boolean exist = userRepository.existsByEmail("emre@live.se");

        assertThat(exist).isTrue();
    }

    @Test
    void testFindAllByUsernameOrFullnameContaining(){
        User user1 = new User();
        user1.setUsername("Emre");
        user1.setEmail("emre@live.se");
        user1.setFullname("Emre The Man");
        userRepository.save(user1);


        User user2 = new User();
        user2.setUsername("Emre2");
        user2.setEmail("emre2@live.se");
        user2.setFullname("Emre2 The Man");
        userRepository.save(user2);

        Set<User> users = userRepository.findAllByUsernameIgnoreCaseContainingOrFullnameIgnoreCaseContaining("em", "Emre");


        assertThat(users).hasSize(2);
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    void testFindAll(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");
        userRepository.save(user);


        User user2 = new User();
        user2.setUsername("Emre2");
        user2.setEmail("emre2@live.se");
        user2.setFullname("Emre2 The Man");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();


        assertThat(users).hasSize(2);
    }

    @Test
    void testDeleteById(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isNotPresent();
    }

    @Test
    void testUpdate(){
        User user = new User();
        user.setUsername("Emre");
        user.setEmail("emre@live.se");
        user.setFullname("Emre The Man");

        User savedUser = userRepository.save(user);
        savedUser.setFullname("Emre New Man");
        userRepository.save(savedUser);

        Optional<User> updatedUser = userRepository.findById(savedUser.getId());

        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getFullname()).isEqualTo("Emre New Man");
    }
}
