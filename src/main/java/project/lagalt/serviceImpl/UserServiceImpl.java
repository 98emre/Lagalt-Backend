package project.lagalt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.lagalt.model.User;
import project.lagalt.repository.UserRepository;
import project.lagalt.service.UserService;
import project.lagalt.utilites.exceptions.UserNotFoundExceptions;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundExceptions(id));
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(User user) {
        User updateUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundExceptions(user.getId()));

        if(user.getUsername() != null){
            updateUser.setUsername(user.getUsername());
        }

        if(user.getPassword() != null){
            updateUser.setPassword(user.getPassword());
        }

        if(user.getSkills() != null){
            updateUser.setSkills(user.getSkills());
        }

        return userRepository.save(updateUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundExceptions(id));

        userRepository.deleteById(id);
    }
}
