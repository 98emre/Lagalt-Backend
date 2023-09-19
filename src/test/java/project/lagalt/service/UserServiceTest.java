package project.lagalt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.lagalt.model.entities.User;
import project.lagalt.repository.UserRepository;
import project.lagalt.serviceImpl.UserServiceImpl;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void test_Add_User_Successfully(){
        User mockUser = new User();
        mockUser.setUsername("EmreTest");
        mockUser.setEmail("Emretest@gmail.com");

        lenient().when(userRepository.existsByUsername("EmreTest")).thenReturn(false);
        lenient().when(userRepository.existsByEmail("Emretest@gmail.com")).thenReturn(false);
        lenient().when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User addUser = userService.add(mockUser);

        assertThat(addUser).isNotNull();
        assertThat(addUser.getUsername()).isEqualTo("EmreTest");
    }

    @Test
    void test_Find_User_By_Id() {
        User mockUser = new User();
        mockUser.setUsername("EmreTest");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        User foundUser = userService.findById(1);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(mockUser.getId());
    }

    @Test
    void test_Find_All_User() {
        User mockUser = new User();
        mockUser.setUsername("EmreTest");

        User mockUser2 = new User();
        mockUser2.setUsername("EmreTest2");

        List<User> mockUsers = Arrays.asList(mockUser, mockUser2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> returnedUsers = (List<User>) userService.findAll();
        
        assertThat(returnedUsers).hasSize(2);
        assertThat(returnedUsers.get(0).getUsername()).isEqualTo("EmreTest");
    }

    @Test
    void test_Find_User_By_Username() {
        User mockUser = new User();
        mockUser.setUsername("EmreTest");

        when(userRepository.findByUsername("EmreTest")).thenReturn(Optional.of(mockUser));

        User foundUser = userService.findByUsername("EmreTest");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("EmreTest");
    }

    @Test
    void test_Find_All_Users_By_Name(){
        User user1 = new User();
        user1.setUsername("Emre");
        user1.setFullname("Emre");

        User user2 = new User();
        user2.setUsername("Emre 2");
        user2.setFullname("Emre");

        Set<User> userSet = new HashSet<>(Arrays.asList(user1));
        lenient().when(userRepository.findAllByUsernameIgnoreCaseContainingOrFullnameIgnoreCaseContaining("emre","emre")).thenReturn(userSet);

        List<User> users = new ArrayList<>(userSet);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("Emre");

    }

    @Test
    void test_update_user(){
        User oldUser = new User();
        oldUser.setId(1);
        oldUser.setUsername("oldEmre");
        oldUser.setEmail("oldEmreEmail");

        User newUser = new User();
        newUser.setId(1);
        newUser.setUsername("newEmre");
        newUser.setEmail("newEmreEmail");

        when(userRepository.findById(1)).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.update(newUser);

        assertThat(updatedUser.getUsername()).isEqualTo("newEmre");
        assertThat(updatedUser.getEmail()).isEqualTo("newEmreEmail");
    }

    @Test
    void test_Delete_User_By_Id(){
        User deleteUser = new User();
        deleteUser.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(deleteUser));
        userService.deleteById(1);

        verify(userRepository,times(1)).deleteById(1);
    }

}
